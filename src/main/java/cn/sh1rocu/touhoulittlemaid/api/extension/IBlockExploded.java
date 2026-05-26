package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockExploded {
    default void tlm$onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        ((Block) this).wasExploded(level, pos, explosion);
    }
}
