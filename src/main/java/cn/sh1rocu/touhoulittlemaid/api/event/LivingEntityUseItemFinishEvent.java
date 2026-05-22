package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LivingEntityUseItemFinishEvent {
    private final LivingEntity entity;
    private final ItemStack item;
    private int duration;
    private ItemStack result;

    public LivingEntity getEntity() {
        return entity;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ItemStack getResultStack() {
        return result;
    }

    public void setResultStack(ItemStack result) {
        this.result = result;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks)
            callback.post(event);
    });


    public LivingEntityUseItemFinishEvent(LivingEntity entity, ItemStack item, int duration, ItemStack result) {
        this.entity = entity;
        this.item = item;
        this.duration = duration;
        this.setResultStack(result);
    }

    public interface Callback {
        void post(LivingEntityUseItemFinishEvent event);
    }
}