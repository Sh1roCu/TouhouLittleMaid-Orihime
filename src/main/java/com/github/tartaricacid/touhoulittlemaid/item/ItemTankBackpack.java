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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;

import java.util.Optional;
import java.util.function.Consumer;

import static com.github.tartaricacid.touhoulittlemaid.init.InitDataComponent.TANK_BACKPACK_TAG;

public class ItemTankBackpack extends ItemMaidBackpack {
    public ItemTankBackpack(Identifier id) {
        super(id);
    }

    public static ItemStack getTankBackpack(HolderLookup.Provider provider, TankBackpackData data) {
        ItemStack backpack = InitItems.TANK_BACKPACK.getDefaultInstance();
        CompoundTag tags = backpack.get(TANK_BACKPACK_TAG);
        if (tags == null) {
            tags = new CompoundTag();
            backpack.set(InitDataComponent.TANK_BACKPACK_TAG, tags);
        }
        if (data.getTank().isResourceBlank()) {
            tags.remove("Fluid");
        } else {
            var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, provider);
            data.getTank().writeValue(output);
            tags.put("Fluid", output.buildResult());
        }
        return backpack;
    }

    public static void setTankBackpack(EntityMaid maid, TankBackpackData data, ItemStack backpack) {
        CompoundTag tags = backpack.get(TANK_BACKPACK_TAG);
        if (tags == null) {
            tags = new CompoundTag();
            backpack.set(InitDataComponent.TANK_BACKPACK_TAG, tags);
        }
        var tag = TagValueInput.create(ProblemReporter.DISCARDING, maid.level.registryAccess(), tags);
        data.loadTank(tag.childOrEmpty("Fluid"), maid);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag nbt = stack.get(TANK_BACKPACK_TAG);
        if (nbt == null) {
            return;
        }
        Optional<CompoundTag> fluidCompound = nbt.getCompound("Fluid");
        if (fluidCompound.isEmpty() || fluidCompound.get().isEmpty()) {
            return;
        }
        HolderLookup.Provider registries = context.registries();
        if (registries == null) {
            return;
        }

        var input = TagValueInput.create(ProblemReporter.DISCARDING, registries, fluidCompound.get());
        FluidVariant variant = input.read("variant", FluidVariant.CODEC).orElseGet(FluidVariant::blank);
        long amount = input.getLongOr("amount", 0L);
        if (variant.isBlank() || amount == 0) {
            tooltip.accept(Component.translatable("tooltips.touhou_little_maid.tank_backpack.empty_fluid").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.accept(Component.translatable("tooltips.touhou_little_maid.tank_backpack.fluid",
                    FluidVariantAttributes.getName(variant), amount / 81).withStyle(ChatFormatting.GRAY));
        }
    }
}
