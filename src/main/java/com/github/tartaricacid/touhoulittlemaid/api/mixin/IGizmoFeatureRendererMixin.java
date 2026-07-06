package com.github.tartaricacid.touhoulittlemaid.api.mixin;

import net.minecraft.client.renderer.gizmos.DrawableGizmoPrimitives;

import javax.annotation.Nullable;

public interface IGizmoFeatureRendererMixin {
    @Nullable
    DrawableGizmoPrimitives.Text tlm$getCurrentText();
}