package com.github.tartaricacid.touhoulittlemaid.event.maid;

import cn.sh1rocu.touhoulittlemaid.api.event.FarmlandTrampleEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;

public class MaidFarmlandTrample {
    public static void onFarmlandTrample(FarmlandTrampleEvent event) {
        if (event.getEntity() instanceof EntityMaid) {
            event.setCanceled(true);
        }
    }
}
