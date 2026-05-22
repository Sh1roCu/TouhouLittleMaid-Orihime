package com.github.tartaricacid.touhoulittlemaid.event;


import com.github.tartaricacid.touhoulittlemaid.init.InitTrigger;
import net.minecraft.server.level.ServerPlayer;

public final class EnterServerEvent {
    public static void onAttachCapabilityEvent(ServerPlayer serverPlayer) {
        InitTrigger.GIVE_SMART_SLAB_CONFIG.trigger(serverPlayer);
        InitTrigger.GIVE_PATCHOULI_BOOK_CONFIG.trigger(serverPlayer);
    }
}
