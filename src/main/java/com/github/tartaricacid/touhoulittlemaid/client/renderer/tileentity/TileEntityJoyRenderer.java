package com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity;

import com.github.tartaricacid.touhoulittlemaid.tileentity.TileEntityJoy;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

public abstract class TileEntityJoyRenderer<T extends TileEntityJoy> implements BlockEntityRenderer<T> {
    // TODO
/*    @Override
    @Environment(EnvType.CLIENT)
    public AABB getRenderBoundingBox(T te) {
        return RenderHelper.getAABB(te.getWorldPosition().offset(-2, 0, -2), te.getWorldPosition().offset(2, 1, 2));
    }*/

    @Override
    public boolean shouldRenderOffScreen(T te) {
        return true;
    }
}
