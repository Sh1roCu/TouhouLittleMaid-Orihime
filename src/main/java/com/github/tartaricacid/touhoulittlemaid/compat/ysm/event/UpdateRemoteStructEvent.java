package com.github.tartaricacid.touhoulittlemaid.compat.ysm.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class UpdateRemoteStructEvent {
    private final EntityMaid maid;
    private final Object2FloatOpenHashMap<String> roamingVars;

    public static Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, (callbacks) -> (event) -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public UpdateRemoteStructEvent(EntityMaid maid, Object2FloatOpenHashMap<String> roamingVars) {
        this.maid = maid;
        this.roamingVars = roamingVars;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public Object2FloatOpenHashMap<String> getRoamingVars() {
        return roamingVars;
    }

    public interface Callback {
        void post(UpdateRemoteStructEvent event);
    }
}
