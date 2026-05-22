package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.item.ItemWirelessIO;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public final class WirelessIORenderEvent {
    // after block entities
    public static void onRender(LevelRenderContext context) {
        //if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        ItemStack mainStack = mc.player.getMainHandItem();
        if (mainStack.getItem() != InitItems.WIRELESS_IO) {
            return;
        }
        BlockPos pos = ItemWirelessIO.getBindingPos(mainStack);
        if (pos == null) {
            return;
        }
        Vec3 position = context.gameRenderer().getMainCamera().position().reverse();
        AABB aabb = new AABB(pos).move(position);
        VertexConsumer buffer = mc.renderBuffers().bufferSource().getBuffer(RenderTypes.LINES);
        LevelRenderer.renderLineBox(context.matrixStack(), buffer, aabb, 1.0F, 0, 0, 1.0F);
        //}
    }
}
