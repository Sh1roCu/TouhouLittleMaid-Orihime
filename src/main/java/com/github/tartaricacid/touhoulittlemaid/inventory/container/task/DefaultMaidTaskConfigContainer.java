package com.github.tartaricacid.touhoulittlemaid.inventory.container.task;

import net.fabricmc.fabric.api.menu.v1.ExtendedScreenHandlerType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class DefaultMaidTaskConfigContainer extends TaskConfigContainer {
    public static final MenuType<DefaultMaidTaskConfigContainer> TYPE = new ExtendedScreenHandlerType<>(DefaultMaidTaskConfigContainer::new, ByteBufCodecs.INT);

    public DefaultMaidTaskConfigContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
    }
}
