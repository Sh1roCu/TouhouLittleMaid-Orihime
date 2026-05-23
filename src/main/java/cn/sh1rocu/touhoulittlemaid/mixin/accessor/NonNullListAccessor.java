package cn.sh1rocu.touhoulittlemaid.mixin.accessor;

import net.minecraft.core.NonNullList;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(NonNullList.class)
public interface NonNullListAccessor {
    @Invoker("<init>")
    static <E> NonNullList<E> tlm$new(final List<E> list, final @Nullable E defaultValue) {
        throw new UnsupportedOperationException();
    }
}
