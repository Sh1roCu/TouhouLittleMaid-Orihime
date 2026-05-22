package cn.sh1rocu.touhoulittlemaid.api.event;

import com.mojang.blaze3d.audio.Channel;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;

public class PlaySoundSourceEvent {
    private final SoundEngine engine;
    private final String name;
    private final SoundInstance sound;
    private final Channel channel;

    public PlaySoundSourceEvent(SoundEngine engine, SoundInstance sound, Channel channel) {
        this.engine = engine;
        this.name = sound.getLocation().getPath();
        this.sound = sound;
        this.channel = channel;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public SoundEngine getEngine() {
        return engine;
    }

    public SoundInstance getSound() {
        return sound;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getName() {
        return name;
    }

    public interface Callback {
        void post(PlaySoundSourceEvent event);
    }
}
