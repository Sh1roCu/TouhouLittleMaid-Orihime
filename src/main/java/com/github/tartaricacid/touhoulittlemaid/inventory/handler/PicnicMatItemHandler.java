package com.github.tartaricacid.touhoulittlemaid.inventory.handler;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.component.DataComponents;

public class PicnicMatItemHandler extends ItemStacksResourceHandler {
    public PicnicMatItemHandler() {
        super(9);
    }

    @Override
    public boolean isValid(int index, ItemVariant resource) {
        if (resource.isBlank()) {
            return false;
        }
        return resource.has(DataComponents.FOOD);
    }
}
