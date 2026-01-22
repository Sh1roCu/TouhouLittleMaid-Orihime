package com.github.tartaricacid.touhoulittlemaid.compat.trinkets.event;

import com.google.common.collect.ImmutableSet;
import dev.emi.trinkets.api.SlotType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Set;

public class SlotModifiersUpdatedEvent {
    private final LivingEntity livingEntity;
    private final Set<SlotType> types;

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback c : callbacks) {
            c.post(event);
        }
    });

    public SlotModifiersUpdatedEvent(LivingEntity livingEntity, Set<SlotType> types) {
        this.livingEntity = livingEntity;
        this.types = types;
    }

    public LivingEntity getEntity() {
        return livingEntity;
    }

    public Set<SlotType> getTypes() {
        return ImmutableSet.copyOf(this.types);
    }

    public interface Callback {
        void post(SlotModifiersUpdatedEvent event);
    }
}
