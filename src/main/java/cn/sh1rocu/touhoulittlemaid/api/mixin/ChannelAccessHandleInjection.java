package cn.sh1rocu.touhoulittlemaid.api.mixin;

import com.mojang.blaze3d.audio.Library;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;

// From Kilt
public interface ChannelAccessHandleInjection {
    void tlm$setPool(Library.Pool pool);

    void tlm$setSoundInstance(SoundInstance instance);

    void tlm$setSoundEngine(SoundEngine engine);
}