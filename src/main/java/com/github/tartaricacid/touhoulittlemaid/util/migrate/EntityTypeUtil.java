package com.github.tartaricacid.touhoulittlemaid.util.migrate;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;

import java.util.Optional;

/**
 * 方便 26.1 -> 26.2 迁移的工具类
 */
public final class EntityTypeUtil {
    private EntityTypeUtil() {
    }

    public static Optional<EntityType<?>> byString(String id) {
        Identifier identifier = Identifier.tryParse(id);
        return BuiltInRegistries.ENTITY_TYPE.getOptional(identifier);
    }

    public static EntityType<?> wolf() {
        return EntityTypes.WOLF;
    }

    public static EntityType<?> cat() {
        return EntityTypes.CAT;
    }

    public static EntityType<?> parrot() {
        return EntityTypes.PARROT;
    }

    public static EntityType<?> player() {
        return EntityTypes.PLAYER;
    }

    public static EntityType<?> armorStand() {
        return EntityTypes.ARMOR_STAND;
    }

    public static EntityType<?> item() {
        return EntityTypes.ITEM;
    }

    public static EntityType<?> lightningBolt() {
        return EntityTypes.LIGHTNING_BOLT;
    }

    public static EntityType<?> ironGolem() {
        return EntityTypes.IRON_GOLEM;
    }

    public static EntityType<?> creeper() {
        return EntityTypes.CREEPER;
    }

    public static EntityType<?> zombie() {
        return EntityTypes.ZOMBIE;
    }

    public static EntityType<?> allay() {
        return EntityTypes.ALLAY;
    }
}
