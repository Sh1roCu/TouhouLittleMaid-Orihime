package com.github.tartaricacid.touhoulittlemaid.init;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.Direction;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public class InitCapabilities {
    public static final EntityApiLookup<ResourceHandler<ItemVariant>, Direction> ENTITY_ITEM = EntityApiLookup.get(getResourceLocation("entity_item"), ResourceHandler.asClass(), Direction.class);

    public static final EntityApiLookup<ResourceHandler<ItemVariant>, Direction> HAND_ITEM = EntityApiLookup.get(getResourceLocation("hand_item"), ResourceHandler.asClass(), Direction.class);
    public static final EntityApiLookup<ResourceHandler<ItemVariant>, Direction> ARMOR_ITEM = EntityApiLookup.get(getResourceLocation("armor_item"), ResourceHandler.asClass(), Direction.class);

    public static void registerGenericItemHandlers() {
        HAND_ITEM.registerForType((maid, direction) -> maid.getHandsInvWrapper(), InitEntities.MAID);
        ARMOR_ITEM.registerForType((maid, direction) -> maid.getArmorInvWrapper(), InitEntities.MAID);

        ENTITY_ITEM.registerForType((maid, direction) -> maid.getAllInv(), InitEntities.MAID);
    }
}
