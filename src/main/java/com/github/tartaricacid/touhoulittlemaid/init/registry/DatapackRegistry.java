package com.github.tartaricacid.touhoulittlemaid.init.registry;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.datapack.resources.BoardStateDataReloadListener;
import com.github.tartaricacid.touhoulittlemaid.datapack.resources.KaomojiDataReloadListener;
import com.github.tartaricacid.touhoulittlemaid.datapack.resources.SkillsDataReloadListener;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

public class DatapackRegistry {
    public static void onAddReloadListenerEvent() {
        var registry = ResourceLoader.get(PackType.SERVER_DATA);
        registry.registerReloadListener(Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "kaomoji"), new KaomojiDataReloadListener());
        registry.registerReloadListener(Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "board_state"), new BoardStateDataReloadListener());
        registry.registerReloadListener(Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "skill"), new SkillsDataReloadListener());
    }
}
