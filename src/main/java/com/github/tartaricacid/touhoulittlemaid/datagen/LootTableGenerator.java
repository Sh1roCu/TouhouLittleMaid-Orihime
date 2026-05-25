package com.github.tartaricacid.touhoulittlemaid.datagen;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.block.BlockMaidBed;
import com.github.tartaricacid.touhoulittlemaid.block.BlockScarecrow;
import com.github.tartaricacid.touhoulittlemaid.init.InitBlocks;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.loot.RandomBoardStateFunction;
import com.github.tartaricacid.touhoulittlemaid.loot.SetInitMaidOwnerFunction;
import com.github.tartaricacid.touhoulittlemaid.loot.SetTankCountFunction;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class LootTableGenerator {
    public static final ResourceKey<LootTable> GIVE_SMART_SLAB = getLootTableKey("give_smart_slab");

    public static final ResourceKey<LootTable> ADVANCEMENT_POWER_POINT = getLootTableKey("advancement/power_point");
    public static final ResourceKey<LootTable> CAKE = getLootTableKey("advancement/cake");

    public static final ResourceKey<LootTable> CHEST_POWER_POINT = getLootTableKey("chest/power_point");
    public static final ResourceKey<LootTable> FISHING_POWER_POINT = getLootTableKey("fishing/power_point");

    public static final ResourceKey<LootTable> SHRINE_LESS = getLootTableKey("chest/shrine_less");
    public static final ResourceKey<LootTable> SHRINE_MORE = getLootTableKey("chest/shrine_more");

    public static final ResourceKey<LootTable> SPAWN_BONUS = getLootTableKey("chest/spawn_bonus");
    public static final ResourceKey<LootTable> NORMAL_BACKPACK = getLootTableKey("chest/normal_backpack");
    public static final ResourceKey<LootTable> FURNACE_OR_CRAFTING_TABLE_BACKPACK = getLootTableKey("chest/furnace_or_crafting_table_backpack");
    public static final ResourceKey<LootTable> TANK_BACKPACK = getLootTableKey("chest/tank_backpack");
    public static final ResourceKey<LootTable> ENDER_CHEST_BACKPACK = getLootTableKey("chest/ender_chest_backpack");

    public static final ResourceKey<LootTable> NORMAL_BAUBLE = getLootTableKey("chest/normal_bauble");
    public static final ResourceKey<LootTable> RARE_BAUBLE = getLootTableKey("chest/rare_bauble");
    public static final ResourceKey<LootTable> VERY_RARE_BAUBLE = getLootTableKey("chest/very_rare_bauble");

    public static final ResourceKey<LootTable> STRUCTURE_SPAWN_MAID_GIFT = getLootTableKey("chest/structure_spawn_maid_gift");
    public static final ResourceKey<LootTable> MAID_BURIED_TREASURE = getLootTableKey("chest/maid_buried_treasure");

    public static final ResourceKey<LootTable> RANDOM_BOARD_STATE = getLootTableKey("chest/random_board_state");

    public static ResourceKey<LootTable> getLootTableKey(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, name));
    }

    public static class ChestLootTables extends SimpleFabricLootTableSubProvider {
        private final HolderLookup.Provider registries;

        public ChestLootTables(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup, LootContextParamSets.CHEST);
            this.registries = registryLookup.join();
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
            consumer.accept(CHEST_POWER_POINT, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(InitItems.POWER_POINT)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                    .add(EmptyLootItem.emptyItem().setWeight(2))));

            consumer.accept(FISHING_POWER_POINT, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(InitItems.POWER_POINT))
                    .add(EmptyLootItem.emptyItem().setWeight(9))));

            consumer.accept(SHRINE_LESS, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(InitItems.SHRINE))
                    .add(EmptyLootItem.emptyItem().setWeight(9))));

            consumer.accept(SHRINE_MORE, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(InitItems.SHRINE))
                    .add(EmptyLootItem.emptyItem().setWeight(2))));

            consumer.accept(SPAWN_BONUS, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(InitItems.MAID_BACKPACK_SMALL).setWeight(3))
                            .add(LootItem.lootTableItem(InitItems.MAID_BACKPACK_MIDDLE).setWeight(9))
                            .add(LootItem.lootTableItem(InitItems.MAID_BACKPACK_BIG).setWeight(4)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(InitItems.POWER_POINT)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 9))))
                    ));

            consumer.accept(NORMAL_BACKPACK, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(InitItems.MAID_BACKPACK_SMALL).setWeight(3))
                    .add(LootItem.lootTableItem(InitItems.MAID_BACKPACK_MIDDLE).setWeight(9))
                    .add(LootItem.lootTableItem(InitItems.MAID_BACKPACK_BIG).setWeight(4))
                    .add(EmptyLootItem.emptyItem().setWeight(50))));

            consumer.accept(FURNACE_OR_CRAFTING_TABLE_BACKPACK, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(InitItems.FURNACE_BACKPACK))
                    .add(LootItem.lootTableItem(InitItems.CRAFTING_TABLE_BACKPACK))
                    .add(EmptyLootItem.emptyItem().setWeight(8))));

            var tank1 = LootItem.lootTableItem(InitItems.TANK_BACKPACK).apply(new SetTankCountFunction.Builder(Fluids.LAVA, 9));
            var tank2 = LootItem.lootTableItem(InitItems.TANK_BACKPACK).apply(new SetTankCountFunction.Builder(Fluids.LAVA, 4));
            var tank3 = LootItem.lootTableItem(InitItems.TANK_BACKPACK).apply(new SetTankCountFunction.Builder(Fluids.LAVA, 3));

            consumer.accept(TANK_BACKPACK, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(tank1).add(tank2).add(tank3)
                    .add(EmptyLootItem.emptyItem().setWeight(12))));

            consumer.accept(ENDER_CHEST_BACKPACK, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(InitItems.ENDER_CHEST_BACKPACK).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(4))));

            consumer.accept(NORMAL_BAUBLE, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1, 3))
                    // 有附魔的饰品
                    .add(LootItem.lootTableItem(InitItems.EXPLOSION_PROTECT_BAUBLE).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                    .add(LootItem.lootTableItem(InitItems.FIRE_PROTECT_BAUBLE).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                    .add(LootItem.lootTableItem(InitItems.PROJECTILE_PROTECT_BAUBLE).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                    .add(LootItem.lootTableItem(InitItems.MAGIC_PROTECT_BAUBLE).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                    .add(LootItem.lootTableItem(InitItems.FALL_PROTECT_BAUBLE).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                    .add(LootItem.lootTableItem(InitItems.DROWN_PROTECT_BAUBLE).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                    // 没有附魔的饰品
                    .add(LootItem.lootTableItem(InitItems.EXPLOSION_PROTECT_BAUBLE).setWeight(4))
                    .add(LootItem.lootTableItem(InitItems.FIRE_PROTECT_BAUBLE).setWeight(4))
                    .add(LootItem.lootTableItem(InitItems.PROJECTILE_PROTECT_BAUBLE).setWeight(4))
                    .add(LootItem.lootTableItem(InitItems.MAGIC_PROTECT_BAUBLE).setWeight(4))
                    .add(LootItem.lootTableItem(InitItems.FALL_PROTECT_BAUBLE).setWeight(4))
                    .add(LootItem.lootTableItem(InitItems.DROWN_PROTECT_BAUBLE).setWeight(4))
                    // 其他
                    .add(EmptyLootItem.emptyItem().setWeight(90))));

            consumer.accept(RARE_BAUBLE, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1, 2))
                    .add(LootItem.lootTableItem(InitItems.NIMBLE_FABRIC).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                    .add(LootItem.lootTableItem(InitItems.NIMBLE_FABRIC))
                    .add(LootItem.lootTableItem(InitItems.ITEM_MAGNET_BAUBLE))
                    .add(EmptyLootItem.emptyItem().setWeight(6))));

            consumer.accept(VERY_RARE_BAUBLE, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(InitItems.ULTRAMARINE_ORB_ELIXIR).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                    .add(LootItem.lootTableItem(InitItems.ULTRAMARINE_ORB_ELIXIR).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(4))));

            var setDamage = SetItemDamageFunction.setDamage(UniformGenerator.between(0.06f, 0.1f));
            consumer.accept(STRUCTURE_SPAWN_MAID_GIFT, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(Items.CAKE)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(InitItems.CAMERA).apply(setDamage))));

            consumer.accept(MAID_BURIED_TREASURE, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(InitItems.SMART_SLAB_EMPTY))
                            .add(EmptyLootItem.emptyItem().setWeight(4)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(InitItems.SHRINE))
                            .add(EmptyLootItem.emptyItem())));

            var library = RandomBoardStateFunction.create().addTag("library");
            consumer.accept(RANDOM_BOARD_STATE, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(InitItems.GOMOKU_BOARD_STATE).apply(library))
                            .add(LootItem.lootTableItem(InitItems.CCHESS_BOARD_STATE).apply(library))
                            .add(LootItem.lootTableItem(InitItems.WCHESS_BOARD_STATE).apply(library))));
        }
    }

    public static class AdvancementLootTables extends SimpleFabricLootTableSubProvider {
        public AdvancementLootTables(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup, LootContextParamSets.ADVANCEMENT_REWARD);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
            consumer.accept(GIVE_SMART_SLAB, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(InitItems.SMART_SLAB_INIT)
                                    .apply(SetInitMaidOwnerFunction.create())
                            )));

            consumer.accept(ADVANCEMENT_POWER_POINT, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(5))
                    .add(LootItem.lootTableItem(InitItems.POWER_POINT))));

            consumer.accept(CAKE, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(Items.CAKE))));
        }
    }

    public static class EntityLootTables extends FabricEntityLootSubProvider {
        public EntityLootTables(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generate() {
            add(InitEntities.BOX, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(Items.PAPER))));
        }
    }

    public static class BlockLootTables extends FabricBlockLootSubProvider {
        public final Set<Block> knownBlocks = new HashSet<>();

        protected BlockLootTables(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            add(InitBlocks.MAID_BED, block -> createSinglePropConditionTable(block, BlockMaidBed.PART, BedPart.HEAD));
            add(InitBlocks.SCARECROW, block -> createSinglePropConditionTable(block, BlockScarecrow.HALF, DoubleBlockHalf.LOWER));

            dropSelf(InitBlocks.MODEL_SWITCHER);
            dropSelf(InitBlocks.KEYBOARD);
            dropSelf(InitBlocks.BOOKSHELF);
            dropSelf(InitBlocks.COMPUTER);
            dropSelf(InitBlocks.SHRINE);
            dropSelf(InitBlocks.SNACK_CABINET);
        }

        @Override
        public void add(Block block, LootTable.Builder builder) {
            this.knownBlocks.add(block);
            super.add(block, builder);
        }
    }
}