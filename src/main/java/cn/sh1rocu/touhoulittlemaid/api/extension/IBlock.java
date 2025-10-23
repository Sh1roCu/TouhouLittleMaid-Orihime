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
        return !state.shouldSpawnParticlesOnBreak();
    }

    default void tlm$onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        ((Block) this).wasExploded(world, pos, explosion);
    }
}
