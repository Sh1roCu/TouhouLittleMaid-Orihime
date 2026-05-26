package cn.sh1rocu.touhoulittlemaid.api.extension.client;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public interface IClientBlockExtensions {
    boolean addHitEffects(BlockState state, Level world, HitResult target, ParticleEngine manager);

    boolean addDestroyEffects(BlockState state, Level Level, BlockPos pos, ParticleEngine engine);
}
