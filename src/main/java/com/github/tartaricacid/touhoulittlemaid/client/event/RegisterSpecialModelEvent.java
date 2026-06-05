package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.blockentity.BlockEntityItemStackChairRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.blockentity.BlockEntityItemStackGarageKitRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.blockentity.BlockEntityItemStackPicnicBasketRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class RegisterSpecialModelEvent {
    public static void registerSpecialModelRenderers() {
        var mapper = SpecialModelRenderers.ID_MAPPER;
        mapper.put(BlockEntityItemStackChairRenderer.CHAIR_ITEM_RENDERER, BlockEntityItemStackChairRenderer.Unbaked.MAP_CODEC);
        mapper.put(BlockEntityItemStackGarageKitRenderer.GARAGE_KIT_ITEM_RENDERER, BlockEntityItemStackGarageKitRenderer.Unbaked.MAP_CODEC);
        mapper.put(BlockEntityItemStackPicnicBasketRenderer.PICNIC_BASKET_ITEM_RENDERER, BlockEntityItemStackPicnicBasketRenderer.Unbaked.MAP_CODEC);
    }
}
