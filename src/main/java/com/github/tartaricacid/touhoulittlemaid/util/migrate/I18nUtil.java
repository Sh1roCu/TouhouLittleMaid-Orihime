package com.github.tartaricacid.touhoulittlemaid.util.migrate;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.locale.Language;

/**
 * 方便 26.1 -> 26.2 迁移的类
 */
public final class I18nUtil {
    private I18nUtil() {
    }

    public static boolean exists(String key) {
        return Language.getInstance().has(key);
    }

    public static String get(String key, Object... args) {
        return I18n.get(key, args);
    }

    public static String getOrDefault(String key, String defaultValue) {
        return exists(key) ? get(key) : defaultValue;
    }
}
