package com.github.tartaricacid.touhoulittlemaid.block;

import cn.sh1rocu.touhoulittlemaid.api.extension.IBlock;
import cn.sh1rocu.touhoulittlemaid.util.particle.ParticleUtil;
import com.github.tartaricacid.touhoulittlemaid.client.resource.CustomPackLoader;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitBlocks;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import com.github.tartaricacid.touhoulittlemaid.item.ItemGarageKit;
import com.github.tartaricacid.touhoulittlemaid.tileentity.TileEntityGarageKit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class BlockGarageKit extends Block implements EntityBlock, IBlock {
    public static final VoxelShape BLOCK_AABB = Block.box(4, 0, 4, 12, 16, 12);
    private static final String ENTITY_INFO = "EntityInfo";

    @Override
    public boolean tlm$addHitEffects(BlockState state, Level world, HitResult target, ParticleEngine manager) {
        if (target instanceof BlockHitResult blockTarget && world instanceof ClientLevel clientWorld) {
            BlockPos pos = blockTarget.getBlockPos();
            this.crack(clientWorld, pos, Blocks.CLAY.defaultBlockState(), blockTarget.getDirection());
        }
        return true;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean tlm$addDestroyEffects(BlockState state, Level world, BlockPos pos, ParticleEngine manager) {
        Minecraft.getInstance().particleEngine.destroy(pos, Blocks.CLAY.defaultBlockState());
        return true;
    }

    @Environment(EnvType.CLIENT)
    private void crack(ClientLevel world, BlockPos pos, BlockState state, Direction side) {
        if (state.getRenderShape() != RenderShape.INVISIBLE) {
            int posX = pos.getX();
            int posY = pos.getY();
            int posZ = pos.getZ();
            AABB aabb = state.getShape(world, pos).bounds();
            double x = posX + world.random.nextDouble() * (aabb.maxX - aabb.minX - 0.2) + 0.1 + aabb.minX;
            double y = posY + world.random.nextDouble() * (aabb.maxY - aabb.minY - 0.2) + 0.1 + aabb.minY;
            double z = posZ + world.random.nextDouble() * (aabb.maxZ - aabb.minZ - 0.2) + 0.1 + aabb.minZ;
            if (side == Direction.DOWN) {
                y = posY + aabb.minY - 0.1;
            }
            if (side == Direction.UP) {
                y = posY + aabb.maxY + 0.1;
            }
            if (side == Direction.NORTH) {
                z = posZ + aabb.minZ - 0.1;
            }
            if (side == Direction.SOUTH) {
                z = posZ + aabb.maxZ + 0.1;
            }
            if (side == Direction.WEST) {
                x = posX + aabb.minX - 0.1;
            }
            if (side == Direction.EAST) {
                x = posX + aabb.maxX + 0.1;
            }
            TerrainParticle diggingParticle = new TerrainParticle(world, x, y, z, 0, 0, 0, state);
            Minecraft.getInstance().particleEngine.add(ParticleUtil.updateSprite(diggingParticle, state, pos).setPower(0.2f).scale(0.6f));
        }
    }

    public BlockGarageKit() {
        super(Properties.of().sound(SoundType.MUD).strength(1, 2).noOcclusion());
    }

    @Environment(EnvType.CLIENT)
    public static void fillItemCategory(CreativeModeTab.Output items) {
        for (String modelId : CustomPackLoader.MAID_MODELS.getModelIdSet()) {
            ItemStack stack = new ItemStack(InitBlocks.GARAGE_KIT);
            CompoundTag data = stack.getOrCreateTagElement(ENTITY_INFO);
            data.putString("id", Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(InitEntities.MAID)).toString());
            data.putString(EntityMaid.MODEL_ID_TAG, modelId);
            // 创造模式物品栏数据需要强制指定 YSM 渲染为空
            data.putBoolean(EntityMaid.IS_YSM_MODEL_TAG, false);
            items.accept(stack);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityGarageKit(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && !isMoving) {
            popResource(worldIn, pos, getGarageKitFromWorld(worldIn, pos));
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        this.getGarageKit(worldIn, pos).ifPresent(te -> {
            Direction facing = Direction.SOUTH;
            if (placer != null) {
                facing = placer.getDirection().getOpposite();
            }
            te.setData(facing, ItemGarageKit.getMaidData(stack));
        });
    }

    @Override
    //public ItemStack getCloneItemStack(@NotNull BlockState state, @NotNull HitResult target, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Player player) {
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return getGarageKitFromWorld(world, pos);
    }


    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (!(worldIn instanceof ServerLevel) || !(stack.getItem() instanceof SpawnEggItem)) {
            return InteractionResult.PASS;
        }
        BlockEntity tile = worldIn.getBlockEntity(pos);
        if (!(tile instanceof TileEntityGarageKit garageKit)) {
            return InteractionResult.PASS;
        }
        EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        if (key.equals(BuiltInRegistries.ENTITY_TYPE.getDefaultKey())) {
            return InteractionResult.PASS;
        }

        String id = key.toString();
        CompoundTag data = new CompoundTag();
        data.putString("id", id);

        Entity entity = type.create(worldIn);
        if (entity instanceof Mob mobEntity) {
            mobEntity.finalizeSpawn((ServerLevel) worldIn, worldIn.getCurrentDifficultyAt(pos), MobSpawnType.SPAWN_EGG, null, data);
            mobEntity.addAdditionalSaveData(data);
        }

        garageKit.setData(garageKit.getFacing(), data);
        return InteractionResult.SUCCESS;
    }

    private ItemStack getGarageKitFromWorld(BlockGetter world, BlockPos pos) {
        ItemStack stack = new ItemStack(InitBlocks.GARAGE_KIT);
        getGarageKit(world, pos).ifPresent(te -> stack.getOrCreateTag().put(ENTITY_INFO, te.getExtraData()));
        return stack;
    }

    private Optional<TileEntityGarageKit> getGarageKit(BlockGetter world, BlockPos pos) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof TileEntityGarageKit) {
            return Optional.of((TileEntityGarageKit) te);
        }
        return Optional.empty();
    }

    @Nullable
    public EntityType<?> getType(@Nullable CompoundTag nbt) {
        if (nbt != null && nbt.contains("EntityTag", Tag.TAG_COMPOUND)) {
            CompoundTag compound = nbt.getCompound("EntityTag");
            if (compound.contains("id", Tag.TAG_STRING)) {
                return EntityType.byString(compound.getString("id")).orElse(null);
            }
        }
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return BLOCK_AABB;
    }
}
