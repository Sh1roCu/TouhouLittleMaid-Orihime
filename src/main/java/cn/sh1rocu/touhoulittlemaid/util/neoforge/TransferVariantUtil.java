package cn.sh1rocu.touhoulittlemaid.util.neoforge;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.impl.transfer.item.ItemVariantImpl;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class TransferVariantUtil {
    private TransferVariantUtil() {

    }

    @SuppressWarnings("UnstableApiUsage")
    public static <D> ItemVariant with(ItemVariant variant, DataComponentType<D> type, @Nullable D data) {
        if (variant.isBlank()) return ItemVariant.blank();
        if (Objects.equals(variant.get(type), data)) return variant;

        ItemStack stack = variant instanceof ItemVariantImpl impl ? impl.getCachedStack() : variant.toStack();
        stack.set(type, data);
        return ItemVariant.of(stack);
    }
}
