package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.advancements.altar.AltarCraftTrigger;
import com.github.tartaricacid.touhoulittlemaid.advancements.maid.MaidEventTrigger;
import com.github.tartaricacid.touhoulittlemaid.advancements.rewards.GivePatchouliBookConfigTrigger;
import com.github.tartaricacid.touhoulittlemaid.advancements.rewards.GiveSmartSlabConfigTrigger;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public final class InitTrigger {
    public static void init() {

    }

    public static final GiveSmartSlabConfigTrigger GIVE_SMART_SLAB_CONFIG = register("give_smart_slab_config", new GiveSmartSlabConfigTrigger());
    public static final GivePatchouliBookConfigTrigger GIVE_PATCHOULI_BOOK_CONFIG = register("give_patchouli_book_config", new GivePatchouliBookConfigTrigger());
    public static final AltarCraftTrigger ALTAR_CRAFT = register("altar_craft", new AltarCraftTrigger());
    public static final MaidEventTrigger MAID_EVENT = register("maid_event", new MaidEventTrigger());

    private static <T extends CriterionTrigger<?>> T register(String id, T trigger) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), trigger);
    }
}
