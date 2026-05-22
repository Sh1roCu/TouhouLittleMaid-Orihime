package com.github.tartaricacid.touhoulittlemaid.api.event;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityPowerPoint;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

public abstract class MaidPickupEvent extends CancellableEvent {
    private final EntityMaid maid;
    private final boolean simulate;
    private boolean canPickup = false;

    public static final Event<ItemResultPre.Callback> ITEM_RESULT_PRE = EventFactory.createWithPhases(ItemResultPre.Callback.class, callbacks -> event -> {
        for (ItemResultPre.Callback callback : callbacks) {
            callback.onItemResultPre(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);
    public static final Event<ItemResultPost.Callback> ITEM_RESULT_POST = EventFactory.createWithPhases(ItemResultPost.Callback.class, callbacks -> event -> {
        for (ItemResultPost.Callback callback : callbacks) {
            callback.onItemResultPost(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);
    public static final Event<ExperienceResult.Callback> EXPERIENCE_RESULT = EventFactory.createWithPhases(ExperienceResult.Callback.class, callbacks -> event -> {
        for (ExperienceResult.Callback callback : callbacks) {
            callback.onExperienceResult(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);
    public static final Event<ArrowResult.Callback> ARROW_RESULT = EventFactory.createWithPhases(ArrowResult.Callback.class, callbacks -> event -> {
        for (ArrowResult.Callback callback : callbacks) {
            callback.onArrowResult(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);
    public static final Event<PowerPointResult.Callback> POWERPOINT_RESULT = EventFactory.createWithPhases(PowerPointResult.Callback.class, callbacks -> event -> {
        for (PowerPointResult.Callback callback : callbacks) {
            callback.onPowerPointResult(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public MaidPickupEvent(EntityMaid maid, boolean simulate) {
        this.maid = maid;
        this.simulate = simulate;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public boolean isSimulate() {
        return simulate;
    }

    public boolean isCanPickup() {
        return canPickup;
    }

    public void setCanPickup(boolean canPickup) {
        this.canPickup = canPickup;
    }

    public static class ItemResultPre extends MaidPickupEvent {
        private final ItemEntity entityItem;

        public ItemResultPre(EntityMaid maid, ItemEntity entityItem, boolean simulate) {
            super(maid, simulate);
            this.entityItem = entityItem;
        }

        public ItemEntity getEntityItem() {
            return entityItem;
        }

        public interface Callback {
            void onItemResultPre(ItemResultPre event);
        }
    }

    public static class ItemResultPost extends MaidPickupEvent {
        /**
         * 女仆捡起的物品，复制的对象
         */
        private final ItemStack pickupItem;

        public ItemResultPost(EntityMaid maid, ItemStack pickupItem) {
            super(maid, false);
            this.pickupItem = pickupItem;
        }

        public ItemStack getPickupItem() {
            return pickupItem;
        }

        public interface Callback {
            void onItemResultPost(ItemResultPost event);
        }
    }

    public static class ExperienceResult extends MaidPickupEvent {
        private final ExperienceOrb experienceOrb;

        public ExperienceResult(EntityMaid maid, ExperienceOrb experienceOrb, boolean simulate) {
            super(maid, simulate);
            this.experienceOrb = experienceOrb;
        }

        public ExperienceOrb getExperienceOrb() {
            return experienceOrb;
        }

        public interface Callback {
            void onExperienceResult(ExperienceResult event);
        }
    }

    public static class ArrowResult extends MaidPickupEvent {
        private final AbstractArrow arrow;

        public ArrowResult(EntityMaid maid, AbstractArrow arrow, boolean simulate) {
            super(maid, simulate);
            this.arrow = arrow;
        }

        public AbstractArrow getArrow() {
            return arrow;
        }

        public interface Callback {
            void onArrowResult(ArrowResult event);
        }
    }

    public static class PowerPointResult extends MaidPickupEvent {
        private final EntityPowerPoint powerPoint;

        public PowerPointResult(EntityMaid maid, EntityPowerPoint powerPoint, boolean simulate) {
            super(maid, simulate);
            this.powerPoint = powerPoint;
        }

        public EntityPowerPoint getPowerPoint() {
            return powerPoint;
        }


        public interface Callback {
            void onPowerPointResult(PowerPointResult event);
        }
    }
}