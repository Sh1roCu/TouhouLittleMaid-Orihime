package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class ProjectileImpactEvent extends CancellableEvent {
    private final HitResult ray;
    private final Projectile projectile;
    private ImpactResult result;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (final Callback callback : callbacks)
            callback.post(event);
    });

    public ProjectileImpactEvent(Projectile projectile, HitResult ray) {
        this.ray = ray;
        this.projectile = projectile;
        this.result = ImpactResult.DEFAULT;
    }

    public HitResult getRayTraceResult() {
        return ray;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public ImpactResult getResult() {
        return result;
    }

    public interface Callback {
        void post(ProjectileImpactEvent event);
    }

    public enum ImpactResult {
        DEFAULT,
        SKIP_ENTITY,
        STOP_AT_CURRENT,
        STOP_AT_CURRENT_NO_DAMAGE
    }
}