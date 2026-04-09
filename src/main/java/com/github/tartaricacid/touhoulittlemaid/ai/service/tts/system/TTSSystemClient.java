package com.github.tartaricacid.touhoulittlemaid.ai.service.tts.system;

import com.github.tartaricacid.touhoulittlemaid.ai.manager.entity.TTSCallback;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSClient;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSConfig;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSSystemServices;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

public class TTSSystemClient implements TTSClient, TTSSystemServices {
    @Override
    public void play(String message, TTSConfig config, TTSCallback callback) {
        if (isClient()) {
            onHandle(message);
        }
    }

    @Environment(EnvType.CLIENT)
    private void onHandle(String message) {
        Minecraft mc = Minecraft.getInstance();
        mc.getNarrator().narrator.say(message, true);
    }
}
