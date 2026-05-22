package com.github.tartaricacid.touhoulittlemaid.loot;

import cn.sh1rocu.touhoulittlemaid.api.extension.ILootContext;
import cn.sh1rocu.touhoulittlemaid.mixin.accessor.LootContextParamSetsAccessor;
import com.github.tartaricacid.touhoulittlemaid.init.InitLootModifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public record LootTableTypeCondition(Identifier lootTableType,
                                     @Nullable ResourceKey<LootTable> lootTableId,
                                     ResourceKey<LootTable> lootTableAdd) implements LootItemCondition {
    public static final MapCodec<LootTableTypeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("loot_table_type").forGetter(m -> m.lootTableType),
            ResourceKey.codec(Registries.LOOT_TABLE).optionalFieldOf("loot_table_id").forGetter(m -> Optional.ofNullable(m.lootTableId)),
            ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table_add").forGetter(m -> m.lootTableAdd)
    ).apply(instance, (type, id, add)
            -> new LootTableTypeCondition(type, id.orElse(null), add)));

    @Override
    public boolean test(LootContext context) {
        Identifier currentLootTable = ((ILootContext) context).tlm$getQueriedLootTableId();
        return !currentLootTable.equals(lootTableAdd.location()) && typeAreEquals(context) && idAreEquals(context);
    }

    private boolean typeAreEquals(LootContext context) {
        ResourceKey<LootTable> currentLootTable = ResourceKey.create(Registries.LOOT_TABLE, ((ILootContext) context).tlm$getQueriedLootTableId());
        LootContextParamSet lootContextParamSet = LootContextParamSetsAccessor.tlm$getRegistry().get(lootTableType);
        return context.getResolver().get(Registries.LOOT_TABLE, currentLootTable).map(lootTable ->
                        Objects.equals(lootTable.value().getParamSet(), lootContextParamSet))
                .orElse(false);
    }

    private boolean idAreEquals(LootContext context) {
        if (this.lootTableId == null) {
            return true;
        }
        return ((ILootContext) context).tlm$getQueriedLootTableId().equals(this.lootTableId.location());
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return InitLootModifier.LOOT_TABLE_TYPE;
    }
}