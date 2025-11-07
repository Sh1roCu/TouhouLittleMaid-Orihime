package com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack;

import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class BigBackpackContainer extends MaidMainContainer {
    public static final MenuType<BigBackpackContainer> TYPE = new ExtendedScreenHandlerType<>(
            (i, inventory, buf) -> new BigBackpackContainer(i, inventory, buf.readInt()));

    public BigBackpackContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
    }

    @Override
    protected void addBackpackInv(Inventory inventory) {
        for (int i = 0; i < 6; i++) {
            addSlot(new BackpackSlot(maid, 6 + i, 143 + 18 * i, 59));
        }
        for (int i = 0; i < 6; i++) {
            addSlot(new BackpackSlot(maid, 12 + i, 143 + 18 * i, 82));
        }
        for (int i = 0; i < 6; i++) {
            addSlot(new BackpackSlot(maid, 18 + i, 143 + 18 * i, 100));
        }
        for (int i = 0; i < 6; i++) {
            addSlot(new BackpackSlot(maid, 24 + i, 143 + 18 * i, 123));
        }
        for (int i = 0; i < 6; i++) {
            addSlot(new BackpackSlot(maid, 30 + i, 143 + 18 * i, 141));
        }
    }
}
