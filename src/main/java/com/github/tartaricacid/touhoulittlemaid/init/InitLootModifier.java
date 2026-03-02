package com.github.tartaricacid.touhoulittlemaid.init;

import cn.sh1rocu.touhoulittlemaid.api.extension.ILootTableBuilder;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.datagen.LootTableGenerator;
import com.github.tartaricacid.touhoulittlemaid.loot.RandomBoardStateFunction;
import com.github.tartaricacid.touhoulittlemaid.loot.SetInitMaidOwnerFunction;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class InitLootModifier {
    private static final ResourceLocation LAST = new ResourceLocation(TouhouLittleMaid.MOD_ID, "last");

    public static final LootItemFunctionType BOARD_STATE_RANDOMLY = registerFunction("board_state_randomly", new LootItemFunctionType(new RandomBoardStateFunction.Serializer()));
    public static final LootItemFunctionType SET_INIT_MAID_OWNER = registerFunction("set_init_maid_owner", new LootItemFunctionType(new SetInitMaidOwnerFunction.Serializer()));

    private static LootItemFunctionType registerFunction(String name, LootItemFunctionType function) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, name), function);
    }

    public static void init() {
        // Global Modifier
        LootTableEvents.MODIFY.register((resourceManager, lootManager, key, builder, source) -> {
                    // all chests
                    if (key.toString().startsWith("minecraft:chests"))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.CHEST_POWER_POINT)));

                    if (key.equals(BuiltInLootTables.SPAWN_BONUS_CHEST))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.SPAWN_BONUS)));
                    else if (key.equals(BuiltInLootTables.VILLAGE_TEMPLE))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.NORMAL_BAUBLE)));
                    else if (key.equals(BuiltInLootTables.VILLAGE_CARTOGRAPHER))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.RANDOM_BOARD_STATE)));
                    else if (key.equals(BuiltInLootTables.DESERT_PYRAMID))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.RARE_BAUBLE)));
                    else if (key.equals(BuiltInLootTables.JUNGLE_TEMPLE))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.RARE_BAUBLE)));
                    else if (key.equals(BuiltInLootTables.WOODLAND_MANSION))
                        builder.withPool(LootPool.lootPool()
                                .add(LootTableReference.lootTableReference(LootTableGenerator.VERY_RARE_BAUBLE))
                                .add(LootTableReference.lootTableReference(LootTableGenerator.STRUCTURE_SPAWN_MAID_GIFT))
                        );
                    else if (key.equals(BuiltInLootTables.SIMPLE_DUNGEON))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.FURNACE_OR_CRAFTING_TABLE_BACKPACK)));
                    else if (key.equals(BuiltInLootTables.ABANDONED_MINESHAFT))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.NORMAL_BACKPACK)));
                    else if (key.equals(BuiltInLootTables.NETHER_BRIDGE))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.TANK_BACKPACK)));
                    else if (key.equals(BuiltInLootTables.STRONGHOLD_CORRIDOR))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.ENDER_CHEST_BACKPACK)));
                    else if (key.equals(BuiltInLootTables.STRONGHOLD_LIBRARY))
                        builder.withPool(LootPool.lootPool()
                                .add(LootTableReference.lootTableReference(LootTableGenerator.SHRINE_LESS))
                                .add(LootTableReference.lootTableReference(LootTableGenerator.RANDOM_BOARD_STATE))
                        );
                    else if (key.equals(BuiltInLootTables.ANCIENT_CITY))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.SHRINE_LESS)));
                    else if (key.equals(BuiltInLootTables.BASTION_TREASURE))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.SHRINE_LESS)));
                    else if (key.equals(BuiltInLootTables.END_CITY_TREASURE))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.SHRINE_MORE)));

                    else if (key.equals(BuiltInLootTables.BURIED_TREASURE))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.MAID_BURIED_TREASURE)));

                    else if (key.equals(BuiltInLootTables.PILLAGER_OUTPOST))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.STRUCTURE_SPAWN_MAID_GIFT)));

                    else if (key.equals(BuiltInLootTables.FISHING_JUNK))
                        builder.withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(LootTableGenerator.FISHING_POWER_POINT)));
                }
        );

        LootTableEvents.MODIFY.addPhaseOrdering(Event.DEFAULT_PHASE, LAST);
        LootTableEvents.MODIFY.register(LAST,
                (resourceManager, lootManager, key, builder, source) -> ((ILootTableBuilder) builder).tlm$setId(key)
        );
    }
}
