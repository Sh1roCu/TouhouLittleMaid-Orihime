package com.github.tartaricacid.touhoulittlemaid.event.maid;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidDeathEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.favorability.FavorabilityManager;
import com.github.tartaricacid.touhoulittlemaid.entity.favorability.Type;

public class MaidDeathFavorability {
    public static void onDeath(MaidDeathEvent event) {
        FavorabilityManager manager = event.getMaid().getFavorabilityManager();
        manager.apply(Type.DEATH);
    }
}
