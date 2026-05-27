package com.github.tartaricacid.touhoulittlemaid.entity.backpack.data;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import com.github.tartaricacid.touhoulittlemaid.api.backpack.IBackpackData;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.util.MaidFluidUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class TankBackpackData extends SimpleContainer implements IBackpackData {
    public static final long CAPACITY = 10 * FluidConstants.BUCKET;
    private static final int INPUT_INDEX = 0;
    private static final int OUTPUT_INDEX = 1;
    private final EntityMaid maid;
    private final SingleFluidStorage tank = SingleFluidStorage.withFixedCapacity(CAPACITY, () -> {
    });
    private final ContainerData dataAccess = new ContainerData() {
        // Fabric: 由于莫名其妙的客户端同步问题，只能让返回的流体数量单位是mB从而适配int类型
        @Override
        public int get(int index) {
            if (index == 0) {
                return (int) (TankBackpackData.this.tankFluidCount / 81);
            }
            return 0;
        }

        // value: mB
        @Override
        public void set(int index, int value) {
            if (index == 0) {
                TankBackpackData.this.tankFluidCount = value * 81L;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    private long tankFluidCount = 0;

    public TankBackpackData(EntityMaid maid) {
        super(2);
        this.maid = maid;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (!this.maid.level().isClientSide()) {
            ResourceHandler<ItemVariant> availableInv = this.maid.getAvailableInv(false);
            long moved = 0;
            if (index == INPUT_INDEX) {
                moved = MaidFluidUtil.bucketToTank(stack, tank, availableInv);
            }
            if (index == OUTPUT_INDEX) {
                moved = MaidFluidUtil.tankToBucket(stack, tank, availableInv);
            }
            if (moved > 0) {
                this.tankFluidCount = tank.getAmount();
            }
            Identifier key = BuiltInRegistries.FLUID.getKey(tank.getResource().getFluid());
            this.maid.setBackpackFluid(!key.equals(BuiltInRegistries.FLUID.getDefaultKey()) ? key.toString() : "");
        }
        super.setItem(index, stack);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public ContainerData getDataAccess() {
        return dataAccess;
    }

    @Override
    public void load(ValueInput tag, EntityMaid maid) {
        this.clearContent();
        tag.child("Tanks").ifPresent(this::readTankNbt);
        ContainerHelper.loadAllItems(tag, this.getItems());
    }

    @Override
    public void save(ValueOutput tag, EntityMaid maid) {
        if (this.tank.isResourceBlank()) {
            tag.discard("Tanks");
        } else {
            ValueOutput tanks = tag.child("Tanks");
            this.writeTankCompound(tanks);
        }
        ContainerHelper.saveAllItems(tag, this.getItems());
    }

    @Override
    public void serverTick(EntityMaid maid) {
    }

    public SingleFluidStorage getTank() {
        return tank;
    }

    public void loadTank(ValueInput tag, EntityMaid maid) {
        this.readTankNbt(tag);
        this.tankFluidCount = this.tank.getAmount();
        if (this.tank.isResourceBlank()) {
            maid.setBackpackFluid("");
        } else {
            Identifier key = BuiltInRegistries.FLUID.getKey(this.tank.getResource().getFluid());
            maid.setBackpackFluid(!key.equals(BuiltInRegistries.FLUID.getDefaultKey()) ? key.toString() : "");
        }
    }

    private void readTankNbt(ValueInput tag) {
        this.tank.readValue(tag);
        this.tankFluidCount = this.tank.getAmount();
    }

    private void writeTankCompound(ValueOutput tag) {
        if (this.tank.isResourceBlank()) {
            return;
        }
        this.tank.writeValue(tag);
    }
}
