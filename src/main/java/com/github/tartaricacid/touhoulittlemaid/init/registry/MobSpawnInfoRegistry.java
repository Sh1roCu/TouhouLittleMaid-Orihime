package com.github.tartaricacid.touhoulittlemaid.init.registry;

import cn.sh1rocu.touhoulittlemaid.api.event.PotentialSpawnsEvent;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MiscConfig;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.Weighted;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.List;

import static com.github.tartaricacid.touhoulittlemaid.config.subconfig.MiscConfig.MAID_FAIRY_BLACKLIST_DIMENSION;

public final class MobSpawnInfoRegistry {
    private static Weighted<MobSpawnSettings.SpawnerData> SPAWNER_DATA;

    public static void addMobSpawnInfo(PotentialSpawnsEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            int spawnProbability = MiscConfig.MAID_FAIRY_SPAWN_PROBABILITY.get();
            if (spawnProbability <= 0) {
                // 优先判断等于 0 的情况，减少性能消耗
                return;
            }
            Identifier dimension = level.dimension().identifier();
            if (event.getMobCategory() == MobCategory.MONSTER && dimensionIsOkay(dimension)) {
                List<Weighted<MobSpawnSettings.SpawnerData>> spawnerData = event.getSpawnerDataList();
                boolean canZombieSpawn = spawnerData.stream().anyMatch(data -> data.value().type().equals(EntityType.ZOMBIE));
                if (SPAWNER_DATA == null || SPAWNER_DATA.weight() != spawnProbability) {
                    SPAWNER_DATA = new Weighted<>(new MobSpawnSettings.SpawnerData(InitEntities.FAIRY, 2, 4), spawnProbability);
                }
                if (canZombieSpawn) {
                    event.addSpawnerData(SPAWNER_DATA);
                }
            }
        }
    }

    private static boolean dimensionIsOkay(Identifier id) {
        return !MAID_FAIRY_BLACKLIST_DIMENSION.get().contains(id.toString());
    }
}
