package com.github.tartaricacid.touhoulittlemaid;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TouhouLittleMaidClient {
    public static void setup() {
        registerClientOnly();
    }

    private static void registerClientOnly() {
        // 这个仅用于客户端，所以不需要在服务端注册

        // 弃用，改为使用mixin在实体生成时赋予
//        EntityJoinLevelEvent.CALLBACK.register(event -> {
//            Entity clientEntity = event.getEntity();
//            if (!clientEntity.level.isClientSide())
//                return;
//            if (clientEntity instanceof Mob mob) {
//                IMaid maid = IMaid.convert(mob);
//                if (maid != null) {
//                    clientEntity.setAttached(GeckoMaidEntity.TYPE, new GeckoMaidEntity(mob, maid));
//                }
//            }
//        });
    }
}
