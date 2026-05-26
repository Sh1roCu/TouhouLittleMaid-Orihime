package cn.sh1rocu.touhoulittlemaid.util.particle;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.ParticleAccessor;
import cn.sh1rocu.touhoulittlemaid.mixin.accessor.SingleQuadParticleAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ParticleUtil {
    public static TerrainParticle updateSprite(TerrainParticle particle, BlockState state, @Nullable BlockPos pos) {
        if (pos != null)
            ((SingleQuadParticleAccessor) particle).tlm$setSprite(
                    Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(state, ((ParticleAccessor) particle).tlm$getLevel(), pos)
                            .sprite());
        return particle;
    }
}
