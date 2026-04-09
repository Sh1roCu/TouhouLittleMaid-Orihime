package com.github.tartaricacid.touhoulittlemaid.api.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

public class MaidEquipEvent {
    private final EntityMaid maid;
    private final EquipmentSlot slot;
    private final ItemStack stack;

    public MaidEquipEvent(EntityMaid maid, EquipmentSlot slot, ItemStack stack) {
        this.maid = maid;
        this.slot = slot;
        this.stack = stack;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public ItemStack getStack() {
        return stack;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void post(MaidEquipEvent event);
    }
}
