package com.github.tartaricacid.touhoulittlemaid.entity.backpack.data;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.CombinedInvWrapper;
import com.github.tartaricacid.touhoulittlemaid.api.backpack.IBackpackData;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.network.message.SyncFluidAmountMessage;
import com.github.tartaricacid.touhoulittlemaid.util.MaidFluidUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TankBackpackData extends SimpleContainer implements IBackpackData {
    public static final long CAPACITY = 10 * FluidConstants.BUCKET;
    private static final int INPUT_INDEX = 0;
    private static final int OUTPUT_INDEX = 1;
    private final EntityMaid maid;
    private final SingleFluidStorage tank = SingleFluidStorage.withFixedCapacity(CAPACITY, () -> {
        // amount改变时发包同步客户端流体amount
        if (TankBackpackData.this.maid.getOwner() instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, SyncFluidAmountMessage.ID, SyncFluidAmountMessage.encode(this.getTank().amount));
        }
    });
    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            if (index == 0) {
                return (int) TankBackpackData.this.tankFluidCount;
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                TankBackpackData.this.tankFluidCount = value;
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
    public void setItem(int index, @NotNull ItemStack stack) {
        if (!this.maid.level.isClientSide) {
            CombinedInvWrapper availableInv = this.maid.getAvailableInv(false);
            if (index == INPUT_INDEX) {
                MaidFluidUtil.bucketToTank(stack, tank, availableInv);
            }
            if (index == OUTPUT_INDEX) {
                MaidFluidUtil.tankToBucket(stack, tank, availableInv);
            }
            this.tankFluidCount = tank.amount;
            ResourceLocation key = BuiltInRegistries.FLUID.getKey(tank.getResource().getFluid());
            maid.setBackpackFluid(key.toString());
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
    public void load(CompoundTag tag, EntityMaid maid) {
        this.loadTank(tag.getCompound("Tanks"), maid);
        this.fromTag(tag.getList("Items", Tag.TAG_COMPOUND));
    }

    @Override
    public void save(CompoundTag tag, EntityMaid maid) {
        CompoundTag tankNbt = new CompoundTag();
        tank.writeNbt(tankNbt);
        tag.put("Tanks", tankNbt);
        tag.put("Items", this.createTag());
    }

    @Override
    public void serverTick(EntityMaid maid) {
    }

    public SingleFluidStorage getTank() {
        return tank;
    }

    public void loadTank(CompoundTag nbt, EntityMaid maid) {
        tank.readNbt(nbt);
        this.tankFluidCount = tank.getAmount();
        ResourceLocation key = BuiltInRegistries.FLUID.getKey(tank.getResource().getFluid());
        maid.setBackpackFluid(key.toString());
    }
}
