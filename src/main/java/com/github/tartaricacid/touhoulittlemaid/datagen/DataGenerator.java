package com.github.tartaricacid.touhoulittlemaid.datagen;

import com.github.tartaricacid.touhoulittlemaid.datagen.tag.TagBlock;
import com.github.tartaricacid.touhoulittlemaid.datagen.tag.TagDamage;
import com.github.tartaricacid.touhoulittlemaid.datagen.tag.TagEntity;
import com.github.tartaricacid.touhoulittlemaid.datagen.tag.TagItem;
import com.github.tartaricacid.touhoulittlemaid.init.InitDamage;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        // Model
        pack.addProvider(ItemModelGenerator::new);

        // Advancements
        pack.addProvider(AdvancementDataGen::new);

        // Loot Tables
        pack.addProvider(LootTableGenerator.ChestLootTables::new);
        pack.addProvider(LootTableGenerator.AdvancementLootTables::new);
        pack.addProvider(LootTableGenerator.EntityLootTables::new);

        // Global Loot Modifier Fabric使用Event修改
        // pack.addProvider(packOutput -> new GlobalLootModifier(packOutput, registries, TouhouLittleMaid.MOD_ID));

        // Tags
        pack.addProvider(TagDamage::new);
        pack.addProvider(TagEntity::new);
        pack.addProvider(TagBlock::new);
        pack.addProvider(TagItem::new);

        // Dynamic data
        // DamageType etc.
        pack.addProvider(RegistryDataGenerator::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.DAMAGE_TYPE, InitDamage::bootstrap);
    }
}
