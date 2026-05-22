package com.github.tartaricacid.touhoulittlemaid.api.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

/**
 * 这个事件在女仆被驯服时触发。
 * 事件的触发时机在 EntityMaid#tameMaid 方法中。
 * 事件无法取消
 */
public class MaidTamedEvent {
    private final EntityMaid maid;
    private final Player player;

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.onMaidTamed(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    /**
     * 当女仆是通过主人转换工具强制转换主人时，此值为 true
     */

    public interface Callback {
        void onMaidTamed(MaidTamedEvent event);
    }

    private final boolean isOwnerConversion;

    public MaidTamedEvent(EntityMaid maid, Player player, boolean isOwnerConversion) {
        this.maid = maid;
        this.player = player;
        this.isOwnerConversion = isOwnerConversion;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isOwnerConversion() {
        return isOwnerConversion;
    }
}