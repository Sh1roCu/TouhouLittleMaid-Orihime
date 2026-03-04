package com.github.tartaricacid.touhoulittlemaid.event;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public final class CopyEntityIdEvent {
    public static InteractionResult copyEntityId(Player player, Level world, InteractionHand hand, Entity target, @Nullable HitResult hitResult) {
        if (player.getItemInHand(hand).is(InitItems.ENTITY_ID_COPY)) {
            if (player.level.isClientSide && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                copyEntityId(player, target);
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    @Environment(EnvType.CLIENT)
    private static void copyEntityId(Player player, Entity target) {
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
        if (key.equals(BuiltInRegistries.ENTITY_TYPE.getDefaultKey())) {
            return;
        }
        Minecraft.getInstance().keyboardHandler.setClipboard(key.toString());
        player.sendSystemMessage(Component.translatable("message.touhou_little_maid.entity_id_copy.copy", key.toString()));
    }
}
