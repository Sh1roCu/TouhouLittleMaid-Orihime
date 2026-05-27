package com.github.tartaricacid.touhoulittlemaid.util;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStackStorage;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MaidFluidUtil {
    @Nullable
    public static Storage<FluidVariant> fluidOnItem(ItemStack stack) {
        return ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack)).find(FluidStorage.ITEM);
    }

    public static long tankToBucket(ItemStack bucket, SingleFluidStorage tank, ResourceHandler<ItemVariant> maidBackpack) {
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
                ItemsUtil.insertItemStacked(maidBackpack, context.getItemVariant().toStack(), false, tx);
                bucket.shrink(1);
                tx.commit();
                return result;
            }
            return 0;
        }
    }

    public static long bucketToTank(ItemStack bucket, SingleFluidStorage tank, ResourceHandler<ItemVariant> maidBackpack) {
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
                ItemsUtil.insertItemStacked(maidBackpack, context.getItemVariant().toStack(), false, tx);
                bucket.shrink(1);
                tx.commit();
                return result;
            }
            return 0;
        }
    }
}
