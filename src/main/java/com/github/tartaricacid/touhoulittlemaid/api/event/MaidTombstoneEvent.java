package com.github.tartaricacid.touhoulittlemaid.api.event;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityTombstone;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

public class MaidTombstoneEvent extends CancellableEvent {
    private final EntityMaid maid;
    private final EntityTombstone tombstone;

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.onTombstone(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void onTombstone(MaidTombstoneEvent event);
    }

    public MaidTombstoneEvent(EntityMaid maid, EntityTombstone tombstone) {
        this.maid = maid;
        this.tombstone = tombstone;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public EntityTombstone getTombstone() {
        return tombstone;
    }
}