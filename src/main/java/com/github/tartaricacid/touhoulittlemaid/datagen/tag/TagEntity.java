package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class TagEntity extends FabricTagProvider<EntityType<?>> {
    /**
     * 女仆妖精的攻击目标，默认仅攻击铁傀儡和玩家
     */
    public static TagKey<EntityType<?>> MAID_FAIRY_ATTACK_GOAL = createTagKey("maid_fairy_attack_goal");

    /**
     * 女仆在骑乘时，为了朝向一致，会强制同步女仆朝向和当前骑乘实体朝向；
     * <p>
     * 但是部分模组（如机械动力）这么做反而会导致女仆异常旋转，故添加此标签
     */
    public static TagKey<EntityType<?>> MAID_VEHICLE_ROTATE_BLOCKLIST = createTagKey("maid_vehicle_rotate_blocklist");

    /**
     * 仅 1.20.1 需要修正的问题，工业先锋的生物捕捉工具复制女仆问题
     */
    public static final TagKey<EntityType<?>> MOB_IMPRISONMENT_TOOL_BLACKLIST = createTagKey(
            new ResourceLocation("industrialforegoing:mob_imprisonment_tool_blacklist")
    );

    /**
     * 冰与火的石化效果免疫标签
     */
    public static final TagKey<EntityType<?>> IMMUNE_TO_GORGON_STONE = createTagKey(
            new ResourceLocation("iceandfire:immune_to_gorgon_stone")
    );

    public TagEntity(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ENTITY_TYPE, lookupProvider);
    }

    private static TagKey<EntityType<?>> createTagKey(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, name));
    }

    private static TagKey<EntityType<?>> createTagKey(ResourceLocation id) {
        return TagKey.create(Registries.ENTITY_TYPE, id);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
        getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(InitEntities.DANMAKU);
        getOrCreateTagBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(InitEntities.FAIRY);
        getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(InitEntities.FAIRY);
        getOrCreateTagBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(InitEntities.FAIRY);

        getOrCreateTagBuilder(MAID_FAIRY_ATTACK_GOAL).add(EntityType.IRON_GOLEM)
                .addOptional(id("guardvillagers:guard"))
                .addOptional(id("earthtojavamobs:furnace_golem"))
                .addOptional(id("earthmobsmod:furnace_golem"))
                .addOptional(id("mutantmonsters:mutant_snow_golem"))
                .addOptional(id("alexscaves:gingerbread_man"))
                .addOptional(id("alexsmobs:bunfungus"));

        getOrCreateTagBuilder(MAID_VEHICLE_ROTATE_BLOCKLIST)
                .addOptional(id("create:carriage_contraption"))
                .addOptional(id("create:seat"));

        // 仅 1.20.1 需要修正的问题，生物捕捉工具复制女仆问题
        getOrCreateTagBuilder(MOB_IMPRISONMENT_TOOL_BLACKLIST).add(InitEntities.MAID);

        // 让女仆免疫冰与火的石化效果，避免石化带来的各种问题
        getOrCreateTagBuilder(IMMUNE_TO_GORGON_STONE).add(InitEntities.MAID);
    }

    private ResourceLocation id(String name) {
        return new ResourceLocation(name);
    }
}
