package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.util.forge.EventHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
    @ModifyReturnValue(method = "mobsAt", at = @At("RETURN"))
    private static WeightedRandomList<MobSpawnSettings.SpawnerData> tlm$mobsAt(
            WeightedRandomList<MobSpawnSettings.SpawnerData> original,
            ServerLevel level,
            StructureManager structureManager,
            ChunkGenerator generator,
            MobCategory category,
            BlockPos pos,
            Holder<Biome> biome
    ) {
        return EventHooks.getPotentialSpawns(level, category, pos, original);
    }
}
