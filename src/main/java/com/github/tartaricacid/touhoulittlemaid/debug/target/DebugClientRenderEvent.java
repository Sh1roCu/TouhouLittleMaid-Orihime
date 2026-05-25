package com.github.tartaricacid.touhoulittlemaid.debug.target;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.util.VisibleForDebug;

@VisibleForDebug
public class DebugClientRenderEvent {
    //AfterOpaqueBlocks
    public static void onRender(LevelRenderContext context) {
        if (TouhouLittleMaid.DEBUG) {
            // TODO
//            MultiBufferSource.BufferSource bufferSource = context.bufferSource();
//            Minecraft.getInstance().debugRenderer.pathfindingRenderer.render(context.poseStack(),
//                    bufferSource,
//                    context.levelState().cameraRenderState.pos.x,
//                    context.levelState().cameraRenderState.pos.y,
//                    context.levelState().cameraRenderState.pos.z);
//            bufferSource.endBatch();
        }
    }
}
