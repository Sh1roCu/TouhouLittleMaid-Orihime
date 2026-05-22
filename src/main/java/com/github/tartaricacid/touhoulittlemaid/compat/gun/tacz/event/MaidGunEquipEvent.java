package com.github.tartaricacid.touhoulittlemaid.compat.gun.tacz.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidEquipEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.IGun;

public class MaidGunEquipEvent {
    public void onMaidEquip(MaidEquipEvent event) {
        EntityMaid maid = event.getMaid();
        if (IGun.mainHandHoldGun(maid)) {
            IGunOperator operator = IGunOperator.fromLivingEntity(maid);
            operator.draw(maid::getMainHandItem);
        }
    }
}
