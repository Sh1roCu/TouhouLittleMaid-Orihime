package com.github.tartaricacid.touhoulittlemaid.debug.target;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.VisibleForDebug;

@VisibleForDebug
@Environment(EnvType.CLIENT)
public class DebugClientRenderEvent {
    //after block entities
    public static void onRender(LevelRenderContext context) {
        if (TouhouLittleMaid.DEBUG) {
            MultiBufferSource.BufferSource bufferSource = context.bufferSource();
            Minecraft.getInstance().debugRenderer.pathfindingRenderer.render(context.matrixStack(),
                    bufferSource,
                    context.camera().getPosition().x,
                    context.camera().getPosition().y,
                    context.camera().getPosition().z);
            bufferSource.endBatch();
        }
    }
}