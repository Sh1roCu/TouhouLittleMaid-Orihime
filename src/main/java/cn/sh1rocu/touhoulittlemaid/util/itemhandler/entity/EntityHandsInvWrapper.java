package cn.sh1rocu.touhoulittlemaid.util.itemhandler.entity;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class EntityHandsInvWrapper extends EntityEquipmentInvWrapper {
    public EntityHandsInvWrapper(LivingEntity entity) {
        super(entity, EquipmentSlot.Type.HAND);
    }
}