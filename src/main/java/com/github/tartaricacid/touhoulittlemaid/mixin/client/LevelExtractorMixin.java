package com.github.tartaricacid.touhoulittlemaid.mixin.client;

import com.github.tartaricacid.touhoulittlemaid.client.animation.gecko.GeckoUpdateManager;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.geo.RenderContextManager;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.extract.LevelExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public class LevelExtractorMixin {
    @Inject(at = @At(value = "HEAD"), method = "extract(Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;F)V")
    private void beforeExtractLevel(DeltaTracker deltaTracker, Camera camera, float deltaPartialTick, CallbackInfo ci) {
        RenderContextManager.setRenderingLevel(true);
    }

    @Inject(at = @At(value = "RETURN"), method = "extract(Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;F)V")
    private void afterExtractLevel(DeltaTracker deltaTracker, Camera camera, float deltaPartialTick, CallbackInfo ci) {
        GeckoUpdateManager.updateRemaining(deltaTracker.getGameTimeDeltaPartialTick(true));
        RenderContextManager.setRenderingLevel(false);
    }
}
