package com.github.tartaricacid.touhoulittlemaid.client.event;

import cn.sh1rocu.touhoulittlemaid.api.event.PlaySoundSourceEvent;
import com.github.tartaricacid.touhoulittlemaid.api.client.sound.ICustomSoundBuffer;
import com.mojang.blaze3d.audio.SoundBuffer;

public class PlayMaidSoundEvent {
    public static void onPlaySoundSource(PlaySoundSourceEvent event) {
        if (event.getSound() instanceof ICustomSoundBuffer custom) {
            SoundBuffer soundBuffer = custom.getSoundBuffer();
            if (soundBuffer != null) {
                event.getChannel().attachStaticBuffer(soundBuffer);
                event.getChannel().play();
            }
        }
    }
}
