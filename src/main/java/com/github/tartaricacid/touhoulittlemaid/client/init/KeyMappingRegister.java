package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.client.input.DismountBroomKey;
import com.github.tartaricacid.touhoulittlemaid.client.input.STTChatKey;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class KeyMappingRegister {
    public static final KeyMapping.Category MAID_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "main")
    );

    public static void onRegisterKeyMappings() {
        KeyMappingHelper.registerKeyMapping(STTChatKey.STT_CHAT_KEY);
        KeyMappingHelper.registerKeyMapping(DismountBroomKey.DISMOUNT_KEY);
    }
}
