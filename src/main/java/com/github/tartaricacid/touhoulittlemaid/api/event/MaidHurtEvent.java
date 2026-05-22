package com.github.tartaricacid.touhoulittlemaid.api.event;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

/**
 * 女仆受到攻击时的事件，此时已经进行了敌我类型判断，但是还没有进行护甲、药水效果吸收后的伤害计算，等同于 LivingHurtEvent
 * <p>
 * 各个伤害执行顺序：
 * MaidAttackEvent -> MaidHurtEvent -> MaidDamageEvent -> 最终受到伤害
 * <p>
 * 此事件是可取消的，取消后女仆不会受到此次攻击的伤害
 */
public class MaidHurtEvent extends CancellableEvent {
    private final EntityMaid maid;
    private final DamageSource source;
    private float amount;

    public MaidHurtEvent(EntityMaid maid, DamageSource source, float amount) {
        this.maid = maid;
        this.source = source;
        this.amount = amount;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public DamageSource getSource() {
        return source;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void post(MaidHurtEvent event);
    }
}
