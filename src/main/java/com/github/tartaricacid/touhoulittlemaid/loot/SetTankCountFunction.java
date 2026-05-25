package com.github.tartaricacid.touhoulittlemaid.loot;

import com.github.tartaricacid.touhoulittlemaid.entity.backpack.data.TankBackpackData;
import com.github.tartaricacid.touhoulittlemaid.init.InitDataComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.tartaricacid.touhoulittlemaid.init.InitDataComponent.TANK_BACKPACK_TAG;

public class SetTankCountFunction extends LootItemConditionalFunction {
    public static MapCodec<SetTankCountFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance)
            .and(instance.group(
                    Identifier.CODEC.fieldOf("fluid_id").forGetter(f -> f.fluidId),
                    Codec.LONG.fieldOf("count").forGetter(f -> f.count)
            )).apply(instance, SetTankCountFunction::new));

    private final Identifier fluidId;
    private final long count;

    public SetTankCountFunction(List<LootItemCondition> predicates, Identifier fluidId, long count) {
        super(predicates);
        this.fluidId = fluidId;
        this.count = count;
    }

    @Override
    public MapCodec<? extends LootItemConditionalFunction> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull ItemStack run(ItemStack stack, @NotNull LootContext context) {
        CompoundTag tags = stack.get(TANK_BACKPACK_TAG);
        if (tags == null) {
            tags = new CompoundTag();
        }
        Fluid fluid = BuiltInRegistries.FLUID.getValue(this.fluidId);
        if (fluid == null || fluid.isSame(Fluids.EMPTY)) {
            stack.set(InitDataComponent.TANK_BACKPACK_TAG, tags);
            return stack;
        }
        SingleFluidStorage tank = SingleFluidStorage.withFixedCapacity(TankBackpackData.CAPACITY, () -> {
        });
        FluidVariant fluidStack = FluidVariant.of(fluid);
        tank.variant = fluidStack;
        tank.amount = this.count;
        var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, context.getLevel().registryAccess());
        tank.writeValue(output);
        tags.put("Fluid", output.buildResult());
        stack.set(InitDataComponent.TANK_BACKPACK_TAG, tags);
        return stack;
    }

    public static class Builder extends LootItemConditionalFunction.Builder<SetTankCountFunction.Builder> {
        private final Fluid fluid;
        private final int bucketCount;

        public Builder(Fluid fluid, int bucketCount) {
            this.fluid = fluid;
            this.bucketCount = bucketCount;
        }

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public LootItemFunction build() {
            Identifier key = BuiltInRegistries.FLUID.getKey(fluid);
            return new SetTankCountFunction(this.getConditions(), key, bucketCount * FluidConstants.BUCKET);
        }
    }
}
