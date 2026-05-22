package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public interface IItemRenderer {
    @Environment(EnvType.CLIENT)
    BlockEntityWithoutLevelRenderer getCustomRenderer();
}
