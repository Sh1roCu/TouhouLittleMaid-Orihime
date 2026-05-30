package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.sensor.MaidHostilesSensor;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.sensor.MaidNearestLivingEntitySensor;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.sensor.MaidPickupEntitiesSensor;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.edible.MaidEdibleBlockAction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Optional;

public interface InitBrains {
    Activity RIDE_IDLE = registerActivity("ride_idle", new Activity("tlm_ride_idle"));
    Activity RIDE_WORK = registerActivity("ride_work", new Activity("tlm_ride_work"));
    Activity RIDE_REST = registerActivity("ride_rest", new Activity("tlm_ride_rest"));

    MemoryModuleType<List<Entity>> VISIBLE_PICKUP_ENTITIES = registerMemoryModuleType(
            "visible_pickup_entities",
            new MemoryModuleType<>(Optional.empty())
    );

    MemoryModuleType<PositionTracker> TARGET_POS = registerMemoryModuleType(
            "target_pos",
            new MemoryModuleType<>(Optional.empty())
    );

    MemoryModuleType<MaidEdibleBlockAction> MAID_EDIBLE_BLOCK_ACTION = registerMemoryModuleType(
            "maid_edible_block_action",
            new MemoryModuleType<>(Optional.empty())
    );

    SensorType<MaidNearestLivingEntitySensor> MAID_NEAREST_LIVING_ENTITY_SENSOR = registerSensorType(
            "maid_nearest_living_entity",
            new SensorType<>(MaidNearestLivingEntitySensor::new)
    );

    SensorType<MaidHostilesSensor> MAID_HOSTILES_SENSOR = registerSensorType(
            "maid_hostiles",
            new SensorType<>(MaidHostilesSensor::new)
    );

    SensorType<MaidPickupEntitiesSensor> MAID_PICKUP_ENTITIES_SENSOR = registerSensorType(
            "maid_pickup_entities",
            new SensorType<>(MaidPickupEntitiesSensor::new)
    );

    EnvironmentAttribute<Activity> MAID_DAY_SHIFT_ACTIVITY = registerEnvironment(
            "gameplay/maid_day_shift_activity",
            EnvironmentAttribute
                    .builder(AttributeTypes.ACTIVITY)
                    .defaultValue(Activity.IDLE)
                    .build());

    EnvironmentAttribute<Activity> MAID_NIGHT_SHIFT_ACTIVITY = registerEnvironment(
            "gameplay/maid_night_shift_activity",
            EnvironmentAttribute
                    .builder(AttributeTypes.ACTIVITY)
                    .defaultValue(Activity.IDLE)
                    .build());

    EnvironmentAttribute<Activity> MAID_ALL_DAY_ACTIVITY = registerEnvironment(
            "gameplay/maid_all_day_activity",
            EnvironmentAttribute
                    .builder(AttributeTypes.ACTIVITY)
                    .defaultValue(Activity.WORK)
                    .build()
    );

    private static <T extends MemoryModuleType<?>> T registerMemoryModuleType(String id, T mType) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), mType);
    }

    private static <T extends SensorType<?>> T registerSensorType(String id, T sType) {
        return Registry.register(BuiltInRegistries.SENSOR_TYPE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), sType);
    }

    private static EnvironmentAttribute<Activity> registerEnvironment(String id, EnvironmentAttribute<Activity> schedule) {
        return Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), schedule);
    }

    private static Activity registerActivity(String id, Activity activity) {
        return Registry.register(BuiltInRegistries.ACTIVITY, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), activity);
    }

    static void init() {

    }
}
