package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleRegister;
import com.github.tartaricacid.touhoulittlemaid.entity.item.*;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityDanmaku;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityThrowPowerPoint;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.MaidFishingHook;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public final class InitEntities {
    public static void init() {
        addEntitySpawnPlacements();
    }

    public static EntityType<EntityMaid> MAID = registerEntityType("maid", EntityMaid.TYPE);
    public static EntityType<EntityChair> CHAIR = registerEntityType("chair", EntityChair.TYPE);
    public static EntityType<EntityFairy> FAIRY = registerEntityType("fairy", EntityFairy.TYPE);
    public static EntityType<EntityDanmaku> DANMAKU = registerEntityType("danmaku", EntityDanmaku.TYPE);
    public static EntityType<EntityPowerPoint> POWER_POINT = registerEntityType("power_point", EntityPowerPoint.TYPE);
    public static EntityType<EntityExtinguishingAgent> EXTINGUISHING_AGENT = registerEntityType("extinguishing_agent", EntityExtinguishingAgent.TYPE);
    public static EntityType<EntityBox> BOX = registerEntityType("box", EntityBox.TYPE);
    public static EntityType<EntityThrowPowerPoint> THROW_POWER_POINT = registerEntityType("throw_power_point", EntityThrowPowerPoint.TYPE);
    public static EntityType<EntityTombstone> TOMBSTONE = registerEntityType("tombstone", EntityTombstone.TYPE);
    public static EntityType<EntitySit> SIT = registerEntityType("sit", EntitySit.TYPE);
    public static EntityType<EntityBroom> BROOM = registerEntityType("broom", EntityBroom.TYPE);
    public static EntityType<MaidFishingHook> FISHING_HOOK = registerEntityType("fishing_hook", MaidFishingHook.TYPE);

    public static EntityDataSerializer<?> MAID_CHAT_BUBBLE_DATA_SERIALIZERS = registerDataSerializer("maid_chat_bubble", ChatBubbleRegister.INSTANCE);

    private static <T extends EntityType<?>> T registerEntityType(String id, T eType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), eType);
    }

    private static EntityDataSerializer<?> registerDataSerializer(String id, EntityDataSerializer<?> serializer) {
        FabricEntityDataRegistry.register(Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), serializer);
        return serializer;
    }

    private static void addEntitySpawnPlacements() {
        SpawnPlacements.register(InitEntities.FAIRY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFairy::checkFairySpawnRules);
    }
}
