/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.neoforge;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.TagValueOutputAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.TagValueOutput;


public class TagValueOutputUtil {
    public static void store(TagValueOutput output, CompoundTag tag) {
        for (var entry : tag.entrySet()) {
            ((TagValueOutputAccessor) output).tlm$getOutput().put(entry.getKey(), entry.getValue());
        }
    }
}
