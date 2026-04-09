package com.github.tartaricacid.touhoulittlemaid.init.registry;

import com.github.tartaricacid.touhoulittlemaid.datapack.resources.BoardStateDataReloadListener;
import com.github.tartaricacid.touhoulittlemaid.datapack.resources.KaomojiDataReloadListener;
import com.github.tartaricacid.touhoulittlemaid.datapack.resources.SkillsDataReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class DatapackRegistry {
    public static void onAddReloadListenerEvent() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new KaomojiDataReloadListener());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new BoardStateDataReloadListener());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SkillsDataReloadListener());
    }
}
