package com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task;

import cn.sh1rocu.touhoulittlemaid.util.transfer.CombinedResourceHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.util.ItemsUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.Block.canSupportCenter;

public class MaidTorchMoveTask extends MaidMoveToBlockTask {
    private static final int LOW_BRIGHTNESS = 9;

    public MaidTorchMoveTask(float movementSpeed) {
        super(movementSpeed, 2);
    }

    @Override
    protected boolean shouldMoveTo(ServerLevel worldIn, EntityMaid entityIn, BlockPos pos) {
        BlockPos posUp = pos.above();
        if (worldIn.getMaxLocalRawBrightness(posUp) < LOW_BRIGHTNESS && entityIn.canPlaceBlock(posUp)) {
            BlockState stateUp = worldIn.getBlockState(posUp);
            return canSupportCenter(worldIn, pos, Direction.UP) && !stateUp.liquid();
        }
        return false;
    }

    @Override
    protected void start(ServerLevel worldIn, EntityMaid maid, long gameTimeIn) {
        if (!getTorchItem(maid).isEmpty()) {
            searchForDestination(worldIn, maid);
        }
    }

    private ItemStack getTorchItem(EntityMaid entityMaid) {
        CombinedResourceHandler<ItemVariant> itemHandler = entityMaid.getAvailableInv(false);
        return ItemsUtil.getStack(itemHandler, stack -> stack.getItem() == Items.TORCH);
    }
}
