package com.github.tartaricacid.touhoulittlemaid.inventory.handler;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import org.jetbrains.annotations.NotNull;

public class AltarItemHandler extends ItemStacksResourceHandler {
    public AltarItemHandler() {
        super(1);
    }

    @Override
    protected int getCapacity(int index, @NotNull ItemVariant resource) {
        return 1;
    }
}
