package com.github.tartaricacid.touhoulittlemaid.inventory.handler;


import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;

public class AltarItemHandler extends ItemStacksResourceHandler {
    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
