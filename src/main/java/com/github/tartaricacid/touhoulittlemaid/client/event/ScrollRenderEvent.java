package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.item.ItemFoxScroll;
import com.github.tartaricacid.touhoulittlemaid.item.ItemServantBell;
import com.github.tartaricacid.touhoulittlemaid.util.RenderHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ScrollRenderEvent {
    // after particles
    // Fabric用AFTER_TRANSLUCENT（？ ，懒得mixin+event了（
    public static void onRenderWorldLastEvent(LevelRenderContext context) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (/*RenderLevelStageEvent.Stage.AFTER_PARTICLES.equals(event.getStage()) &&*/ player != null) {
            Optional<ItemFoxScroll.TrackInfo> trackInfo = getInfo(player, player.getMainHandItem());
            if (trackInfo.isEmpty()) {
                return;
            }
            ItemFoxScroll.TrackInfo info = trackInfo.get();
            String dimension = info.dimension();
            Vec3 trackVec = new Vec3(info.position().getX(), info.position().getY(), info.position().getZ());
            if (!dimension.equals(player.level.dimension().identifier().toString())) {
                return;
            }
            Vec3 playerVec = player.position();
            double actualDistance = playerVec.distanceTo(trackVec);
            if (actualDistance < 5) {
                return;
            }
            double viewDistance = actualDistance;
            double maxRenderDistance = mc.options.renderDistance().get() * 16;
            if (actualDistance > maxRenderDistance) {
                Vec3 delta = trackVec.subtract(playerVec).normalize();
                trackVec = playerVec.add(delta.x * maxRenderDistance, delta.y * maxRenderDistance, delta.z * maxRenderDistance);
                viewDistance = maxRenderDistance;
            }
            float scale = 0.02f * (((float) viewDistance + 4.0f) / 3.0f);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderHelper.renderFloatingText(context.poseStack(), Math.round(actualDistance) + " m", trackVec, 0xff8800, scale, -17);
            RenderHelper.renderFloatingText(context.poseStack(), "▼", trackVec, 0xff0000, scale * 1.2f, -5);
        }
    }

    private static Optional<ItemFoxScroll.TrackInfo> getInfo(Player player, ItemStack stack) {
        if (stack.getItem() instanceof ItemFoxScroll) {
            var trackInfo = ItemFoxScroll.getTrackInfo(player.getMainHandItem());
            return Optional.ofNullable(trackInfo);
        }
        if (stack.is(InitItems.SERVANT_BELL)) {
            var maidShow = ItemServantBell.getMaidShow(stack);
            return Optional.ofNullable(maidShow);
        }
        return Optional.empty();
    }
}
