package com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.accessories;

import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.ExtraContainerManager;
import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.MaidContainerCache;
import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.SBackpackCompat;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.wispforest.accessories.api.events.SlotStateChange;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.item.ItemStack;

public class ExtraContainerEquipHandler {

    public static void onCurioChange(ItemStack from, ItemStack to, SlotReference reference, SlotStateChange stateChange) {
        if (!(reference.entity() instanceof EntityMaid maid)) {
            return;
        }

        String slotType = reference.slotName();
        int slotIndex = reference.slot();

        boolean wasBackpack = ExtraContainerManager.isAnyBackpack(from);
        boolean isBackpack = ExtraContainerManager.isAnyBackpack(to);

        if (wasBackpack && !isBackpack) {
            MaidContainerCache.onUnequipped(maid, slotType, slotIndex);
        } else if (!wasBackpack && isBackpack) {
            MaidContainerCache.onEquipped(maid, to, slotType, slotIndex);
        }
    }
}