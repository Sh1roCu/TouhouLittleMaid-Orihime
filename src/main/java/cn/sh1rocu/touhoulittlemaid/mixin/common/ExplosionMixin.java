package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.event.ExplosionEvents;
import cn.sh1rocu.touhoulittlemaid.api.extension.IBlock;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Set;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Shadow
    @Final
    private Level level;

    @Inject(
            method = "finalizeExplosion",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"
            )
    )
    private void tlm$onBlockExploded(boolean spawnParticles, CallbackInfo ci, @Local(ordinal = 0) BlockPos pos, @Local(ordinal = 0) BlockState state) {
        if (state.getBlock() instanceof IBlock block) {
            block.tlm$onBlockExploded(state, this.level, pos, (Explosion) (Object) this);
        }
    }

    @Inject(method = "explode", at = @At(value = "NEW", target = "net/minecraft/world/phys/Vec3", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    public void tlm$onExplode(CallbackInfo ci, Set<BlockPos> blocks, int i, float j, int k, int l, int d, int q, int e, int r, List<Entity> list) {
        ExplosionEvents.DETONATE.invoker().onDetonate(this.level, (Explosion) (Object) this, list, j);
    }
}