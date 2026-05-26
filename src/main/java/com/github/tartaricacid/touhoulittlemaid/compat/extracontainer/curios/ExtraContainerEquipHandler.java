package com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.curios;

import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.ExtraContainerManager;
import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.MaidContainerCache;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ExtraContainerEquipHandler {

    public static void onCurioChange(ItemStack from, ItemStack to, TrinketSlotAccess reference, LivingEntity entity) {
        if (!(entity instanceof EntityMaid maid)) {
            return;
        }

        String slotType = reference.slotType().getId();
        int slotIndex = reference.index();

        boolean wasBackpack = ExtraContainerManager.isAnyBackpack(from);
        boolean isBackpack = ExtraContainerManager.isAnyBackpack(to);

        if (wasBackpack && !isBackpack) {
            MaidContainerCache.onUnequipped(maid, slotType, slotIndex);
        } else if (!wasBackpack && isBackpack) {
            MaidContainerCache.onEquipped(maid, to, slotType, slotIndex);
        }
    }
}