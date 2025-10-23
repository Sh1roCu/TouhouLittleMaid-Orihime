package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public interface IBlock {
    @Environment(EnvType.CLIENT)
    boolean tlm$addHitEffects(BlockState state, Level world, HitResult target, ParticleEngine manager);

    @Environment(EnvType.CLIENT)
    default boolean tlm$addDestroyEffects(BlockState state, Level Level, BlockPos pos, ParticleEngine engine) {
        return !state.shouldSpawnTerrainParticles();
    }

    default void tlm$onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        ((Block) this).wasExploded(level, pos, explosion);
    }
}
