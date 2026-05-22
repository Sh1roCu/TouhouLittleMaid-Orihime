package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public abstract class PlayerTickEvent {
    private final Player player;

    public PlayerTickEvent(Player player) {
        this.player = player;
    }

    public static final Event<Pre.Callback> START = EventFactory.createArrayBacked(Pre.Callback.class, callbacks -> event -> {
        for (final Pre.Callback callback : callbacks)
            callback.onStart(event);
    });
    public static final Event<Post.Callback> END = EventFactory.createArrayBacked(Post.Callback.class, callbacks -> event -> {
        for (final Post.Callback callback : callbacks)
            callback.onEnd(event);
    });

    public Player getEntity() {
        return player;
    }

    public static class Pre extends PlayerTickEvent {
        public Pre(Player player) {
            super(player);
        }

        public interface Callback {
            void onStart(Pre event);
        }
    }

    public static class Post extends PlayerTickEvent {
        public Post(Player player) {
            super(player);
        }

        public interface Callback {
            void onEnd(Post event);
        }
    }
}