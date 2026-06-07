package com.github.tartaricacid.touhoulittlemaid.inventory.handler;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

public class ShrineItemHandler extends ItemStacksResourceHandler {
    public ShrineItemHandler() {
        super(1);
    }

    @Override
    public boolean isValid(int index, ItemVariant resource) {
        return resource.is(InitItems.FILM);
    }

    @Override
    protected int getCapacity(int index, ItemVariant resource) {
        return 1;
    }
}
