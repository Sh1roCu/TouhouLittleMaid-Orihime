package com.github.tartaricacid.touhoulittlemaid.item;

import com.github.tartaricacid.touhoulittlemaid.entity.backpack.data.TankBackpackData;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitDataComponent;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static com.github.tartaricacid.touhoulittlemaid.init.InitDataComponent.TANK_BACKPACK_TAG;

public class ItemTankBackpack extends ItemMaidBackpack {
    public static ItemStack getTankBackpack(HolderLookup.Provider provider, TankBackpackData data) {
        ItemStack backpack = InitItems.TANK_BACKPACK.getDefaultInstance();
        CompoundTag tags = backpack.get(TANK_BACKPACK_TAG);
        if (tags == null) {
            tags = new CompoundTag();
            backpack.set(InitDataComponent.TANK_BACKPACK_TAG, tags);
        }
        data.getTank().writeNbt(tags, provider);
        return backpack;
    }

    public static void setTankBackpack(EntityMaid maid, TankBackpackData data, ItemStack backpack) {
        CompoundTag tags = backpack.get(TANK_BACKPACK_TAG);
        if (tags == null) {
            tags = new CompoundTag();
            backpack.set(InitDataComponent.TANK_BACKPACK_TAG, tags);
        }
        data.loadTank(tags, maid);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag nbt = stack.get(TANK_BACKPACK_TAG);
        if (nbt != null) {
            //CompoundTag compound = nbt.getCompound("Fluid");
            CompoundTag compound = nbt.getCompound("variant");
            if (compound.isEmpty() || worldIn == null) {
                return;
            }
            HolderLookup.Provider registries = worldIn.registries();
            if (registries == null) {
                return;
            }


            MutableComponent fluidInfo;
            //Optional<FluidStack> fluid = FluidStack.read(registries, compound);
            Optional<FluidVariant> fluid = FluidVariant.CODEC.parse(registries.createSerializationContext(NbtOps.INSTANCE), compound).result();
            if (fluid.isEmpty()) {
                return;
            }
            FluidVariant fluidStack = fluid.get();
            if (fluidStack.getFluid() == Fluids.EMPTY || nbt.getLong("amount") == 0) {
                fluidInfo = Component.translatable("tooltips.touhou_little_maid.tank_backpack.empty_fluid").withStyle(ChatFormatting.GRAY);
            } else {
                fluidInfo = Component.translatable("tooltips.touhou_little_maid.tank_backpack.fluid",
                        FluidVariantAttributes.getName(fluidStack),
                        nbt.getLong("amount") / 81).withStyle(ChatFormatting.GRAY);
            }
            tooltip.add(fluidInfo);
        }
    }
}
