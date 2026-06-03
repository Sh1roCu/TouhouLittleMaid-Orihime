package com.github.tartaricacid.touhoulittlemaid.init;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.Direction;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.modLoc;

public class InitCapabilities {
    public static final EntityApiLookup<ResourceHandler<ItemVariant>, Direction> ENTITY_ITEM = EntityApiLookup.get(modLoc("entity_item"), ResourceHandler.asClass(), Direction.class);

    public static final EntityApiLookup<ResourceHandler<ItemVariant>, Direction> HAND_ITEM = EntityApiLookup.get(modLoc("hand_item"), ResourceHandler.asClass(), Direction.class);
    public static final EntityApiLookup<ResourceHandler<ItemVariant>, Direction> ARMOR_ITEM = EntityApiLookup.get(modLoc("armor_item"), ResourceHandler.asClass(), Direction.class);

    public static void register() {
        HAND_ITEM.registerForType((maid, direction) -> maid.getHandsInvWrapper(), InitEntities.MAID);
        ARMOR_ITEM.registerForType((maid, direction) -> maid.getArmorInvWrapper(), InitEntities.MAID);

        ENTITY_ITEM.registerForType((maid, direction) -> maid.getAllInv(), InitEntities.MAID);
    }
}
