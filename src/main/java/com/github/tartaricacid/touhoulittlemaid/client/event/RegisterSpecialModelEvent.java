package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.TileEntityItemStackChairRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.TileEntityItemStackGarageKitRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class RegisterSpecialModelEvent {
    public static void registerSpecialModelRenderers() {
        var mapper = SpecialModelRenderers.ID_MAPPER;
        mapper.put(TileEntityItemStackChairRenderer.CHAIR_ITEM_RENDERER, TileEntityItemStackChairRenderer.Unbaked.MAP_CODEC);
        mapper.put(TileEntityItemStackGarageKitRenderer.GARAGE_KIT_ITEM_RENDERER, TileEntityItemStackGarageKitRenderer.Unbaked.MAP_CODEC);
    }
}
