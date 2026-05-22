package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.jetbrains.annotations.Nullable;

public class PlaySoundEvent {
    private final SoundEngine engine;
    private final String name;
    private final SoundInstance originalSound;
    @Nullable
    private SoundInstance sound;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public PlaySoundEvent(SoundEngine manager, SoundInstance sound) {
        this.engine = manager;
        this.originalSound = sound;
        this.name = sound.getLocation().getPath();
        this.setSound(sound);
    }

    public SoundEngine getEngine() {
        return engine;
    }

    public String getName() {
        return name;
    }

    public SoundInstance getOriginalSound() {
        return originalSound;
    }

    @Nullable
    public SoundInstance getSound() {
        return sound;
    }

    public void setSound(@Nullable SoundInstance newSound) {
        this.sound = newSound;
    }

    public interface Callback {
        void post(PlaySoundEvent event);
    }
}
