package com.github.tartaricacid.touhoulittlemaid.api.event.client;

import com.github.tartaricacid.touhoulittlemaid.client.model.bedrock.EntityMaidModel;
import com.github.tartaricacid.touhoulittlemaid.client.resource.pojo.MaidModelInfo;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * 读取自定义女仆模型包时触发的事件，主要用于给全体自定义模型包添加额外内容（比如添加额外的兼容动画）
 */
public abstract class MaidPackLoaderEvent {
    private final MaidModelInfo info;

    public static Event<Legacy.LegacyCallback> LEGACY = EventFactory.createArrayBacked(Legacy.LegacyCallback.class, callbacks -> event -> {
        for (Legacy.LegacyCallback callback : callbacks) {
            callback.post(event);
        }
    });
    public static Event<Gecko.GeckoCallback> GECKO = EventFactory.createArrayBacked(Gecko.GeckoCallback.class, callbacks -> event -> {
        for (Gecko.GeckoCallback callback : callbacks) {
            callback.post(event);
        }
    });

    public MaidPackLoaderEvent(MaidModelInfo info) {
        this.info = info;
    }

    public MaidModelInfo getInfo() {
        return info;
    }

    /**
     * 传统模型
     */
    public static class Legacy extends MaidPackLoaderEvent {
        private final EntityMaidModel model;

        public interface LegacyCallback {
            void post(Legacy event);
        }

        public Legacy(MaidModelInfo info, EntityMaidModel model) {
            super(info);
            this.model = model;
        }

        public EntityMaidModel getModel() {
            return model;
        }
    }

    /**
     * Gecko Lib 基岩版模型
     */
    public static class Gecko extends MaidPackLoaderEvent {
        public Gecko(MaidModelInfo info) {
            super(info);
        }

        public interface GeckoCallback {
            void post(Gecko event);
        }
    }
}
