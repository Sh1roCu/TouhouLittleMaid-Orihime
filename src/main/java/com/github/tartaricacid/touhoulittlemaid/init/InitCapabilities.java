package com.github.tartaricacid.touhoulittlemaid.init;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.minecraft.core.Direction;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.getResourceLocation;

public class InitCapabilities {
    public static final EntityApiLookup<IItemHandler, Direction> ENTITY_ITEM = EntityApiLookup.get(getResourceLocation("entity_item"), IItemHandler.class, Direction.class);

    public static final EntityApiLookup<IItemHandler, Direction> HAND_ITEM = EntityApiLookup.get(getResourceLocation("hand_item"), IItemHandler.class, Direction.class);
    public static final EntityApiLookup<IItemHandler, Direction> ARMOR_ITEM = EntityApiLookup.get(getResourceLocation("armor_item"), IItemHandler.class, Direction.class);

    public static void registerGenericItemHandlers() {
        HAND_ITEM.registerForType((maid, direction) -> maid.getHandsInvWrapper(), InitEntities.MAID);
        ARMOR_ITEM.registerForType((maid, direction) -> maid.getArmorInvWrapper(), InitEntities.MAID);

        ENTITY_ITEM.registerForType((maid, direction) -> maid.getAllInv(), InitEntities.MAID);
    }
}
