package com.github.tartaricacid.touhoulittlemaid.util;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.ItemHandlerHelper;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStackStorage;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("UnstableApiUsage")
public class MaidFluidUtil {
    public static long tankToBucket(ItemStack bucket, SingleFluidStorage tank, IItemHandler maidBackpack) {
        if (bucket.isEmpty()) {
            return 0;
        }

        ContainerItemContext context = ContainerItemContext.ofSingleSlot(new ItemStackStorage(bucket));
        Storage<FluidVariant> bucketStorage = context.find(FluidStorage.ITEM);
        if (bucketStorage == null)
            return 0;
        if (tank.isResourceBlank())
            return 0;

        try (Transaction tx = Transaction.openOuter()) {
            long result = StorageUtil.move(tank, bucketStorage, v -> !v.isBlank(), tank.getCapacity(), tx);
            if (result > 0) {
                ItemHandlerHelper.insertItemStacked(maidBackpack, context.getItemVariant().toStack(), false);
                bucket.shrink(1);
                tx.commit();
                return result;
            }
            return 0;
        }
    }

    public static long bucketToTank(ItemStack bucket, SingleFluidStorage tank, IItemHandler maidBackpack) {
        if (bucket.isEmpty()) {
            return 0;
        }

        ContainerItemContext context = ContainerItemContext.ofSingleSlot(new ItemStackStorage(bucket));
        Storage<FluidVariant> bucketStorage = context.find(FluidStorage.ITEM);
        if (bucketStorage == null)
            return 0;

        try (Transaction tx = Transaction.openOuter()) {
            long result = StorageUtil.move(bucketStorage, tank, v -> !v.isBlank(), tank.getCapacity(), tx);
            if (result > 0) {
                ItemHandlerHelper.insertItemStacked(maidBackpack, context.getItemVariant().toStack(), false);
                bucket.shrink(1);
                tx.commit();
                return result;
            }
            return 0;
        }
    }
}
