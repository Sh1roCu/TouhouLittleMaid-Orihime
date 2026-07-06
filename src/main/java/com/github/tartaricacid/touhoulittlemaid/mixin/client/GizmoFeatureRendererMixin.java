package com.github.tartaricacid.touhoulittlemaid.mixin.client;

import com.github.tartaricacid.touhoulittlemaid.api.mixin.IDrawableGizmoPrimitives$TextMixin;
import com.github.tartaricacid.touhoulittlemaid.api.mixin.IGizmoFeatureRendererMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.renderer.feature.GizmoFeatureRenderer;
import net.minecraft.client.renderer.gizmos.DrawableGizmoPrimitives;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(GizmoFeatureRenderer.class)
public abstract class GizmoFeatureRendererMixin implements IGizmoFeatureRendererMixin {
    @Unique
    private final ThreadLocal<DrawableGizmoPrimitives.Text> tlm$cachedText = new ThreadLocal<>();

    @Inject(
            method = "buildTexts",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font$PreparedText;visit(Lnet/minecraft/client/gui/Font$GlyphVisitor;)V")
    )
    private void tlm$captureCurrentText(List<DrawableGizmoPrimitives.Text> texts, CameraRenderState camera, Font font, CallbackInfo ci,
                                        @Local DrawableGizmoPrimitives.Text text) {
        tlm$cachedText.set(text);
    }

    @Nullable
    @Unique
    public DrawableGizmoPrimitives.Text tlm$getCurrentText() {
        return tlm$cachedText.get();
    }

    @Mixin(targets = "net.minecraft.client.renderer.feature.GizmoFeatureRenderer$1")
    public static class GlyphVisitorMixin {
        @Shadow(remap = false)
        @Final
        GizmoFeatureRenderer this$0;

        @WrapOperation(
                method = "acceptRenderable",
                at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/client/gui/font/TextRenderable;renderType(Lnet/minecraft/client/gui/Font$DisplayMode;)Lnet/minecraft/client/renderer/rendertype/RenderType;"
                )

        )
        private RenderType tlm$modifyDisplayMode(
                TextRenderable instance, Font.DisplayMode oriMode, Operation<RenderType> original
        ) {
            DrawableGizmoPrimitives.Text text = ((IGizmoFeatureRendererMixin) this.this$0).tlm$getCurrentText();
            if (text != null) {
                Font.DisplayMode mode = ((IDrawableGizmoPrimitives$TextMixin) (Object) text).tlm$getDisplayMode();
                return original.call(instance, mode == null ? oriMode : mode);
            } else {
                return original.call(instance, oriMode);
            }
        }
    }
}
