package com.github.tartaricacid.touhoulittlemaid.client.event;

import cn.sh1rocu.touhoulittlemaid.api.event.PlaySoundEvent;
import com.github.tartaricacid.touhoulittlemaid.client.sound.data.MaidSoundInstance;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MaidConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MaidSoundFreqEvent {
    public static void onPlaySoundEvent(PlaySoundEvent event) {
        if (event.getSound() instanceof MaidSoundInstance maidSoundInstance) {
            EntityMaid maid = maidSoundInstance.getMaid();
            if (maid == null) {
                event.setSound(null);
                return;
            }
            double soundFrequency = maid.getConfigManager().getSoundFreq();
            soundFrequency = soundFrequency * MaidConfig.GLOBAL_MAID_SOUND_FREQUENCY.get() / 100;
            if (soundFrequency < 1 && !maidSoundInstance.isTestSound()) {
                if (Math.random() > soundFrequency) {
                    event.setSound(null);
                }
            }
        }
    }
}