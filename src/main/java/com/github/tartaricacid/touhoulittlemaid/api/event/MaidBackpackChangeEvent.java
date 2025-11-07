package com.github.tartaricacid.touhoulittlemaid.api.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * 女仆背包变化事件
 * 当玩家给女仆装备或取下背包内物品时触发
 * <p>
 * 注意：如果仅是物品数量发生变化，那么不会触发此事件
 * </p>
 */
@ApiStatus.AvailableSince("1.4.3")
public abstract class MaidBackpackChangeEvent {
    private final EntityMaid maid;
    private final ItemStack itemStack;

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

    public MaidBackpackChangeEvent(EntityMaid maid, ItemStack itemStack) {
        this.maid = maid;
        this.itemStack = itemStack;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * 当玩家给女仆背包装备任意物品时触发
     */
    public static class PutOn extends MaidBackpackChangeEvent {
        public PutOn(EntityMaid maid, ItemStack itemStack) {
            super(maid, itemStack);
        }
    }

    /**
     * 当玩家从女仆背包取下任意物品时触发
     */
    public static class TakeOff extends MaidBackpackChangeEvent {
        public TakeOff(EntityMaid maid, ItemStack itemStack) {
            super(maid, itemStack);
        }
    }
}