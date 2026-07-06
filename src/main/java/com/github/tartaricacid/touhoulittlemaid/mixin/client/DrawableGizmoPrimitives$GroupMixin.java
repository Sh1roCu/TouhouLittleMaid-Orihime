package com.github.tartaricacid.touhoulittlemaid.mixin.client;

import com.github.tartaricacid.touhoulittlemaid.api.mixin.IDrawableGizmoPrimitives$GroupMixin;
import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.gizmos.DrawableGizmoPrimitives;
import net.minecraft.gizmos.TextGizmo;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(targets = "net.minecraft.client.renderer.gizmos.DrawableGizmoPrimitives$Group")
public abstract class DrawableGizmoPrimitives$GroupMixin implements IDrawableGizmoPrimitives$GroupMixin {
    @Unique
    private final Map<TextGizmo.Style, Font.DisplayMode> tlm$displayModes = Maps.newHashMap();

    @Override
    public Font.DisplayMode tlm$getDisplayMode(TextGizmo.Style style) {
        return tlm$displayModes.get(style);
    }

    @Override
    public void tlm$setDisplayMode(TextGizmo.Style style, Font.DisplayMode displayMode) {
        tlm$displayModes.put(style, displayMode);
    }

    @WrapOperation(
            method = "renderTexts",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;drawInBatch(Ljava/lang/String;FFIZLorg/joml/Matrix4fc;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)V"
            )
    )
    private void tlm$modifyDisplayMode(
            Font instance, String str, float x, float y, int color, boolean dropShadow, Matrix4fc pose, MultiBufferSource bufferSource,
            Font.DisplayMode oriMode, int backgroundColor, int packedLightCoords,
            Operation<Void> original,
            @Local DrawableGizmoPrimitives.Text text
    ) {
        Font.DisplayMode mode = tlm$getDisplayMode(text.style());
        original.call(instance, str, x, y, color, dropShadow, pose, bufferSource, mode == null ? oriMode : mode, backgroundColor, packedLightCoords);
    }
}
