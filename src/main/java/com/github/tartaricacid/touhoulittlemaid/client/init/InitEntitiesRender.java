package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.client.model.DebugFloorModel;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.*;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.*;
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
import net.minecraft.world.entity.EntityType;

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

        BlockEntityRenderers.register(InitBlocks.ALTAR_TE, TileEntityAltarRenderer::new);
        BlockEntityRenderers.register(InitBlocks.STATUE_TE, TileEntityStatueRenderer::new);
        BlockEntityRenderers.register(InitBlocks.GARAGE_KIT_TE, TileEntityGarageKitRenderer::new);
        BlockEntityRenderers.register(InitBlocks.GOMOKU_TE, TileEntityGomokuRenderer::new);
        BlockEntityRenderers.register(InitBlocks.CCHESS_TE, TileEntityCChessRenderer::new);
        BlockEntityRenderers.register(InitBlocks.WCHESS_TE, TileEntityWChessRenderer::new);
        BlockEntityRenderers.register(InitBlocks.KEYBOARD_TE, TileEntityKeyboardRenderer::new);
        BlockEntityRenderers.register(InitBlocks.BOOKSHELF_TE, TileEntityBookshelfRenderer::new);
        BlockEntityRenderers.register(InitBlocks.COMPUTER_TE, TileEntityComputerRenderer::new);
        BlockEntityRenderers.register(InitBlocks.SHRINE_TE, TileEntityShrineRenderer::new);
        BlockEntityRenderers.register(InitBlocks.PICNIC_MAT_TE, PicnicMatRender::new);
        BlockEntityRenderers.register(InitBlocks.MAID_BED_TE, TileEntityMaidBedRenderer::new);
        BlockEntityRenderers.register(InitBlocks.SNACK_CABINET_TE, TileEntitySnackCabinetRenderer::new);
    }

    public static void onRegisterLayers() {
        ModelLayerRegistry.registerModelLayer(DebugFloorModel.LAYER, DebugFloorModel::createBodyLayer);
    }
}
