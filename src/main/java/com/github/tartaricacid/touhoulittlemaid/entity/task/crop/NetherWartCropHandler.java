package com.github.tartaricacid.touhoulittlemaid.entity.task.crop;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.CombinedInvWrapper;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.ItemHandlerHelper;
import com.github.tartaricacid.touhoulittlemaid.api.task.ISpecialCropHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class NetherWartCropHandler implements ISpecialCropHandler {
    @Override
    public boolean canHarvest(@NotNull EntityMaid maid, @NotNull BlockPos cropPos, @NotNull BlockState cropState) {
        return cropState.getValue(NetherWartBlock.AGE) >= NetherWartBlock.MAX_AGE;
    }

    @Override
    public void harvest(@NotNull EntityMaid maid, @NotNull BlockPos cropPos, @NotNull BlockState cropState, boolean isDestroyMode) {
        if (isDestroyMode) {
            maid.destroyBlock(cropPos);
        } else {
            CombinedInvWrapper availableInv = maid.getAvailableInv(false);

            ItemStack dropItemStack = new ItemStack(Items.NETHER_WART);
            ItemStack remindItemStack = ItemHandlerHelper.insertItemStacked(availableInv, dropItemStack, false);
            if (!remindItemStack.isEmpty()) {
                Block.popResource(maid.level, cropPos, remindItemStack);
            }
            maid.level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, cropPos, Block.getId(cropState));
            maid.level.setBlock(cropPos, Blocks.NETHER_WART.defaultBlockState(), Block.UPDATE_ALL);
            maid.level.gameEvent(maid, GameEvent.BLOCK_CHANGE, cropPos);
        }
    }
}