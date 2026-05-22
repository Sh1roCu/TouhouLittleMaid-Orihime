package com.github.tartaricacid.touhoulittlemaid.compat.ysm.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class YsmMaidClientTickEvent {
    private final EntityMaid maid;

    public YsmMaidClientTickEvent(EntityMaid maid) {
        this.maid = maid;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.post(event);
                }
            }
    );

    public interface Callback {
        void post(YsmMaidClientTickEvent event);
    }
}
