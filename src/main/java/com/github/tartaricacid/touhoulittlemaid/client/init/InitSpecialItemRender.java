package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.item.PerspectiveBakedModel;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.item.ReplaceableBakedModel;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.VanillaConfig;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public final class InitSpecialItemRender implements ModelLoadingPlugin {
    private static final List<Pair<ModelResourceLocation, ResourceLocation>> PERSPECTIVE_MODEL_LIST = Lists.newArrayList();
    private static final List<Triple<ModelResourceLocation, ResourceLocation, Supplier<Boolean>>> REPLACEABLE_MODEL_LIST = Lists.newArrayList();

    private static final ResourceLocation LIFE_POINT = new ResourceLocation(TouhouLittleMaid.MOD_ID, "life_point");
    private static final ResourceLocation POINT_ITEM = new ResourceLocation(TouhouLittleMaid.MOD_ID, "point_item");

    // 祭坛合成占位符的物品模型
    private static final ResourceLocation SPAWN_BOX = new ResourceLocation(TouhouLittleMaid.MOD_ID, "item/spawn_box");
    private static final ResourceLocation REBORN_MAID = new ResourceLocation(TouhouLittleMaid.MOD_ID, "item/reborn_maid");
    private static final ResourceLocation SPAWN_LIGHTNING_BOLT = new ResourceLocation(TouhouLittleMaid.MOD_ID, "item/spawn_lightning_bolt");

    @Override
    public void onInitializeModelLoader(Context plugin) {
        register();
        registerModels(plugin);
        plugin.modifyModelAfterBake().register((bakedModel, context) -> {
            if (bakedModel == null)
                return bakedModel;
            ResourceLocation id = context.id();
            for (Pair<ModelResourceLocation, ResourceLocation> pair : PERSPECTIVE_MODEL_LIST) {
                if (id instanceof ModelResourceLocation modelId && modelId.equals(pair.getLeft())) {
                    BakedModel newModel = context.baker().bake(pair.getRight(), context.settings());
                    if (newModel != null)
                        return new PerspectiveBakedModel(bakedModel, newModel);
                }
            }

            for (Triple<ModelResourceLocation, ResourceLocation, Supplier<Boolean>> triple : REPLACEABLE_MODEL_LIST) {
                if (id instanceof ModelResourceLocation modelId && modelId.equals(triple.getLeft())) {
                    BakedModel newModel = context.baker().bake(triple.getMiddle(), context.settings());
                    if (newModel != null)
                        return new ReplaceableBakedModel(bakedModel, newModel, triple.getRight());
                }
            }
            return bakedModel;
        });
    }

    public static void register() {
        addInHandModel(InitItems.HAKUREI_GOHEI);
        addInHandModel(InitItems.SANAE_GOHEI);
        addInHandModel(InitItems.EXTINGUISHER);
        addInHandModel(InitItems.CAMERA);
        addInHandModel(InitItems.MAID_BEACON);
        addInHandModel(InitItems.SNACK_CABINET);

        addReplaceableModel(Items.TOTEM_OF_UNDYING, LIFE_POINT, () -> VanillaConfig.REPLACE_TOTEM_TEXTURE.get());
        addReplaceableModel(Items.EXPERIENCE_BOTTLE, POINT_ITEM, () -> VanillaConfig.REPLACE_XP_BOTTLE_TEXTURE.get());
    }

    public static void registerModels(Context plugin) {
        plugin.addModels(PERSPECTIVE_MODEL_LIST.stream().map(Pair::getRight).toList());
        plugin.addModels(REPLACEABLE_MODEL_LIST.stream().map(Triple::getMiddle).toList());

        // 特殊需要额外注册的模型
        plugin.addModels(SPAWN_BOX);
        plugin.addModels(REBORN_MAID);
        plugin.addModels(SPAWN_LIGHTNING_BOLT);
    }

    public static void addInHandModel(Item item) {
        ResourceLocation res = BuiltInRegistries.ITEM.getKey(item);
        if (!res.equals(BuiltInRegistries.ITEM.getDefaultKey())) {
            ModelResourceLocation rawName = new ModelResourceLocation(res, "inventory");
            ResourceLocation inHandName = new ResourceLocation(res.getNamespace(), "item/" + res.getPath() + "_in_hand");
            PERSPECTIVE_MODEL_LIST.add(Pair.of(rawName, inHandName));
        }
    }

    public static void addReplaceableModel(Item item, ResourceLocation replacedModel, Supplier<Boolean> isReplace) {
        ResourceLocation res = BuiltInRegistries.ITEM.getKey(item);
        if (!res.equals(BuiltInRegistries.ITEM.getDefaultKey())) {
            ModelResourceLocation rawModelResourceLocation = new ModelResourceLocation(res, "inventory");
            ResourceLocation replacedModelResourceLocation = new ResourceLocation(replacedModel.getNamespace(), "item/" + replacedModel.getPath());
            REPLACEABLE_MODEL_LIST.add(Triple.of(rawModelResourceLocation, replacedModelResourceLocation, isReplace));
        }
    }
}
