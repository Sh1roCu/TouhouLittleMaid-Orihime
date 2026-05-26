package com.github.tartaricacid.touhoulittlemaid.item.bauble;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemUtil;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import com.github.tartaricacid.touhoulittlemaid.advancements.maid.TriggerType;
import com.github.tartaricacid.touhoulittlemaid.api.bauble.IChestType;
import com.github.tartaricacid.touhoulittlemaid.api.bauble.IMaidBauble;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidWirelessIOEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitTrigger;
import com.github.tartaricacid.touhoulittlemaid.inventory.chest.ChestManager;
import com.github.tartaricacid.touhoulittlemaid.item.ItemWirelessIO;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WirelessIOBauble implements IMaidBauble {
    private static final int SLOT_NUM = 38;

    public static ItemStack insertItemStacked(ResourceHandler<ItemVariant> inventory, ItemStack stack, boolean simulate, @Nullable List<Boolean> slotConfig) {
        if (stack.isEmpty()) {
            return stack;
        }
        if (!stack.isStackable()) {
            return insertItem(inventory, stack, simulate, slotConfig);
        }
        int sizeInventory = inventory.size();
        for (int i = 0; i < sizeInventory; i++) {
            ItemStack slot = ItemUtil.getStack(inventory, i);
            if (slotConfig != null && i < slotConfig.size() && slotConfig.get(i)) {
                continue;
            }
            if (ItemStack.isSameItemSameComponents(slot, stack) && !slot.isEmpty() && slot.isStackable()) {
                stack = ItemUtil.insertItemReturnRemaining(inventory, i, stack, simulate, null);
                if (stack.isEmpty()) {
                    break;
                }
            }
        }

        if (!stack.isEmpty()) {
            for (int i = 0; i < sizeInventory; i++) {
                if (slotConfig != null && i < slotConfig.size() && slotConfig.get(i)) {
                    continue;
                }
                if (ItemUtil.getStack(inventory, i).isEmpty()) {
                    stack = ItemUtil.insertItemReturnRemaining(inventory, i, stack, simulate, null);
                    if (stack.isEmpty()) {
                        break;
                    }
                }
            }
        }

        return stack;
    }

    public static ItemStack insertItem(ResourceHandler<ItemVariant> dest, ItemStack stack, boolean simulate, @Nullable List<Boolean> slotConfig) {
        if (stack.isEmpty()) {
            return stack;
        }
        for (int i = 0; i < dest.size(); i++) {
            if (slotConfig != null && i < slotConfig.size() && slotConfig.get(i)) {
                continue;
            }
            stack = ItemUtil.insertItemReturnRemaining(dest, i, stack, simulate, null);
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
        }
        return stack;
    }

    @Override
    public void onTick(EntityMaid maid, ItemStack baubleItem) {
        if (maid.tickCount % 100 == 0 && !maid.guiOpening) {
            BlockPos bindingPos = ItemWirelessIO.getBindingPos(baubleItem);
            if (bindingPos == null) {
                return;
            }
            float maxDistance = maid.getHomeRadius();
            if (maid.distanceToSqr(bindingPos.getX(), bindingPos.getY(), bindingPos.getZ()) > (maxDistance * maxDistance)) {
                return;
            }
            BlockEntity te = maid.level.getBlockEntity(bindingPos);
            if (te == null) {
                return;
            }
            for (IChestType type : ChestManager.getAllChestTypes()) {
                if (!type.isChest(te)) {
                    continue;
                }
                int openCount = type.getOpenCount(maid.level, bindingPos, te);
                if (openCount > 0) {
                    return;
                }
                Storage<ItemVariant> chestInv = ItemStorage.SIDED.find(maid.level, te.getBlockPos(), te.getBlockState(), te, null);
                if (chestInv != null) {
                    ResourceHandler<ItemVariant> maidInv = maid.getAvailableInv(false);
                    boolean isMaidToChest = ItemWirelessIO.isMaidToChest(baubleItem);
                    boolean isBlacklist = ItemWirelessIO.isBlacklist(baubleItem);
                    List<Boolean> slotConfig = ItemWirelessIO.getSlotConfig(baubleItem);
                    List<Boolean> slotConfigData;
                    if (slotConfig != null) {
                        slotConfigData = new ArrayList<>(slotConfig);
                        slotConfigData.set(maidInv.size() - 2, slotConfig.get(SLOT_NUM - 2));
                        slotConfigData.set(maidInv.size() - 1, slotConfig.get(SLOT_NUM - 1));
                    } else {
                        slotConfigData = new ArrayList<>(Collections.nCopies(SLOT_NUM, false));
                    }
                    ItemStacksResourceHandler filterList = ItemWirelessIO.getFilterList(maid.registryAccess(), baubleItem);

                    if (isMaidToChest) {
                        var event = new MaidWirelessIOEvent.MaidToChest(maid, maidInv, chestInv, filterList, isBlacklist, slotConfigData);
                        MaidWirelessIOEvent.MAID_TO_CHEST.invoker().post(event);
                        if (!event.isCanceled()) {
                            maidToChest(maidInv, chestInv, isBlacklist, filterList, slotConfigData);
                        }
                    } else {
                        var event = new MaidWirelessIOEvent.ChestToMaid(maid, maidInv, chestInv, filterList, isBlacklist, slotConfigData);
                        MaidWirelessIOEvent.CHEST_TO_MAID.invoker().post(event);
                        if (!event.isCanceled()) {
                            chestToMaid(chestInv, maidInv, isBlacklist, filterList, slotConfigData);
                        }
                    }
                }
                if (maid.getOwner() instanceof ServerPlayer serverPlayer) {
                    InitTrigger.MAID_EVENT.trigger(serverPlayer, TriggerType.USE_WIRELESS_IO);
                }
                return;
            }
        }
    }

    private void maidToChest(ResourceHandler<ItemVariant> maid, Storage<ItemVariant> chest, boolean isBlacklist, ResourceHandler<ItemVariant> filterList, List<Boolean> slotConfig) {
        for (int i = 0; i < maid.size(); i++) {
            if (i < slotConfig.size() && slotConfig.get(i)) {
                continue;
            }
            ItemStack maidInvItem = ItemUtil.getStack(maid, i);
            if (maidInvItem.isEmpty())
                continue;
            boolean allowMove = isBlacklist;
            for (int j = 0; j < filterList.size(); j++) {
                ItemStack filterItem = ItemUtil.getStack(filterList, j);
                boolean isEqual = ItemStack.isSameItem(maidInvItem, filterItem);
                if (isEqual) {
                    allowMove = !isBlacklist;
                    break;
                }
            }
            if (allowMove) {
                int beforeCount = maidInvItem.getCount();
                try (Transaction transaction = Transaction.openOuter()) {
                    var variant = ItemVariant.of(maidInvItem.copy());
                    long inserted = StorageUtil.tryInsertStacking(chest, variant, beforeCount, transaction);
                    if (inserted > 0) {
                        maid.extract(i, variant, (int) inserted, transaction);
                        transaction.commit();
                    }
                }
            }
        }
    }

    private void chestToMaid(Storage<ItemVariant> chest, ResourceHandler<ItemVariant> maid, boolean isBlacklist, ResourceHandler<ItemVariant> filterList, List<Boolean> slotConfig) {
        for (StorageView<ItemVariant> view : chest.nonEmptyViews()) {
            ItemVariant chestInvStack = view.getResource();
            boolean allowMove = isBlacklist;
            for (int j = 0; j < filterList.size(); j++) {
                ItemStack filterItem = ItemUtil.getStack(filterList, j);
                boolean isEqual = ItemStack.isSameItem(chestInvStack.toStack(), filterItem);
                if (isEqual) {
                    allowMove = !isBlacklist;
                    break;
                }
            }
            if (allowMove) {
                int beforeCount = (int) view.getAmount();
                ItemStack after = insertItemStacked(maid, chestInvStack.toStack(beforeCount).copy(), false, slotConfig);
                int afterCount = after.getCount();
                // Sync Client & Server
                if (beforeCount != afterCount) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        chest.extract(view.getResource(), beforeCount - afterCount, transaction);
                        transaction.commit();
                    }
                }
            }
        }
    }
}
