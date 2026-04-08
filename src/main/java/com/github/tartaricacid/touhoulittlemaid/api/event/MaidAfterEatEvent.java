package com.github.tartaricacid.touhoulittlemaid.api.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.ItemStack;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

public class MaidAfterEatEvent {
    private final EntityMaid maid;
    private final ItemStack foodAfterEat;

    public MaidAfterEatEvent(EntityMaid maid, ItemStack foodAfterEat) {
        this.maid = maid;
        this.foodAfterEat = foodAfterEat;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public ItemStack getFoodAfterEat() {
        return foodAfterEat;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void post(MaidAfterEatEvent event);
    }
}
