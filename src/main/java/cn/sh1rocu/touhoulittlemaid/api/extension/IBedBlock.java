package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public interface IBedBlock {
    default boolean tlm$isBed(BlockState state, BlockGetter world, BlockPos pos, @Nullable Entity entity) {
        return this instanceof BedBlock;
    }
}
