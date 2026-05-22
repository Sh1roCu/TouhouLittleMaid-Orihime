package com.github.tartaricacid.touhoulittlemaid.api.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * 女仆饰品变化事件
 * 当玩家给女仆佩戴或卸下饰品时触发
 */
@ApiStatus.AvailableSince("1.4.3")
public abstract class MaidBaubleChangeEvent {
    private final EntityMaid maid;
    private final ItemStack baubleItem;

    public static final Event<PutOnCallback> PUT_ON = EventFactory.createArrayBacked(PutOnCallback.class, callbacks -> event -> {
        for (PutOnCallback callback : callbacks) {
            callback.putOn(event);
        }
    });
    public static final Event<TakeOffCallback> TAKE_OFF = EventFactory.createArrayBacked(TakeOffCallback.class, callbacks -> event -> {
        for (TakeOffCallback callback : callbacks) {
            callback.takeOff(event);
        }
    });

    public interface PutOnCallback {
        void putOn(PutOn event);
    }

    public interface TakeOffCallback {
        void takeOff(TakeOff event);
    }

    public MaidBaubleChangeEvent(EntityMaid maid, ItemStack baubleItem) {
        this.maid = maid;
        this.baubleItem = baubleItem;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public ItemStack getBaubleItem() {
        return baubleItem;
    }

    /**
     * 当玩家给女仆佩戴任意饰品时触发
     */
    public static class PutOn extends MaidBaubleChangeEvent {
        public PutOn(EntityMaid maid, ItemStack baubleItem) {
            super(maid, baubleItem);
        }
    }

    /**
     * 当玩家从女仆身上卸下任意饰品时触发
     */
    public static class TakeOff extends MaidBaubleChangeEvent {
        public TakeOff(EntityMaid maid, ItemStack baubleItem) {
            super(maid, baubleItem);
        }
    }
}