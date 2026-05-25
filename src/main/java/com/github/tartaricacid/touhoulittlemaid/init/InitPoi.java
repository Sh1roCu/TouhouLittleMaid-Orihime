package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.poi.MaidPoiManager;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class InitPoi {
    public static void init() {

    }

    public static final PoiType MAID_BED = register("maid_bed", MaidPoiManager.getMaidBed());
    public static final PoiType JOY_BLOCK = register("joy_block", MaidPoiManager.getJoyBlock());
    public static final PoiType HOME_MEAL_BLOCK = register("home_meal", MaidPoiManager.getHomeMeal());
    public static final PoiType SCARECROW = register("scarecrow", MaidPoiManager.getScarecrow());

    private static PoiType register(String id, PoiType type) {
        return PoiHelper.register(
                Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id),
                type.maxTickets(), type.validRange(), type.matchingStates());
    }
}