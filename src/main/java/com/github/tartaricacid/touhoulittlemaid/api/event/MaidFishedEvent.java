package com.github.tartaricacid.touhoulittlemaid.api.event;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.MaidFishingHook;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnegative;
import java.util.List;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

public class MaidFishedEvent extends CancellableEvent {
    private final EntityMaid maid;
    private final NonNullList<ItemStack> drops = NonNullList.create();
    private final MaidFishingHook hook;
    private int rodDamage;

    public MaidFishedEvent(List<ItemStack> drops, int rodDamage, MaidFishingHook hook) {
        this.maid = hook.getMaidOwner();
        this.drops.addAll(drops);
        this.rodDamage = rodDamage;
        this.hook = hook;
    }

    public void damageRodBy(@Nonnegative int rodDamage) {
        this.rodDamage = rodDamage;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public NonNullList<ItemStack> getDrops() {
        return drops;
    }

    public MaidFishingHook getHook() {
        return hook;
    }

    public int getRodDamage() {
        return rodDamage;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void post(MaidFishedEvent event);
    }
}
