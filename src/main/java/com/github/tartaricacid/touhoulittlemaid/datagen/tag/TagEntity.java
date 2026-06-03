package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TagEntity extends FabricTagsProvider.EntityTypeTagsProvider {
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

    public static TagKey<EntityType<?>> CARRYON_ENTITY_BLACKLIST = createTagKey(Identifier.parse("carryon:entity_blacklist"));

    /**
     * 冰与火的石化效果免疫标签
     */
    public static final TagKey<EntityType<?>> IMMUNE_TO_GORGON_STONE = createTagKey(
            Identifier.parse("iceandfire:immune_to_gorgon_stone")
    );


    public TagEntity(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    private static TagKey<EntityType<?>> createTagKey(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, IdentifierUtil.modLoc(name));
    }

    private static TagKey<EntityType<?>> createTagKey(Identifier id) {
        return TagKey.create(Registries.ENTITY_TYPE, id);
    }

    private static ResourceKey<EntityType<?>> createResourceKey(Identifier id) {
        return ResourceKey.create(Registries.ENTITY_TYPE, id);
    }

    @Override
    public void addTags(HolderLookup.@NonNull Provider lookupProvider) {
        valueLookupBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(InitEntities.DANMAKU);
        valueLookupBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(InitEntities.FAIRY);
        valueLookupBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(InitEntities.FAIRY);
        valueLookupBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(InitEntities.FAIRY);

        valueLookupBuilder(MAID_FAIRY_ATTACK_GOAL).add(EntityType.IRON_GOLEM);
        builder(MAID_FAIRY_ATTACK_GOAL)
                .addOptional(createResourceKey(id("guardvillagers:guard")))
                .addOptional(createResourceKey(id("earthtojavamobs:furnace_golem")))
                .addOptional(createResourceKey(id("earthmobsmod:furnace_golem")))
                .addOptional(createResourceKey(id("mutantmonsters:mutant_snow_golem")))
                .addOptional(createResourceKey(id("alexscaves:gingerbread_man")))
                .addOptional(createResourceKey(id("alexsmobs:bunfungus")));

        builder(MAID_VEHICLE_ROTATE_BLOCKLIST)
                .addOptional(createResourceKey(id("create:carriage_contraption")))
                .addOptional(createResourceKey(id("create:seat")));

        valueLookupBuilder(CARRYON_ENTITY_BLACKLIST).add(
                InitEntities.TOMBSTONE,
                InitEntities.SIT,
                InitEntities.BROOM);

        // 让女仆免疫冰与火的石化效果，避免石化带来的各种问题
        valueLookupBuilder(IMMUNE_TO_GORGON_STONE).add(InitEntities.MAID);
    }

    private Identifier id(String name) {
        return Identifier.parse(name);
    }
}
