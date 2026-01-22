package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.MaidSchedule;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.sensor.MaidHostilesSensor;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.sensor.MaidNearestLivingEntitySensor;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.sensor.MaidPickupEntitiesSensor;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.edible.MaidEdibleBlockAction;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleRegister;
import com.github.tartaricacid.touhoulittlemaid.entity.item.*;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityDanmaku;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityThrowPowerPoint;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.MaidFishingHook;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;
import java.util.Optional;

public final class InitEntities {
    public static void init() {
        registerSerializer();
        addEntityAttributes();
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

    public static Activity RIDE_IDLE = registerActivity("ride_idle", new Activity("tlm_ride_idle"));
    public static Activity RIDE_WORK = registerActivity("ride_work", new Activity("tlm_ride_work"));
    public static Activity RIDE_REST = registerActivity("ride_rest", new Activity("tlm_ride_rest"));

    public static MemoryModuleType<List<Entity>> VISIBLE_PICKUP_ENTITIES = registerMemoryModuleType("visible_pickup_entities", new MemoryModuleType<>(Optional.empty()));
    public static MemoryModuleType<PositionTracker> TARGET_POS = registerMemoryModuleType("target_pos", new MemoryModuleType<>(Optional.empty()));
    public static MemoryModuleType<MaidEdibleBlockAction> MAID_EDIBLE_BLOCK_ACTION = registerMemoryModuleType("maid_edible_block_action", new MemoryModuleType<>(Optional.empty()));
    public static SensorType<MaidNearestLivingEntitySensor> MAID_NEAREST_LIVING_ENTITY_SENSOR = registerSensorType("maid_nearest_living_entity", new SensorType<>(MaidNearestLivingEntitySensor::new));
    public static SensorType<MaidHostilesSensor> MAID_HOSTILES_SENSOR = registerSensorType("maid_hostiles", new SensorType<>(MaidHostilesSensor::new));
    public static SensorType<MaidPickupEntitiesSensor> MAID_PICKUP_ENTITIES_SENSOR = registerSensorType("maid_pickup_entities", new SensorType<>(MaidPickupEntitiesSensor::new));

    public static Schedule MAID_DAY_SHIFT_SCHEDULES = registerSchedule("maid_day_shift_schedules",
            // 06:00 ~ 18:00 工作
            // 18:00 ~ 22:00 娱乐
            // 22:00 ~ 06:00 睡觉
            new ScheduleBuilder(new Schedule())
                    .changeActivityAt(0, Activity.WORK)
                    .changeActivityAt(12000, Activity.IDLE)
                    .changeActivityAt(16000, Activity.REST)
                    .build()
    );
    public static Schedule MAID_NIGHT_SHIFT_SCHEDULES = registerSchedule("maid_night_shift_schedules",
            // 18:00 ~ 06:00 工作
            // 06:00 ~ 14:00 睡觉
            // 14:00 ~ 18:00 娱乐
            new ScheduleBuilder(new Schedule())
                    .changeActivityAt(0, Activity.REST)
                    .changeActivityAt(8000, Activity.IDLE)
                    .changeActivityAt(12000, Activity.WORK)
                    .build()
    );

    public static Schedule MAID_ALL_DAY_SCHEDULES = registerSchedule("maid_all_day_schedules",
            new ScheduleBuilder(new Schedule()).changeActivityAt(0, Activity.WORK).build());

    private static <T extends EntityType<?>> T registerEntityType(String id, T eType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), eType);
    }

    private static <T extends MemoryModuleType<?>> T registerMemoryModuleType(String id, T mType) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), mType);
    }

    private static <T extends SensorType<?>> T registerSensorType(String id, T sType) {
        return Registry.register(BuiltInRegistries.SENSOR_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), sType);
    }

    private static Schedule registerSchedule(String id, Schedule schedule) {
        return Registry.register(BuiltInRegistries.SCHEDULE, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), schedule);
    }

    private static Activity registerActivity(String id, Activity activity) {
        return Registry.register(BuiltInRegistries.ACTIVITY, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), activity);
    }

    private static void registerSerializer() {
        EntityDataSerializers.registerSerializer(MaidSchedule.DATA);
        EntityDataSerializers.registerSerializer(ChatBubbleRegister.INSTANCE);
    }

    private static void addEntityAttribute(EntityType<? extends LivingEntity> type, AttributeSupplier.Builder builder) {
        FabricDefaultAttributeRegistry.register(type, builder);
    }

    private static void addEntityAttributes() {
        addEntityAttribute(EntityMaid.TYPE, EntityMaid.createAttributes());
        addEntityAttribute(EntityChair.TYPE, LivingEntity.createLivingAttributes());
        addEntityAttribute(EntityBroom.TYPE, LivingEntity.createLivingAttributes());
        addEntityAttribute(EntityFairy.TYPE, EntityFairy.createFairyAttributes());
    }

    private static void addEntitySpawnPlacements() {
        SpawnPlacements.register(InitEntities.FAIRY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFairy::checkFairySpawnRules);
    }
}
