package cn.sh1rocu.touhoulittlemaid.util.neoforge;

import net.minecraft.world.entity.LivingEntity;

public class ClientHooks {
    public static boolean isNameplateInRenderDistance(LivingEntity entity, double squareDistance) {
        // double value = entity.getAttributeValue(NeoForgeMod.NAMETAG_DISTANCE);
        double value = 32D;
        return !(squareDistance > value * value);
    }
}
