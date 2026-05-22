package cn.sh1rocu.touhoulittlemaid.util.particle;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.TextureSheetParticleAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ParticleUtil {
    public static TerrainParticle updateSprite(TerrainParticle particle, BlockState state, @Nullable BlockPos pos) {
        if (pos != null)
            ((TextureSheetParticleAccessor) particle).tlm$setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(state));
        return particle;
    }
}
