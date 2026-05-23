package com.github.tartaricacid.touhoulittlemaid.init;

import cn.sh1rocu.touhoulittlemaid.util.transfer.LivingEntityEquipmentWrapper;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.BaubleItemHandler;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.MaidBackpackHandler;
import net.minecraft.world.entity.EquipmentSlot;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

import static com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil.getIdentifier;

public class InitCapabilities implements EntityComponentInitializer {
    public static final ComponentKey<LivingEntityEquipmentWrapper.EquipmentTypeWrapper> MAID_HAND = ComponentRegistry.getOrCreate(getIdentifier("maid_hand"), LivingEntityEquipmentWrapper.EquipmentTypeWrapper.class);
    public static final ComponentKey<LivingEntityEquipmentWrapper.EquipmentTypeWrapper> MAID_ARMOR = ComponentRegistry.getOrCreate(getIdentifier("maid_armor"), LivingEntityEquipmentWrapper.EquipmentTypeWrapper.class);
    public static final ComponentKey<MaidBackpackHandler> MAID_INV = ComponentRegistry.getOrCreate(getIdentifier("maid_inv"), MaidBackpackHandler.class);
    public static final ComponentKey<BaubleItemHandler> MAID_BAUBLE = ComponentRegistry.getOrCreate(getIdentifier("maid_bauble"), BaubleItemHandler.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(EntityMaid.class, MAID_HAND, maid ->
                (LivingEntityEquipmentWrapper.EquipmentTypeWrapper) LivingEntityEquipmentWrapper.of(maid, EquipmentSlot.Type.HAND));
        registry.registerFor(EntityMaid.class, MAID_ARMOR, maid ->
                (LivingEntityEquipmentWrapper.EquipmentTypeWrapper) LivingEntityEquipmentWrapper.of(maid, EquipmentSlot.Type.HUMANOID_ARMOR));
        registry.registerFor(EntityMaid.class, MAID_INV, maid -> new MaidBackpackHandler(36, maid));
        registry.registerFor(EntityMaid.class, MAID_BAUBLE, maid -> new BaubleItemHandler(EntityMaid.BAUBLE_INV_SIZE));
    }
}
