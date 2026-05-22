package com.github.tartaricacid.touhoulittlemaid.util;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.ItemHandlerHelper;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;

public class MaidFluidUtil {
    public static long tankToBucket(ItemStack bucket, SingleFluidStorage tank, IItemHandler maidBackpack) {
        if (bucket.isEmpty()) {
            return 0;
        }
        Storage<FluidVariant> bucketStorage = ContainerItemContext.withConstant(bucket).find(FluidStorage.ITEM);
        if (bucketStorage == null)
            return 0;
        if (tank.isResourceBlank())
            return 0;
        try (Transaction tx = Transaction.openOuter()) {
            FluidVariant fluid = tank.variant;
            long inserted = bucketStorage.insert(fluid, tank.amount, tx);
            long result = tank.extract(fluid, inserted, tx);
            if (result > 0) {
                ItemHandlerHelper.insertItemStacked(maidBackpack, new ItemStack(fluid.getFluid().getBucket()), false);
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
        Storage<FluidVariant> bucketStorage = ContainerItemContext.withConstant(bucket).find(FluidStorage.ITEM);
        if (bucketStorage == null)
            return 0;
        FluidVariant fluid = FluidVariant.blank();
        for (StorageView<FluidVariant> view : bucketStorage.nonEmptyViews()) {
            if (!view.isResourceBlank()) {
                fluid = view.getResource();
                break;
            }
        }
        if (fluid.isBlank())
            return 0;
        try (Transaction tx = Transaction.openOuter()) {
            long extracted = bucketStorage.extract(fluid, tank.getCapacity() - tank.amount, tx);
            long result = tank.insert(fluid, extracted, tx);
            if (result > 0) {
                ItemHandlerHelper.insertItemStacked(maidBackpack, new ItemStack(bucket.getRecipeRemainder().getItem()), false);
                bucket.shrink(1);
                tx.commit();
                return result;
            }
            return 0;
        }
    }
}
