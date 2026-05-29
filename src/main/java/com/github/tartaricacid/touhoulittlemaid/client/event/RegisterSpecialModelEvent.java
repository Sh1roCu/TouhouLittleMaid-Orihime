package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.TileEntityItemStackChairRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.TileEntityItemStackGarageKitRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.TileEntityItemStackPicnicBasketRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class RegisterSpecialModelEvent {
    public static void registerSpecialModelRenderers() {
        var mapper = SpecialModelRenderers.ID_MAPPER;
        mapper.put(TileEntityItemStackChairRenderer.CHAIR_ITEM_RENDERER, TileEntityItemStackChairRenderer.Unbaked.MAP_CODEC);
        mapper.put(TileEntityItemStackGarageKitRenderer.GARAGE_KIT_ITEM_RENDERER, TileEntityItemStackGarageKitRenderer.Unbaked.MAP_CODEC);
        mapper.put(TileEntityItemStackPicnicBasketRenderer.PICNIC_BASKET_ITEM_RENDERER, TileEntityItemStackPicnicBasketRenderer.Unbaked.MAP_CODEC);
    }
}
