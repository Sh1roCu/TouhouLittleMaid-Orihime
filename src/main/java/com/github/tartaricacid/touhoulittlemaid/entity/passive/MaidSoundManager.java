package com.github.tartaricacid.touhoulittlemaid.entity.passive;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidPlaySoundEvent;
import com.github.tartaricacid.touhoulittlemaid.init.InitSounds;
import com.github.tartaricacid.touhoulittlemaid.network.NetworkHandler;
import com.github.tartaricacid.touhoulittlemaid.network.message.PlayMaidSoundPackage;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

@MaidManagerDef(alias = "soundManager", exposeView = true)
public class MaidSoundManager {
    private final EntityMaid maid;
    private int playerHurtSoundCount = 120;
    private int pickupSoundCount = 5;

    public MaidSoundManager(EntityMaid maid) {
        this.maid = maid;
    }

    public void tryPlayMaidPickupSound() {
        if (mayPlaySound()) {
            pickupSoundCount--;
            if (pickupSoundCount == 0) {
                this.playSound(InitSounds.MAID_ITEM_GET, 1, 1);
                pickupSoundCount = 5;
            }
        }
    }

    public boolean mayPlaySound() {
        var event = new MaidPlaySoundEvent(maid);
        MaidPlaySoundEvent.CALLBACK.invoker().post(event);
        return !event.isCanceled();
    }

    void tick() {
        if (playerHurtSoundCount > 0) {
            playerHurtSoundCount--;
        }
    }

    boolean playSound(SoundEvent soundEvent, float volume, float pitch) {
        if (soundEvent.location().getPath().startsWith("maid") && !maid.level.isClientSide()) {
            NetworkHandler.sendToNearby(maid, new PlayMaidSoundPackage(soundEvent.location(), maid.getSoundPackId(), maid.getId()), 16);
            return true;
        }
        return false;
    }

    @Nullable
    SoundEvent getAmbientSound() {
        if (!mayPlaySound()) {
            return null;
        }
        return maid.getTask().getAmbientSound(maid);
    }

    @Nullable
    SoundEvent getHurtSound(DamageSource damageSourceIn) {
        if (!mayPlaySound()) {
            return null;
        }
        if (damageSourceIn.is(DamageTypeTags.IS_FIRE)) {
            return InitSounds.MAID_HURT_FIRE;
        } else if (damageSourceIn.getEntity() instanceof Player) {
            if (playerHurtSoundCount == 0) {
                playerHurtSoundCount = 120;
                return InitSounds.MAID_PLAYER;
            } else {
                return null;
            }
        } else {
            return InitSounds.MAID_HURT;
        }
    }

    @Nullable
    SoundEvent getDeathSound() {
        if (!mayPlaySound()) {
            return null;
        }
        return InitSounds.MAID_DEATH;
    }

    interface View {
        MaidSoundManager getSoundManager();

        default void tryPlayMaidPickupSound() {
            this.getSoundManager().tryPlayMaidPickupSound();
        }
    }
}
