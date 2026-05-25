package com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack;

import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class SmallBackpackContainer extends MaidMainContainer {
    public static final MenuType<SmallBackpackContainer> TYPE = new ExtendedMenuType<>(SmallBackpackContainer::new, ByteBufCodecs.INT);

    public SmallBackpackContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
    }

    @Override
    protected void addBackpackInv(Inventory inventory) {
        for (int i = 0; i < 6; i++) {
            addSlot(BackpackSlot.create(maid, 6 + i, 143 + 18 * i, 59));
        }
    }
}
