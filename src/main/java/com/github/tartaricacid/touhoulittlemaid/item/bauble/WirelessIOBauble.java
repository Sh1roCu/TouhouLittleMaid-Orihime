package com.github.tartaricacid.touhoulittlemaid.item.bauble;

import cn.sh1rocu.touhoulittlemaid.util.transfer.CombinedResourceHandler;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemUtil;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import com.github.tartaricacid.touhoulittlemaid.advancements.maid.TriggerType;
import com.github.tartaricacid.touhoulittlemaid.api.bauble.IMaidBauble;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidWirelessIOEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitTrigger;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.WirelessIOItemHandler;
import com.github.tartaricacid.touhoulittlemaid.item.ItemWirelessIO;
import com.github.tartaricacid.touhoulittlemaid.network.NetworkHandler;
import com.github.tartaricacid.touhoulittlemaid.network.message.TeleportItemParticlePackage;
import com.github.tartaricacid.touhoulittlemaid.util.ItemsUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WirelessIOBauble implements IMaidBauble {
    private static final int SLOT_NUM = 38;

    @Override
    public void onTick(EntityMaid maid, ItemStack baubleItem) {
        // 女仆 GUI 打开时不进行物品传输，避免潜在的刷物品问题
        if (maid.tickCount % 100 != 0 || maid.guiOpening) {
            return;
        }

        // 是否已经标记了坐标
        BlockPos pos = ItemWirelessIO.getBindingPos(baubleItem);
        if (pos == null) {
            return;
        }

        // 是否在工作范围内
        Vec3 centerOf = Vec3.atCenterOf(pos);
        float maxDistance = maid.getHomeRadius();
        if (maid.distanceToSqr(centerOf) > (maxDistance * maxDistance)) {
            return;
        }
        BlockEntity te = maid.level.getBlockEntity(pos);
        if (te == null) {
            return;
        }

        // 依据输入输出，选择不同的朝向
        boolean isMaidToChest = ItemWirelessIO.isMaidToChest(baubleItem);
        Direction side = isMaidToChest ? Direction.UP : Direction.DOWN;

        // 获取容器的 Capabilities
        var chestInv = ItemStorage.SIDED.find(maid.level, te.getBlockPos(), te.getBlockState(), te, side);
        if (chestInv == null) {
            return;
        }

        var maidInv = maid.getAvailableInv(false);
        var filterList = WirelessIOItemHandler.fromStack(baubleItem);
        var slotConfig = getSlotConfig(baubleItem, maidInv);
        boolean isBlacklist = ItemWirelessIO.isBlacklist(baubleItem);

        if (isMaidToChest) {
            var event = new MaidWirelessIOEvent.MaidToChest(
                    maid, maidInv, chestInv, filterList, isBlacklist, slotConfig
            );
            MaidWirelessIOEvent.MAID_TO_CHEST.invoker().post(event);
            if (!event.isCanceled()) {
                maidToChest(maid, pos, isBlacklist, maidInv, chestInv, filterList, slotConfig);
            }
        } else {
            var event = new MaidWirelessIOEvent.ChestToMaid(
                    maid, maidInv, chestInv, filterList, isBlacklist, slotConfig
            );
            MaidWirelessIOEvent.CHEST_TO_MAID.invoker().post(event);
            if (!event.isCanceled()) {
                chestToMaid(maid, pos, isBlacklist, chestInv, maidInv, filterList, slotConfig);
            }
        }

        if (maid.getOwner() instanceof ServerPlayer serverPlayer) {
            InitTrigger.MAID_EVENT.trigger(serverPlayer, TriggerType.USE_WIRELESS_IO);
        }
    }

    private static ItemStack insertItemStacked(ResourceHandler<ItemVariant> inventory, ItemStack stack,
                                               @Nullable List<Boolean> slotConfig) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        // 不可堆叠的物品，不用考虑背包碎片化问题，直接遍历插入
        if (!stack.isStackable()) {
            return insertItem(inventory, stack, slotConfig);
        }

        int size = inventory.size();

        // 第一次遍历，优先补满已有堆叠，避免背包碎片化
        for (int i = 0; i < size; i++) {
            // 如果该槽位配置被禁用，那么不插入物品
            if (slotConfig != null && i < slotConfig.size() && slotConfig.get(i)) {
                continue;
            }
            ItemStack slotStack = ItemUtil.getStack(inventory, i);
            if (!slotStack.isEmpty() && slotStack.isStackable() && ItemStack.isSameItemSameComponents(slotStack, stack)) {
                stack = ItemUtil.insertItemReturnRemaining(inventory, i, stack, false, null);
                if (stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
            }
        }

        // 第二次遍历，这次仅填充空槽
        for (int i = 0; i < size; i++) {
            if (slotConfig != null && i < slotConfig.size() && slotConfig.get(i)) {
                continue;
            }
            ItemStack slotStack = ItemUtil.getStack(inventory, i);
            if (slotStack.isEmpty()) {
                stack = ItemUtil.insertItemReturnRemaining(inventory, i, stack, false, null);
                if (stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
            }
        }

        return stack;
    }

    private static ItemStack insertItem(ResourceHandler<ItemVariant> dest, ItemStack stack,
                                        @Nullable List<Boolean> slotConfig) {
        if (stack.isEmpty()) {
            return stack;
        }
        for (int i = 0; i < dest.size(); i++) {
            // 如果该槽位配置被禁用，那么不插入物品
            if (slotConfig != null && i < slotConfig.size() && slotConfig.get(i)) {
                continue;
            }
            // 逐次插入物品，直至为空
            stack = ItemUtil.insertItemReturnRemaining(dest, i, stack, false, null);
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
        }
        return stack;
    }

    private static List<Boolean> getSlotConfig(ItemStack baubleItem, CombinedResourceHandler<ItemVariant> maidInv) {
        List<Boolean> slotConfig = ItemWirelessIO.getSlotConfig(baubleItem);
        List<Boolean> slotConfigData;
        if (slotConfig != null) {
            slotConfigData = new ArrayList<>(slotConfig);
            // 主副手槽位的设置，因为背包是可变大小的，需要动态复制主副手的 index
            slotConfigData.set(maidInv.size() - 2, slotConfig.get(SLOT_NUM - 2));
            slotConfigData.set(maidInv.size() - 1, slotConfig.get(SLOT_NUM - 1));
        } else {
            slotConfigData = new ArrayList<>(Collections.nCopies(SLOT_NUM, false));
        }
        return slotConfigData;
    }

    private static boolean allowMove(ItemStack stack, boolean isBlacklist, ResourceHandler<ItemVariant> filterList) {
        for (int i = 0; i < filterList.size(); i++) {
            ItemStack filterStack = ItemUtil.getStack(filterList, i);
            if (ItemStack.isSameItem(stack, filterStack)) {
                return !isBlacklist;
            }
        }
        return isBlacklist;
    }

    private static void maidToChest(
            EntityMaid maid, BlockPos chestPos, boolean isBlacklist,
            ResourceHandler<ItemVariant> maidInv, Storage<ItemVariant> chestInv,
            ResourceHandler<ItemVariant> filterList, List<Boolean> slotConfig
    ) {
        int delayTicks = 0;
        for (int i = 0; i < maidInv.size(); i++) {
            // 槽位配置检查
            if (i < slotConfig.size() && slotConfig.get(i)) {
                continue;
            }

            ItemStack maidInvItem = ItemUtil.getStack(maidInv, i);
            if (maidInvItem.isEmpty()) {
                continue;
            }

            // 黑白名单检查
            boolean allowMove = allowMove(maidInvItem, isBlacklist, filterList);
            if (!allowMove) {
                continue;
            }

            int beforeCount = maidInvItem.getCount();
            try (Transaction transaction = Transaction.openOuter()) {
                var variant = ItemVariant.of(maidInvItem.copy());
                long inserted = StorageUtil.tryInsertStacking(chestInv, variant, beforeCount, transaction);
                // 执行女仆背包扣除
                if (inserted > 0) {
                    ItemsUtil.extractItem(maidInv, i, (int) inserted, false, transaction);
                    transaction.commit();
                    // 发射粒子和音效
                    NetworkHandler.sendToNearby(maid, new TeleportItemParticlePackage(
                            maid.getId(), chestPos, maidInvItem, false, delayTicks
                    ));
                    delayTicks++;
                }
            }
        }
    }

    private static void chestToMaid(
            EntityMaid maid, BlockPos chestPos, boolean isBlacklist,
            Storage<ItemVariant> chestInv, ResourceHandler<ItemVariant> maidInv,
            ResourceHandler<ItemVariant> filterList, List<Boolean> slotConfig
    ) {
        int delayTicks = 0;
        for (StorageView<ItemVariant> view : chestInv.nonEmptyViews()) {
            if (view.isResourceBlank()) {
                continue;
            }

            // 女仆的槽位配置检查在后面的 insertItemStacked 里
            ItemStack chestInvItem = view.getResource().toStack((int) view.getAmount());
            boolean allowMove = allowMove(chestInvItem, isBlacklist, filterList);
            if (!allowMove) {
                continue;
            }

            int beforeCount = chestInvItem.getCount();
            ItemStack after = insertItemStacked(maidInv, chestInvItem.copy(), slotConfig);
            int afterCount = after.getCount();

            // 执行女仆背包扣除
            if (beforeCount != afterCount) {
                try (Transaction transaction = Transaction.openOuter()) {
                    chestInv.extract(view.getResource(), beforeCount - afterCount, transaction);
                    transaction.commit();
                }
                // 发射粒子和音效
                NetworkHandler.sendToNearby(maid, new TeleportItemParticlePackage(
                        maid.getId(), chestPos, chestInvItem, true, delayTicks
                ));
                delayTicks++;
            }
        }
    }
}
