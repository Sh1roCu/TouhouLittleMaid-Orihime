package com.github.tartaricacid.touhoulittlemaid.item.bauble;

import com.github.tartaricacid.touhoulittlemaid.api.bauble.IMaidBauble;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidPlaySoundEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.util.ItemsUtil;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.HIGH;

public class MuteBauble implements IMaidBauble {
    public MuteBauble() {
        MaidPlaySoundEvent.CALLBACK.register(HIGH, this::onMaidPlaySound);
    }

    public void onMaidPlaySound(MaidPlaySoundEvent event) {
        EntityMaid maid = event.getMaid();
        int slot = ItemsUtil.getBaubleSlotInMaid(maid, this);
        if (slot >= 0) {
            event.setCanceled(true);
        }
    }
}
