package com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.accessories;

import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.SBackpackCompat;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.wispforest.accessories.api.events.SlotStateChange;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.item.ItemStack;

public class BackpackAccessoriesEquipEventHandler {

    public static void onCurioChange(ItemStack from, ItemStack to, SlotReference reference, SlotStateChange stateChange) {
        if (!(reference.entity() instanceof EntityMaid maid)) {
            return;
        }

        String slotType = reference.slotName();
        int slotIndex = reference.slot();

        boolean wasBackpack = SBackpackCompat.isBackpack(from);
        boolean isBackpack = SBackpackCompat.isBackpack(to);

        if (wasBackpack && !isBackpack) {
            MaidBackpackCache.onUnequipped(maid, slotType, slotIndex);
        } else if (!wasBackpack && isBackpack) {
            MaidBackpackCache.onEquipped(maid, slotType, slotIndex);
        }

        // 如果背包被替换为另一个背包，槽位引用不变，不需要更新
    }
}