package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EntityMountEvent extends CancellableEvent {
    private final Entity entityMounting;
    private final Entity entityBeingMounted;
    private final Level level;
    private final boolean isMounting;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (final Callback callback : callbacks)
            callback.post(event);
    });

    public EntityMountEvent(Entity entityMounting, Entity entityBeingMounted, Level level, boolean isMounting) {
        this.entityMounting = entityMounting;
        this.entityBeingMounted = entityBeingMounted;
        this.level = level;
        this.isMounting = isMounting;
    }

    public boolean isMounting() {
        return isMounting;
    }

    public boolean isDismounting() {
        return !isMounting;
    }

    public Entity getEntityMounting() {
        return entityMounting;
    }

    public Entity getEntityBeingMounted() {
        return entityBeingMounted;
    }

    public Level getLevel() {
        return level;
    }

    public interface Callback {
        void post(EntityMountEvent event);
    }
}