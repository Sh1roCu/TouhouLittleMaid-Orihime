package com.github.tartaricacid.touhoulittlemaid.api.event.client;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.gecko.GeckoEntityMaidRenderer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * 为女仆实体添加 Layer 的事件
 */
public abstract class AddMaidLayerEvent {
    private final EntityRendererProvider.Context context;

    public AddMaidLayerEvent(EntityRendererProvider.Context context) {
        this.context = context;
    }

    public EntityRendererProvider.Context getContext() {
        return context;
    }

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

    public static class Legacy extends AddMaidLayerEvent {
        private final EntityMaidRenderer renderer;

        public interface LegacyCallback {
            void post(Legacy event);
        }

        public Legacy(EntityRendererProvider.Context context, EntityMaidRenderer renderer) {
            super(context);
            this.renderer = renderer;
        }

        public EntityMaidRenderer getRenderer() {
            return renderer;
        }
    }

    public static class Gecko extends AddMaidLayerEvent {
        private final GeckoEntityMaidRenderer renderer;

        public interface GeckoCallback {
            void post(Gecko event);
        }

        public Gecko(EntityRendererProvider.Context context, GeckoEntityMaidRenderer renderer) {
            super(context);
            this.renderer = renderer;
        }

        public GeckoEntityMaidRenderer getRenderer() {
            return renderer;
        }
    }
}
