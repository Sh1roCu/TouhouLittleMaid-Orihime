package com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;

public class MaidShearTask extends MaidCheckRateTask {
    private static final int MAX_DELAY_TIME = 12;
    private final float speedModifier;
    private LivingEntity shearableEntity = null;

    public MaidShearTask(float speedModifier) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
        this.speedModifier = speedModifier;
        this.setMaxCheckRate(MAX_DELAY_TIME);
    }

    @Override
    protected void start(ServerLevel worldIn, EntityMaid maid, long gameTimeIn) {
        ItemStack mainHandItem = maid.getMainHandItem();
        shearableEntity = null;

        if (!(mainHandItem.getItem() instanceof ShearsItem)) {
            return;
        }

        this.getEntities(maid)
                .find(e -> maid.isWithinRestriction(e.blockPosition()))
                .filter(Entity::isAlive)
                .filter(e -> e instanceof Shearable /*IShearable*/)
                //.filter(e -> ((IShearable) e).isShearable(null, mainHandItem, maid.level(), e.blockPosition()))
                .filter(e -> ((Shearable) e).readyForShearing())
                .filter(maid::canPathReach)
                .findFirst()
                .ifPresent(e -> {
                    shearableEntity = e;
                    BehaviorUtils.setWalkAndLookTargetMemories(maid, e, this.speedModifier, 0);
                });

        if (shearableEntity != null && shearableEntity.closerThan(maid, 2)) {
/*            RandomSource rand = maid.getRandom();
            List<ItemStack> drops = ((IShearable) shearableEntity).onSheared(null, mainHandItem,
                    maid.level(), shearableEntity.blockPosition());
            drops.forEach(stack -> {
                ItemEntity itemEntity = shearableEntity.spawnAtLocation(stack, 1.0F);
                if (itemEntity != null) {
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(
                            (rand.nextFloat() - rand.nextFloat()) * 0.1F,
                            rand.nextFloat() * 0.05F,
                            (rand.nextFloat() - rand.nextFloat()) * 0.1F));
                }
            });*/

            // Fabric:
            ((Shearable) shearableEntity).shear(SoundSource.BLOCKS);

            maid.swing(InteractionHand.MAIN_HAND);
            mainHandItem.hurtAndBreak(1, maid, (entityMaid) -> entityMaid.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            shearableEntity = null;
        }
    }

    private NearestVisibleLivingEntities getEntities(EntityMaid maid) {
        return maid.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
    }
}
