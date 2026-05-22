package com.github.tartaricacid.touhoulittlemaid.datagen;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.advancements.rewards.GiveSmartSlabConfigTrigger;
import com.github.tartaricacid.touhoulittlemaid.datagen.advancement.BaseAdvancement;
import com.github.tartaricacid.touhoulittlemaid.datagen.advancement.ChallengeAdvancement;
import com.github.tartaricacid.touhoulittlemaid.datagen.advancement.FavorabilityAdvancement;
import com.github.tartaricacid.touhoulittlemaid.datagen.advancement.MaidBaseAdvancement;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementDataGen extends FabricAdvancementProvider {
    public AdvancementDataGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        genGiveSmartSlabAdvancement(consumer);
        genMainAdvancement(consumer);
    }

    private static void genGiveSmartSlabAdvancement(Consumer<AdvancementHolder> consumer) {
        Advancement.Builder.advancement()
                .addCriterion("tick", GiveSmartSlabConfigTrigger.Instance.instance())
                .rewards(AdvancementRewards.Builder.loot(LootTableGenerator.GIVE_SMART_SLAB))
                .save(consumer, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "give_smart_slab").toString());
    }

    private static void genMainAdvancement(Consumer<AdvancementHolder> consumer) {
        BaseAdvancement.generate(consumer);
        MaidBaseAdvancement.generate(consumer);
        FavorabilityAdvancement.generate(consumer);
        ChallengeAdvancement.generate(consumer);
    }
}
