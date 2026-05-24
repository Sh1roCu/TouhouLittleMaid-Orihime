package com.github.tartaricacid.touhoulittlemaid.util;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStackStorage;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class MaidFluidUtil {
    private static final Predicate<FluidVariant> NON_EMPTY = r -> !r.isBlank();

    @Nullable
    public static Storage<FluidVariant> fluidOnItem(ItemStack stack) {
        return ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack)).find(FluidStorage.ITEM);
    }

    public static boolean tankToBucket(ItemStack container, Storage<FluidVariant> tankFluidSlot, @Nullable ResourceHandler<ItemVariant> maidInv) {
        if (container.isEmpty()) {
            return false;
        }
        if (container.getCount() == 1) {
            return tankToBucketSingle(container, tankFluidSlot);
        }
        if (maidInv == null) {
            return false;
        }
        ItemStack probe = container.copyWithCount(1);
        Storage<FluidVariant> probeFluid = fluidOnItem(probe);
        if (probeFluid == null) {
            return false;
        }
        ItemStack filledSim;
        try (Transaction probeTx = Transaction.openOuter()) {
            if (StorageUtil.move(tankFluidSlot, probeFluid, NON_EMPTY, Integer.MAX_VALUE, probeTx) == 0) {
                return false;
            }
            // probe 已在 transaction 内被 move 修改，此时复制以捕获模拟后的状态
            filledSim = probe.copy();
        }

        try (Transaction outer = Transaction.openOuter()) {
            if (!ItemsUtil.insertItemStacked(maidInv, filledSim, true, outer).isEmpty()) {
                return false;
            }
            ItemStack one = container.split(1);
            Storage<FluidVariant> oneFluid = fluidOnItem(one);
            if (oneFluid == null) {
                container.grow(1);
                return false;
            }
            if (StorageUtil.move(tankFluidSlot, oneFluid, NON_EMPTY, Integer.MAX_VALUE, outer) == 0) {
                container.grow(1);
                return false;
            }
            if (!ItemsUtil.insertItemStacked(maidInv, one, false, outer).isEmpty()) {
                container.grow(1);
                return false;
            }
            outer.commit();
            return true;
        }
    }

    private static boolean tankToBucketSingle(ItemStack container, Storage<FluidVariant> tankFluidSlot) {
        Storage<FluidVariant> itemFluid = fluidOnItem(container);
        if (itemFluid == null) {
            return false;
        }
        try (Transaction tx = Transaction.openOuter()) {
            if (StorageUtil.move(tankFluidSlot, itemFluid, NON_EMPTY, Integer.MAX_VALUE, tx) == 0) {
                return false;
            }
            tx.commit();
        }
        return true;
    }

    public static boolean bucketToTank(ItemStack container, Storage<FluidVariant> tankFluidSlot, @Nullable ResourceHandler<ItemVariant> maidInv) {
        if (container.isEmpty()) {
            return false;
        }
        if (container.getCount() == 1) {
            return bucketToTankSingle(container, tankFluidSlot);
        }
        if (maidInv == null) {
            return false;
        }
        ItemStack probe = container.copyWithCount(1);
        Storage<FluidVariant> probeFluid = fluidOnItem(probe);
        if (probeFluid == null) {
            return false;
        }
        ItemStack emptiedSim;
        try (Transaction probeTx = Transaction.openOuter()) {
            if (StorageUtil.move(probeFluid, tankFluidSlot, NON_EMPTY, Integer.MAX_VALUE, probeTx) == 0) {
                return false;
            }
            // probe 已在 transaction 内被 move 修改，此时复制以捕获模拟后的状态
            emptiedSim = probe.copy();
        }

        try (Transaction outer = Transaction.openOuter()) {
            if (!ItemsUtil.insertItemStacked(maidInv, emptiedSim, true, outer).isEmpty()) {
                return false;
            }
            ItemStack one = container.split(1);
            Storage<FluidVariant> oneFluid = fluidOnItem(one);
            if (oneFluid == null) {
                container.grow(1);
                return false;
            }
            if (StorageUtil.move(oneFluid, tankFluidSlot, NON_EMPTY, Integer.MAX_VALUE, outer) == 0) {
                container.grow(1);
                return false;
            }
            if (!ItemsUtil.insertItemStacked(maidInv, one, false, outer).isEmpty()) {
                container.grow(1);
                return false;
            }
            outer.commit();
            return true;
        }
    }

    private static boolean bucketToTankSingle(ItemStack container, Storage<FluidVariant> tankFluidSlot) {
        Storage<FluidVariant> itemFluid = fluidOnItem(container);
        if (itemFluid == null) {
            return false;
        }
        try (Transaction tx = Transaction.openOuter()) {
            if (StorageUtil.move(itemFluid, tankFluidSlot, NON_EMPTY, Integer.MAX_VALUE, tx) == 0) {
                return false;
            }
            tx.commit();
        }
        return true;
    }
}
