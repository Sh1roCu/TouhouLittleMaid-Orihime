package com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack;

import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class EmptyBackpackContainer extends MaidMainContainer {
    public static final MenuType<EmptyBackpackContainer> TYPE = new ExtendedMenuType<>(EmptyBackpackContainer::new, ByteBufCodecs.INT);

    public EmptyBackpackContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
    }

    @Override
    protected void addBackpackInv(Inventory inventory) {
    }
}