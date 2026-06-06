package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.item.ChairItemRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.item.GarageKitItemRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class RegisterSpecialModelEvent {
    public static void registerSpecialModelRenderers() {
        var mapper = SpecialModelRenderers.ID_MAPPER;
        mapper.put(ChairItemRenderer.CHAIR_ITEM_RENDERER, ChairItemRenderer.Unbaked.MAP_CODEC);
        mapper.put(GarageKitItemRenderer.GARAGE_KIT_ITEM_RENDERER, GarageKitItemRenderer.Unbaked.MAP_CODEC);
    }
}
