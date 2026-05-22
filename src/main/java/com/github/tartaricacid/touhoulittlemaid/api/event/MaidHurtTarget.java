package com.github.tartaricacid.touhoulittlemaid.api.event;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

/**
 * 女仆近战伤害其他实体时触发此事件
 */
@ApiStatus.AvailableSince("1.4.0")
public abstract class MaidHurtTarget extends CancellableEvent {
    private final EntityMaid maid;
    private final Entity target;

    public static final Event<PreCallback> PRE = EventFactory.createArrayBacked(PreCallback.class, (callbacks) -> event -> {
        for (PreCallback callback : callbacks) {
            callback.onPre(event);
        }
    });
    public static final Event<PostCallback> POST = EventFactory.createArrayBacked(PostCallback.class, (callbacks) -> event -> {
        for (PostCallback callback : callbacks) {
            callback.onPost(event);
        }
    });

    public MaidHurtTarget(EntityMaid maid, Entity target) {
        this.maid = maid;
        this.target = target;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public Entity getTarget() {
        return target;
    }

    public static class Pre extends MaidHurtTarget {
        public Pre(EntityMaid maid, Entity target) {
            super(maid, target);
        }
    }

    public static class Post extends MaidHurtTarget {
        private final boolean isHurt;

        public Post(EntityMaid maid, Entity target, boolean isHurt) {
            super(maid, target);
            this.isHurt = isHurt;
        }

        public boolean isHurt() {
            return isHurt;
        }
    }

    public interface PreCallback {
        void onPre(Pre event);
    }

    public interface PostCallback {
        void onPost(Post event);
    }
}