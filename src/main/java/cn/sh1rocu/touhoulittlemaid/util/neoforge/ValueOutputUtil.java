/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package cn.sh1rocu.touhoulittlemaid.util.neoforge;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.TagValueOutputAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueOutput;


public class ValueOutputUtil {
    public static void store(ValueOutput output, CompoundTag tag) {
        for (var entry : tag.entrySet()) {
            output.store(entry.getKey(), ExtraCodecs.NBT, entry.getValue());
        }
    }

    public static void store(TagValueOutput output, CompoundTag tag) {
        for (var entry : tag.entrySet()) {
            ((TagValueOutputAccessor) output).tlm$getOutput().put(entry.getKey(), entry.getValue());
        }
    }

    public static void putChild(ValueOutput output, String key, ValueIOSerializable child) {
        child.serialize(output.child(key));
    }
}
