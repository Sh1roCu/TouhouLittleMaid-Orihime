package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.client.model.DebugFloorModel;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.blockentity.*;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.*;
import com.github.tartaricacid.touhoulittlemaid.entity.item.*;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityDanmaku;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityThrowPowerPoint;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.MaidFishingHook;
import com.github.tartaricacid.touhoulittlemaid.init.InitBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public final class InitEntitiesRender {
    public static void onEntityRenderers() {
        EntityRenderers.register(EntityMaid.TYPE, EntityMaidRenderer::new);
        EntityRenderers.register(EntityChair.TYPE, EntityChairRenderer::new);
        EntityRenderers.register(EntityFairy.TYPE, EntityFairyRenderer::new);
        EntityRenderers.register(EntityDanmaku.TYPE, EntityDanmakuRenderer::new);
        EntityRenderers.register(EntityPowerPoint.TYPE, EntityPowerPointRenderer::new);
        EntityRenderers.register(EntityExtinguishingAgent.TYPE, EntityExtinguishingAgentRenderer::new);
        EntityRenderers.register(EntityBox.TYPE, EntityBoxRender::new);
        EntityRenderers.register(EntityThrowPowerPoint.TYPE, ThrownItemRenderer::new);
        EntityRenderers.register(EntityTombstone.TYPE, EntityTombstoneRenderer::new);
        EntityRenderers.register(EntitySit.TYPE, EntitySitRenderer::new);
        EntityRenderers.register(EntityBroom.TYPE, EntityBroomRender::new);
        EntityRenderers.register(MaidFishingHook.TYPE, MaidFishingHookRenderer::new);

        BlockEntityRenderers.register(InitBlocks.ALTAR_BE, AltarRenderer::new);
        BlockEntityRenderers.register(InitBlocks.STATUE_BE, StatueRenderer::new);
        BlockEntityRenderers.register(InitBlocks.GARAGE_KIT_BE, GarageKitRenderer::new);
        BlockEntityRenderers.register(InitBlocks.GOMOKU_BE, GomokuRenderer::new);
        BlockEntityRenderers.register(InitBlocks.CCHESS_BE, CChessRenderer::new);
        BlockEntityRenderers.register(InitBlocks.WCHESS_BE, WChessRenderer::new);
        BlockEntityRenderers.register(InitBlocks.KEYBOARD_BE, KeyboardRenderer::new);
        BlockEntityRenderers.register(InitBlocks.BOOKSHELF_BE, BookshelfRenderer::new);
        BlockEntityRenderers.register(InitBlocks.COMPUTER_BE, ComputerRenderer::new);
        BlockEntityRenderers.register(InitBlocks.SHRINE_BE, ShrineRenderer::new);
        BlockEntityRenderers.register(InitBlocks.PICNIC_MAT_BE, PicnicMatRender::new);
        BlockEntityRenderers.register(InitBlocks.MAID_BED_BE, MaidBedRenderer::new);
        BlockEntityRenderers.register(InitBlocks.SNACK_CABINET_BE, SnackCabinetRenderer::new);
    }

    public static void onRegisterLayers() {
        ModelLayerRegistry.registerModelLayer(DebugFloorModel.LAYER, DebugFloorModel::createBodyLayer);
    }
}
