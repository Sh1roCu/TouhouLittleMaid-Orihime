package com.github.tartaricacid.touhoulittlemaid.api.event;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

import java.util.List;

/**
 * 在女仆使用隙间传输物品时触发
 * <p>
 * 此事件可以取消，取消后则不会进行物品传输
 */
public abstract class MaidWirelessIOEvent extends CancellableEvent {
    private final EntityMaid maid;
    /**
     * 女仆的物品栏
     */
    private final ResourceHandler<ItemVariant> maidInv;
    /**
     * 箱子的物品栏
     */
    private final Storage<ItemVariant> chestInv;
    /**
     * 隙间过滤器的标记的物品
     */
    private final ResourceHandler<ItemVariant> filterInv;
    /**
     * 是否是黑名单模式
     */
    private final boolean isBlacklist;
    /**
     * 物品栏的配置，true 表示该槽位 不 允许传输
     */
    private final List<Boolean> slotConfig;

    public static final Event<MaidToChestCallback> MAID_TO_CHEST = EventFactory.createArrayBacked(MaidToChestCallback.class, callbacks -> event -> {
        for (MaidToChestCallback callback : callbacks) {
            callback.post(event);
        }
    });

    public static final Event<ChestToMaidCallback> CHEST_TO_MAID = EventFactory.createArrayBacked(ChestToMaidCallback.class, callbacks -> event -> {
        for (ChestToMaidCallback callback : callbacks) {
            callback.post(event);
        }
    });

    public interface MaidToChestCallback {
        void post(MaidToChest event);
    }

    public interface ChestToMaidCallback {
        void post(ChestToMaid event);
    }

    public MaidWirelessIOEvent(EntityMaid maid, ResourceHandler<ItemVariant> maidInv, Storage<ItemVariant> chestInv, ResourceHandler<ItemVariant> filterInv, boolean isBlacklist, List<Boolean> slotConfig) {
        this.maid = maid;
        this.maidInv = maidInv;
        this.chestInv = chestInv;
        this.filterInv = filterInv;
        this.isBlacklist = isBlacklist;
        this.slotConfig = slotConfig;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public ResourceHandler<ItemVariant> getMaidInv() {
        return maidInv;
    }

    public Storage<ItemVariant> getChestInv() {
        return chestInv;
    }

    public ResourceHandler<ItemVariant> getFilterInv() {
        return filterInv;
    }

    public boolean isBlacklist() {
        return isBlacklist;
    }

    public List<Boolean> getSlotConfig() {
        return slotConfig;
    }

    public static class MaidToChest extends MaidWirelessIOEvent {
        public MaidToChest(EntityMaid maid,
                           ResourceHandler<ItemVariant> maidInv,
                           Storage<ItemVariant> chestInv,
                           ResourceHandler<ItemVariant> filterInv,
                           boolean isBlacklist,
                           List<Boolean> slotConfig
        ) {
            super(maid, maidInv, chestInv, filterInv, isBlacklist, slotConfig);
        }
    }

    public static class ChestToMaid extends MaidWirelessIOEvent {
        public ChestToMaid(EntityMaid maid,
                           ResourceHandler<ItemVariant> maidInv,
                           Storage<ItemVariant> chestInv,
                           ResourceHandler<ItemVariant> filterInv,
                           boolean isBlacklist,
                           List<Boolean> slotConfig
        ) {
            super(maid, maidInv, chestInv, filterInv, isBlacklist, slotConfig);
        }
    }
}
