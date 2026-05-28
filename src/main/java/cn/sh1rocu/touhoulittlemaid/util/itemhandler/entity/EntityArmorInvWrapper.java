package cn.sh1rocu.touhoulittlemaid.util.itemhandler.entity;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class EntityArmorInvWrapper extends EntityEquipmentInvWrapper {
    public EntityArmorInvWrapper(final LivingEntity entity) {
        super(entity, EquipmentSlot.Type.ARMOR);
    }
}