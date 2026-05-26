package com.github.tartaricacid.touhoulittlemaid;

public class TouhouLittleMaidClient {
    public static void setup() {
        registerClientOnly();
    }

    private static void registerClientOnly() {
        // 这个仅用于客户端，所以不需要在服务端注册

        // 弃用，改为使用mixin在实体生成时赋予
//        ClientEntityEvents.ENTITY_LOAD.register((clientEntity, level) -> {
//            if (!clientEntity.level.isClientSide())
//                return;
//            if (clientEntity instanceof EntityMaid maid) {
//                clientEntity.setAttached(GeckoMaidEntity.TYPE, new GeckoMaidEntity<>(maid));
//            }
//        });
    }
}
