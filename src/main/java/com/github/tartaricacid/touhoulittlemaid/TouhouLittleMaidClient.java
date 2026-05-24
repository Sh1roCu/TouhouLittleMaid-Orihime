package com.github.tartaricacid.touhoulittlemaid;

import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;

public class TouhouLittleMaidClient {
    public static void setup() {
        registerClientOnly();
    }

    private static void registerClientOnly() {
        // 这个仅用于客户端，所以不需要在服务端注册
        ClientEntityEvents.ENTITY_LOAD.register((clientEntity, level) -> {
            if (!clientEntity.level.isClientSide())
                return;
            if (clientEntity instanceof EntityMaid maid) {
                clientEntity.setAttached(GeckoMaidEntity.TYPE, new GeckoMaidEntity<>(maid));
            }
        });
    }
}
