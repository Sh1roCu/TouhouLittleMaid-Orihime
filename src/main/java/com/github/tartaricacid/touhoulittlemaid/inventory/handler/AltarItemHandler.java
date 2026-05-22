package com.github.tartaricacid.touhoulittlemaid.inventory.handler;


import cn.sh1rocu.touhoulittlemaid.util.itemhandler.ItemStackHandler;

public class AltarItemHandler extends ItemStackHandler {
    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
