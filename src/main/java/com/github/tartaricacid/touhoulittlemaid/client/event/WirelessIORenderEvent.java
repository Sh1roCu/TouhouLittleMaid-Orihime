package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.item.ItemWirelessIO;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class WirelessIORenderEvent {
    // AfterOpaqueBlocks
    public static void onRender(LevelRenderContext context) {
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
        Vec3 position = context.levelState().cameraRenderState.pos.reverse();
        AABB aabb = new AABB(pos).move(position);
        Gizmos.cuboid(aabb, GizmoStyle.fill(ARGB.colorFromFloat(1.0F, 1.0F, 0, 0)));
    }
}
