package com.github.tartaricacid.touhoulittlemaid.datagen;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;

public class ItemModelGenerator extends FabricModelProvider {
    public ItemModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        // 这里只生成普通model，动态model使用ModelLoadingPlugin
        basicItem(InitItems.OWNER_CONVERSION_TOOL, generators);
        basicItem(InitItems.GOMOKU_BOARD_STATE, generators);
        basicItem(InitItems.CCHESS_BOARD_STATE, generators);
        basicItem(InitItems.WCHESS_BOARD_STATE, generators);
    }

    private static void basicItem(Item item, ItemModelGenerators generators) {
        generators.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }
}