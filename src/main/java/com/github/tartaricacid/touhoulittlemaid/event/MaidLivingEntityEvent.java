package com.github.tartaricacid.touhoulittlemaid.event;

import cn.sh1rocu.touhoulittlemaid.api.event.LivingDamageEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.LivingHurtEvent;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidDamageEvent;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidHurtEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.damagesource.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * 监听原版的 LivingEntity 相关事件，然后将女仆的单独拎出来触发成专门的事件
 * <p>
 * 相当于特化版本的 LivingEntityEvent，方便其他开发者使用
 */
public class MaidLivingEntityEvent {
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof EntityMaid maid) {
            MaidHurtEvent maidHurtEvent = new MaidHurtEvent(maid, event.getSource(), event.getAmount());
            MaidHurtEvent.CALLBACK.invoker().post(maidHurtEvent);
            float damageAmount = maidHurtEvent.isCanceled() ? 0 : maidHurtEvent.getAmount();
            event.setAmount(damageAmount);
        }
    }

    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof EntityMaid maid) {
            DamageSource source = event.getSource();
            MutableFloat damage = new MutableFloat(event.getAmount());

            boolean baubleCancel = maid.getMaidBauble().fireEvent((b, s) -> b.onInjured(maid, s, source, damage));
            float finalDamage = damage.getValue();
            // 如果饰品取消了事件，那么也不触发后续内容了
            if (baubleCancel || finalDamage <= 0) {
                event.setCanceled(true);
                return;
            }

            MaidDamageEvent maidDamageEvent = new MaidDamageEvent(maid, event.getSource(), event.getAmount());
            MaidDamageEvent.CALLBACK.invoker().post(maidDamageEvent);
            float damageAfterAbsorption = maidDamageEvent.isCanceled() ? 0 : maidDamageEvent.getAmount();
            event.setAmount(damageAfterAbsorption);
        }
    }
}