package com.github.tartaricacid.touhoulittlemaid.datagen;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.advancements.rewards.GiveSmartSlabConfigTrigger;
import com.github.tartaricacid.touhoulittlemaid.datagen.advancement.BaseAdvancement;
import com.github.tartaricacid.touhoulittlemaid.datagen.advancement.ChallengeAdvancement;
import com.github.tartaricacid.touhoulittlemaid.datagen.advancement.FavorabilityAdvancement;
import com.github.tartaricacid.touhoulittlemaid.datagen.advancement.MaidBaseAdvancement;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementDataGen extends FabricAdvancementProvider {
    public AdvancementDataGen(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> saver) {
        genGiveSmartSlabAdvancement(saver);
        genMainAdvancement(provider, saver);
    }

    private static void genGiveSmartSlabAdvancement(Consumer<AdvancementHolder> saver) {
        Advancement.Builder.advancement()
                .addCriterion("tick", GiveSmartSlabConfigTrigger.Instance.instance())
                .rewards(AdvancementRewards.Builder.loot(LootTableGenerator.GIVE_SMART_SLAB))
                .save(saver, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "give_smart_slab").toString());
    }

    private static void genMainAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
        BaseAdvancement.generate(registries, saver);
        MaidBaseAdvancement.generate(registries, saver);
        FavorabilityAdvancement.generate(saver);
        ChallengeAdvancement.generate(saver);
    }
}
