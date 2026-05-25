package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.datagen.LootTableGenerator;
import com.github.tartaricacid.touhoulittlemaid.loot.RandomBoardStateFunction;
import com.github.tartaricacid.touhoulittlemaid.loot.SetInitMaidOwnerFunction;
import com.github.tartaricacid.touhoulittlemaid.loot.SetTankCountFunction;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class InitLootModifier {
    public static final Identifier UNKNOWN_LOOT_TABLE = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "unknown_loot_table");

    public static final MapCodec<? extends LootItemConditionalFunction> SET_TANK_COUNT_FUNCTION =
            registerFunction("set_tank_count", SetTankCountFunction.CODEC);

    public static final MapCodec<? extends LootItemConditionalFunction> BOARD_STATE_RANDOMLY =
            registerFunction("board_state_randomly", RandomBoardStateFunction.CODEC);

    public static final MapCodec<? extends LootItemConditionalFunction> SET_INIT_MAID_OWNER_FUNCTION =
            registerFunction("set_init_maid_owner", SetInitMaidOwnerFunction.CODEC);

    private static MapCodec<? extends LootItemCondition> registerCondition(String id, MapCodec<? extends LootItemCondition> condition) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), condition);
    }

    private static MapCodec<? extends LootItemConditionalFunction> registerFunction(String id, MapCodec<? extends LootItemConditionalFunction> function) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), function);
    }

    public static void init() {
        // Global Modifier
        LootTableEvents.MODIFY.register((key, builder, source, provider) -> {
                    // all chests
                    if (key.identifier().toString().startsWith("minecraft:chests"))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.CHEST_POWER_POINT)));

                    if (key.equals(BuiltInLootTables.SPAWN_BONUS_CHEST))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SPAWN_BONUS)));
                    else if (key.equals(BuiltInLootTables.VILLAGE_TEMPLE))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.NORMAL_BAUBLE)));
                    else if (key.equals(BuiltInLootTables.VILLAGE_CARTOGRAPHER))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.RANDOM_BOARD_STATE)));
                    else if (key.equals(BuiltInLootTables.DESERT_PYRAMID))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.RARE_BAUBLE)));
                    else if (key.equals(BuiltInLootTables.JUNGLE_TEMPLE))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.RARE_BAUBLE)));
                    else if (key.equals(BuiltInLootTables.WOODLAND_MANSION))
                        builder.withPool(LootPool.lootPool()
                                .add(NestedLootTable.lootTableReference(LootTableGenerator.VERY_RARE_BAUBLE))
                                .add(NestedLootTable.lootTableReference(LootTableGenerator.STRUCTURE_SPAWN_MAID_GIFT))
                        );
                    else if (key.equals(BuiltInLootTables.SIMPLE_DUNGEON))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.FURNACE_OR_CRAFTING_TABLE_BACKPACK)));
                    else if (key.equals(BuiltInLootTables.ABANDONED_MINESHAFT))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.NORMAL_BACKPACK)));
                    else if (key.equals(BuiltInLootTables.NETHER_BRIDGE))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.TANK_BACKPACK)));
                    else if (key.equals(BuiltInLootTables.STRONGHOLD_CORRIDOR))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.ENDER_CHEST_BACKPACK)));
                    else if (key.equals(BuiltInLootTables.STRONGHOLD_LIBRARY))
                        builder.withPool(LootPool.lootPool()
                                .add(NestedLootTable.lootTableReference(LootTableGenerator.SHRINE_LESS))
                                .add(NestedLootTable.lootTableReference(LootTableGenerator.RANDOM_BOARD_STATE))
                        );
                    else if (key.equals(BuiltInLootTables.ANCIENT_CITY))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SHRINE_LESS)));
                    else if (key.equals(BuiltInLootTables.BASTION_TREASURE))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SHRINE_LESS)));
                    else if (key.equals(BuiltInLootTables.END_CITY_TREASURE))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SHRINE_MORE)));

                    else if (key.equals(BuiltInLootTables.BURIED_TREASURE))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.MAID_BURIED_TREASURE)));

                    else if (key.equals(BuiltInLootTables.PILLAGER_OUTPOST))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.STRUCTURE_SPAWN_MAID_GIFT)));

                    else if (key.equals(BuiltInLootTables.FISHING_JUNK))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.FISHING_POWER_POINT)));
                }
        );
    }
}
