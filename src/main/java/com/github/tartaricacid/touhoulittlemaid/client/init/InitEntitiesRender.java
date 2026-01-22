package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.client.model.DebugFloorModel;
import com.github.tartaricacid.touhoulittlemaid.client.model.NewEntityFairyModel;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.*;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.*;
import com.github.tartaricacid.touhoulittlemaid.entity.item.*;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityDanmaku;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityThrowPowerPoint;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.MaidFishingHook;
import com.github.tartaricacid.touhoulittlemaid.tileentity.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;

@Environment(EnvType.CLIENT)
public final class InitEntitiesRender {
    public static void onEntityRenderers() {
        EntityRendererRegistry.register(EntityMaid.TYPE, EntityMaidRenderer::new);
        EntityRendererRegistry.register(EntityChair.TYPE, EntityChairRenderer::new);
        EntityRendererRegistry.register(EntityFairy.TYPE, EntityFairyRenderer::new);
        EntityRendererRegistry.register(EntityDanmaku.TYPE, EntityDanmakuRenderer::new);
        EntityRendererRegistry.register(EntityPowerPoint.TYPE, EntityPowerPointRenderer::new);
        EntityRendererRegistry.register(EntityExtinguishingAgent.TYPE, EntityExtinguishingAgentRenderer::new);
        EntityRendererRegistry.register(EntityBox.TYPE, EntityBoxRender::new);
        EntityRendererRegistry.register(EntityThrowPowerPoint.TYPE, ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityTombstone.TYPE, EntityTombstoneRenderer::new);
        EntityRendererRegistry.register(EntitySit.TYPE, EntitySitRenderer::new);
        EntityRendererRegistry.register(EntityBroom.TYPE, EntityBroomRender::new);
        EntityRendererRegistry.register(MaidFishingHook.TYPE, MaidFishingHookRenderer::new);

        EntityRendererRegistry.register(EntityType.SLIME, EntityYukkuriSlimeRender::new);
        EntityRendererRegistry.register(EntityType.MAGMA_CUBE, EntityMarisaYukkuriSlimeRender::new);
        EntityRendererRegistry.register(EntityType.EXPERIENCE_ORB, ReplaceExperienceOrbRenderer::new);

        BlockEntityRenderers.register(TileEntityAltar.TYPE, TileEntityAltarRenderer::new);
        BlockEntityRenderers.register(TileEntityStatue.TYPE, TileEntityStatueRenderer::new);
        BlockEntityRenderers.register(TileEntityGarageKit.TYPE, TileEntityGarageKitRenderer::new);
        BlockEntityRenderers.register(TileEntityGomoku.TYPE, TileEntityGomokuRenderer::new);
        BlockEntityRenderers.register(TileEntityCChess.TYPE, TileEntityCChessRenderer::new);
        BlockEntityRenderers.register(TileEntityWChess.TYPE, TileEntityWChessRenderer::new);
        BlockEntityRenderers.register(TileEntityKeyboard.TYPE, TileEntityKeyboardRenderer::new);
        BlockEntityRenderers.register(TileEntityBookshelf.TYPE, TileEntityBookshelfRenderer::new);
        BlockEntityRenderers.register(TileEntityComputer.TYPE, TileEntityComputerRenderer::new);
        BlockEntityRenderers.register(TileEntityShrine.TYPE, TileEntityShrineRenderer::new);
        BlockEntityRenderers.register(TileEntityPicnicMat.TYPE, PicnicMatRender::new);
        BlockEntityRenderers.register(TileEntityMaidBed.TYPE, TileEntityMaidBedRenderer::new);
        BlockEntityRenderers.register(TileEntitySnackCabinet.TYPE, TileEntitySnackCabinetRenderer::new);
    }

    public static void onRegisterLayers() {
//        EntityModelLayerRegistry.registerModelLayer(AltarModel.LAYER, AltarModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(DebugFloorModel.LAYER, DebugFloorModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(EntityBoxModel.LAYER, EntityBoxModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(EntityFairyModel.LAYER, EntityFairyModel::createBodyLayer);
        // 为了别的模组兼容，暂时保留
        EntityModelLayerRegistry.registerModelLayer(NewEntityFairyModel.LAYER, NewEntityFairyModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(BigBackpackModel.LAYER, BigBackpackModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(MiddleBackpackModel.LAYER, MiddleBackpackModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(SmallBackpackModel.LAYER, SmallBackpackModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(StatueBaseModel.LAYER, StatueBaseModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(EntityYukkuriModel.LAYER, EntityYukkuriModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(EntityMarisaYukkuriModel.LAYER, EntityMarisaYukkuriModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(GomokuModel.LAYER, GomokuModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(CChessModel.LAYER, CChessModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(WChessModel.LAYER, WChessModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(PieceModel.LAYER, PieceModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(CraftingTableBackpackModel.LAYER, CraftingTableBackpackModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(EnderChestBackpackModel.LAYER, EnderChestBackpackModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(FurnaceBackpackModel.LAYER, FurnaceBackpackModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(TankBackpackModel.LAYER, TankBackpackModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(TombstoneModel.LAYER, TombstoneModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(KeyboardModel.LAYER, KeyboardModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(BookshelfModel.LAYER, BookshelfModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(ComputerModel.LAYER, ComputerModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(ShrineModel.LAYER, ShrineModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(MaidBannerModel.LAYER, MaidBannerModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(BroomModel.LAYER, BroomModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(PicnicBasketModel.LAYER, PicnicBasketModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(PicnicMatModel.LAYER, PicnicMatModel::createBodyLayer);
    }
}
