package cn.sh1rocu.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.api.event.PlaySoundEvent;
import cn.sh1rocu.touhoulittlemaid.api.mixin.ChannelAccessHandleInjection;
import cn.sh1rocu.touhoulittlemaid.util.kilt.SoundConsumerStorage;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

// From Kilt https://github.com/KiltMC/Kilt/blob/version/1.21.1/src/main/java/xyz/bluspring/kilt/forgeinjects/client/sounds/SoundEngineInject.java
@Environment(EnvType.CLIENT)
@Mixin(SoundEngine.class)
public abstract class SoundEngineMixin {
    @WrapOperation(
            method = "play",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/sounds/SoundInstance;canPlaySound()Z"
            )
    )
    private boolean tlm$playSoundEvent(SoundInstance instance, Operation<Boolean> original, @Local(argsOnly = true) LocalRef<SoundInstance> refInstance) {
        PlaySoundEvent event = new PlaySoundEvent((SoundEngine) (Object) this, instance);
        PlaySoundEvent.CALLBACK.invoker().post(event);
        refInstance.set(event.getSound());
        return refInstance.get() != null && original.call(refInstance.get());
    }

    @Inject(method = "play", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/ChannelAccess$ChannelHandle;execute(Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
    private void tlm$prepareChannelInfo(SoundInstance soundInstance, CallbackInfo ci, @Local ChannelAccess.ChannelHandle channelHandle, @Local Sound sound) {
        var injection = ((ChannelAccessHandleInjection) channelHandle);

        if (sound.shouldStream())
            injection.tlm$setPool(Library.Pool.STREAMING);
        else
            injection.tlm$setPool(Library.Pool.STATIC);

        injection.tlm$setSoundInstance(soundInstance);
        injection.tlm$setSoundEngine((SoundEngine) (Object) this);
    }

    @ModifyArg(method = "method_19757", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/ChannelAccess$ChannelHandle;execute(Ljava/util/function/Consumer;)V"))
    private static Consumer<Channel> tlm$storeSourceConsumer(Consumer<Channel> consumer) {
        SoundConsumerStorage.soundConsumerChannels.add(consumer);
        return consumer;
    }

    // 暂时用不到
/*    @ModifyArg(method = "method_19758", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/ChannelAccess$ChannelHandle;execute(Ljava/util/function/Consumer;)V"))
    private static Consumer<Channel> kilt$storeStreamConsumer(Consumer<Channel> consumer) {
        SoundConsumerStorage.soundConsumerChannels.add(consumer);
        return consumer;
    }*/
}
