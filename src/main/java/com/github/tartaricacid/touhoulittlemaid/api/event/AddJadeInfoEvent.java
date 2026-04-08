package com.github.tartaricacid.touhoulittlemaid.api.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

public class AddJadeInfoEvent {
    private final EntityMaid maid;
    private final ITooltip tooltip;
    private final IPluginConfig pluginConfig;

    public AddJadeInfoEvent(EntityMaid maid, ITooltip tooltip, IPluginConfig pluginConfig) {
        this.maid = maid;
        this.tooltip = tooltip;
        this.pluginConfig = pluginConfig;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public ITooltip getTooltip() {
        return tooltip;
    }

    public IPluginConfig getPluginConfig() {
        return pluginConfig;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void post(AddJadeInfoEvent event);
    }
}
