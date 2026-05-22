package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class FarmlandTrampleEvent extends CancellableEvent {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState state;
    private final Entity entity;
    private final float fallDistance;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks)
            callback.post(event);
    });

    public FarmlandTrampleEvent(Level level, BlockPos pos, BlockState state, float fallDistance, Entity entity) {
        this.level = level;
        this.pos = pos;
        this.state = state;
        this.entity = entity;
        this.fallDistance = fallDistance;
    }

    public LevelAccessor getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getFallDistance() {
        return fallDistance;
    }

    public interface Callback {
        void post(FarmlandTrampleEvent event);
    }
}
