package com.github.tartaricacid.touhoulittlemaid.mixin.client;

import com.github.tartaricacid.touhoulittlemaid.api.mixin.IDrawableGizmoPrimitives$GroupMixin;
import com.github.tartaricacid.touhoulittlemaid.api.mixin.IDrawableGizmoPrimitivesMixin;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.gizmos.DrawableGizmoPrimitives;
import net.minecraft.gizmos.TextGizmo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DrawableGizmoPrimitives.class)
public abstract class DrawableGizmoPrimitivesMixin implements IDrawableGizmoPrimitivesMixin {
    @Shadow
    protected abstract DrawableGizmoPrimitives.Group getGroup(int color);

    @Override
    public void tlm$setDisplayMode(TextGizmo.Style style, Font.DisplayMode displayMode) {
        ((IDrawableGizmoPrimitives$GroupMixin) (Object) this.getGroup(style.color())).tlm$setDisplayMode(style, displayMode);
    }
}
