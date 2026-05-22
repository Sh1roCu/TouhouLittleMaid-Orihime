package cn.sh1rocu.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.api.event.PlaySoundSourceEvent;
import cn.sh1rocu.touhoulittlemaid.api.mixin.ChannelAccessHandleInjection;
import cn.sh1rocu.touhoulittlemaid.util.kilt.SoundConsumerStorage;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

// From kilt
@Mixin(ChannelAccess.ChannelHandle.class)
public abstract class ChannelAccessHandleMixin implements ChannelAccessHandleInjection {
    @Shadow
    @Nullable Channel channel;
    @Unique
    private Library.Pool tlm$pool;
    @Unique
    private SoundEngine tlm$soundEngine;
    @Unique
    private SoundInstance tlm$soundInstance;

    @Override
    public void tlm$setPool(Library.Pool pool) {
        this.tlm$pool = pool;
    }

    @Override
    public void tlm$setSoundEngine(SoundEngine engine) {
        this.tlm$soundEngine = engine;
    }

    @Override
    public void tlm$setSoundInstance(SoundInstance instance) {
        this.tlm$soundInstance = instance;
    }

    @Inject(method = "method_19737", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private void tlm$callPlaySoundEvents(Consumer<Channel> consumer, CallbackInfo ci) {
        if (this.channel != null && tlm$soundEngine != null && tlm$soundInstance != null && SoundConsumerStorage.soundConsumerChannels.remove(consumer)) {
            if (tlm$pool == Library.Pool.STATIC) {
                PlaySoundSourceEvent.CALLBACK.invoker().post(new PlaySoundSourceEvent(tlm$soundEngine, tlm$soundInstance, this.channel));
            }
            // 暂时用不到
            /* else if (tlm$pool == Library.Pool.STREAMING) {
                PlayStreamingSourceEvent.CALLBACK.invoker().post(new PlayStreamingSourceEvent(tlm$soundEngine, tlm$soundInstance, this.channel));
            }*/
        }
    }
}