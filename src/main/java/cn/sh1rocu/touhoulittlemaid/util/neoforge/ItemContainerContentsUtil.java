package cn.sh1rocu.touhoulittlemaid.util.neoforge;

import net.fabricmc.fabric.mixin.transfer.ItemContainerContentsAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.ItemContainerContents;

@SuppressWarnings("UnstableApiUsage")
public class ItemContainerContentsUtil {
    private ItemContainerContentsUtil() {

    }

    public static int getSlots(ItemContainerContents contents) {
        var containerAccessor = (ItemContainerContentsAccessor) (Object) contents;
        return containerAccessor == null ? 0 : containerAccessor.fabric_getItems().size();
    }

    public static ItemStack getStackInSlot(ItemContainerContents contents, int slot) {
        var containerAccessor = (ItemContainerContentsAccessor) (Object) contents;
        return containerAccessor == null ? ItemStack.EMPTY :
                (containerAccessor.fabric_getItems().get(slot)).map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
    }

    public static void validateSlotIndex(ItemContainerContents contents, int slot) {
        if (slot < 0 || slot >= getSlots(contents)) {
            throw new UnsupportedOperationException("Slot " + slot + " not in valid range - [0," + getSlots(contents) + ")");
        }
    }
}
