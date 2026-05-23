package cn.sh1rocu.touhoulittlemaid.util.neoforge;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.NonNullListAccessor;
import net.minecraft.core.NonNullList;

import java.util.List;

public class NonNullListUtil {
    public static <E> NonNullList<E> copyOf(java.util.Collection<? extends E> entries) {
        return NonNullListAccessor.tlm$new(List.copyOf(entries), null);
    }
}
