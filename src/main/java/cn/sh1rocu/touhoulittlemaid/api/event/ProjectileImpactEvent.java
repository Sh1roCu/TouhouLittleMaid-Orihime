package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class ProjectileImpactEvent extends CancellableEvent {
    private final HitResult ray;
    private final Projectile projectile;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (final Callback callback : callbacks)
            callback.post(event);
    });

    public ProjectileImpactEvent(Projectile projectile, HitResult ray) {
        this.ray = ray;
        this.projectile = projectile;
    }

    public HitResult getRayTraceResult() {
        return ray;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public interface Callback {
        void post(ProjectileImpactEvent event);
    }
}