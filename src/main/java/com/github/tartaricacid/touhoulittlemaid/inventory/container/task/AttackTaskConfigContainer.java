package com.github.tartaricacid.touhoulittlemaid.inventory.container.task;

import net.fabricmc.fabric.api.menu.v1.ExtendedScreenHandlerType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class AttackTaskConfigContainer extends TaskConfigContainer {
    public static final MenuType<AttackTaskConfigContainer> TYPE = new ExtendedScreenHandlerType<>(AttackTaskConfigContainer::new, ByteBufCodecs.INT);

    public AttackTaskConfigContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
    }
}
