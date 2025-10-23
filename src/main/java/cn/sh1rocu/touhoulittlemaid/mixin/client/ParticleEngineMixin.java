package cn.sh1rocu.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.api.extension.IBlock;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
    @Shadow
    protected ClientLevel level;

    @ModifyExpressionValue(
            method = "destroy",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;shouldSpawnParticlesOnBreak()Z"
            )
    )
    private boolean tlm$addDestroyEffects(boolean original, BlockPos blockPos, BlockState blockState) {
        if (blockState.getBlock() instanceof IBlock block) {
            if (block.tlm$addDestroyEffects(blockState, this.level, blockPos, (ParticleEngine) (Object) this)) {
                return false;
            }
        }
        return original;
    }
}