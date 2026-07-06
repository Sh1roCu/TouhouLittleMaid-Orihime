package com.github.tartaricacid.touhoulittlemaid.api.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.gizmos.TextGizmo;

import javax.annotation.Nullable;

public interface IDrawableGizmoPrimitives$GroupMixin {
    @Nullable
    Font.DisplayMode tlm$getDisplayMode(TextGizmo.Style style);

    void tlm$setDisplayMode(TextGizmo.Style style, Font.DisplayMode displayMode);
}
