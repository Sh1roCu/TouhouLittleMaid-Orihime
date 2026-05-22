package com.github.tartaricacid.touhoulittlemaid.init;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.entity.EntityArmorInvWrapper;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.BaubleItemHandler;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.MaidBackpackHandler;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.MaidHandsInvWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.getIdentifier;

public class InitCapabilities implements EntityComponentInitializer {
    public static final ComponentKey<MaidHandsInvWrapper> MAID_HAND = ComponentRegistry.getOrCreate(getIdentifier("maid_hand"), MaidHandsInvWrapper.class);
    public static final ComponentKey<EntityArmorInvWrapper> MAID_ARMOR = ComponentRegistry.getOrCreate(getIdentifier("maid_armor"), EntityArmorInvWrapper.class);
    public static final ComponentKey<MaidBackpackHandler> MAID_INV = ComponentRegistry.getOrCreate(getIdentifier("maid_inv"), MaidBackpackHandler.class);
    public static final ComponentKey<BaubleItemHandler> MAID_BAUBLE = ComponentRegistry.getOrCreate(getIdentifier("maid_bauble"), BaubleItemHandler.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(EntityMaid.class, MAID_HAND, MaidHandsInvWrapper::new);
        registry.registerFor(EntityMaid.class, MAID_ARMOR, EntityArmorInvWrapper::new);
        registry.registerFor(EntityMaid.class, MAID_INV, maid -> new MaidBackpackHandler(36, maid));
        registry.registerFor(EntityMaid.class, MAID_BAUBLE, maid -> new BaubleItemHandler(EntityMaid.BAUBLE_INV_SIZE));
    }
}
