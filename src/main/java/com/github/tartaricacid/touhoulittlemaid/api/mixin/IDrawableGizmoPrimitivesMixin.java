package com.github.tartaricacid.touhoulittlemaid.api.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.gizmos.TextGizmo;

public interface IDrawableGizmoPrimitivesMixin {
    void tlm$setDisplayMode(TextGizmo.Style style, Font.DisplayMode displayMode);
}
