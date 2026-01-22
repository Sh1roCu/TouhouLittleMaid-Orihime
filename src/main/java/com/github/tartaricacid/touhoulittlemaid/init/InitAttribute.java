package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public interface InitAttribute {
    /**
     * 女仆使用物品的速度倍率，数值越大，物品冷却时间越短
     */
    Attribute MAID_USE_ITEM_SPEED = newAttribute("maid_use_item_speed", 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
    /**
     * 女仆使用弩射击的速度倍率，数值越大，弩箭射击间隔越短
     */
    Attribute MAID_CROSSBOW_ATTACK_SPEED = newAttribute("maid_crossbow_attack_speed", 1, 0, Integer.MAX_VALUE);
    /**
     * 女仆使用枪械（TaCZ/卓越前线）射击的速度倍率，数值越大，射击间隔越短
     * <p>
     * 可能无法超越枪本身的最大射速
     */
    Attribute MAID_GUN_ATTACK_SPEED = newAttribute("maid_gun_attack_speed", 1, 0, Integer.MAX_VALUE);
    /**
     * 女仆单次射击（弓、弩）后的冷却时间，数值越大，射击后的冷却时间越长
     */
    Attribute MAID_SHOOT_COOLDOWN = newAttribute("maid_shoot_cooldown", 2, 0, Integer.MAX_VALUE);
    /**
     * 女仆射出一支三叉戟后的冷却时间，数值越大，射击后的冷却时间越长
     */
    Attribute MAID_TRIDENT_COOLDOWN = newAttribute("maid_trident_cooldown", 20, 0, Integer.MAX_VALUE);
    /**
     * 女仆拾取掉落物/经验球/P点的范围，数值越大，拾取范围越大
     */
    Attribute MAID_PICKUP_RANGE = newAttribute("maid_pickup_range", 0.5, 0, Double.MAX_VALUE);
    /**
     * 女仆单次举盾的持续时间（单位：tick）
     */
    Attribute MAID_PASSIVE_USE_SHIELD_TICK = newAttribute("maid_passive_use_shield_tick", 100, 0, Integer.MAX_VALUE);
    /**
     * 女仆的饱食度，目前本体暂时没有使用，主要给其他附属模组调用
     */
    Attribute MAID_HUNGER = newAttribute("maid_hunger", 20, 0, Integer.MAX_VALUE);

    private static Attribute newAttribute(String id, double defaultValue, double min, double max) {
        final String key = "attribute." + TouhouLittleMaid.MOD_ID + "." + id;
        return Registry.register(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), new RangedAttribute(key, defaultValue, min, max).setSyncable(true));
    }

    static void init() {

    }
}