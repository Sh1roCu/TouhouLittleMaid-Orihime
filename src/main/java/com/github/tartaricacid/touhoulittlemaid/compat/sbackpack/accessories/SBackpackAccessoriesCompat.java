package com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.accessories;

import cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidPickupEvent;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidRequestItemEvent;
import com.google.common.collect.Maps;
import io.wispforest.accessories.api.events.AccessoryChangeCallback;
import net.minecraft.Util;

import java.util.Map;

public class SBackpackAccessoriesCompat {
    private static final int DEFAULT_PRIORITY = 100;
    /**
     * Accessories 槽位中背包优先级，决定背包的拾取顺序
     * 优先交互 back 槽位中的背包
     */
    private static final Map<String, Integer> SLOT_PRIORITY = Util.make(Maps.newHashMap(), map -> {
        map.put("back", 0);
        map.put("all", 1);
    });

    public static void init() {
        AccessoryChangeCallback.EVENT.register(BackpackAccessoriesEquipEventHandler::onCurioChange);
        MaidPickupEvent.ITEM_RESULT_PRE.register(TouhouLittleMaidFabric.HIGH, BackpackPickupEventHandler::onMaidPickupPre);
        MaidRequestItemEvent.EVENT.register(BackpackRequestItemEventHandler::onMaidRequestItem);
    }

    public static int getSlotPriority(String slotType) {
        return SLOT_PRIORITY.getOrDefault(slotType, DEFAULT_PRIORITY);
    }
}