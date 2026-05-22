package com.github.tartaricacid.touhoulittlemaid.api.event;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

/**
 * 这个事件会在女仆播放音效时进行触发。<br>
 * 这个事件是 {@link #isCanceled() canceled}，如果取消，那么女仆播放音效会被取消。<br>
 * 该事件在服务端触发。
 */

public class MaidPlaySoundEvent extends CancellableEvent {
    private final EntityMaid maid;

    public MaidPlaySoundEvent(EntityMaid maid) {
        this.maid = maid;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public static Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> (event) -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void post(MaidPlaySoundEvent e);
    }
}
