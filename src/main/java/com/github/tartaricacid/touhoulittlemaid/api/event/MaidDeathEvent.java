package com.github.tartaricacid.touhoulittlemaid.api.event;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

public class MaidDeathEvent extends CancellableEvent {
    private final EntityMaid maid;
    private final DamageSource source;

    public MaidDeathEvent(EntityMaid maid, DamageSource source) {
        this.maid = maid;
        this.source = source;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public DamageSource getSource() {
        return source;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void post(MaidDeathEvent event);
    }
}
