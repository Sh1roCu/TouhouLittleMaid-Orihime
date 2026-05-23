package cn.sh1rocu.touhoulittlemaid.util.transfer;

import net.minecraft.world.item.ItemStack;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public interface IItemHandler extends AutoSyncedComponent {
    String TAG_INVENTORY = "Inventory";

    int getSlots();

    ItemStack getStackInSlot(int slot);

    ItemStack insertItem(int slot, ItemStack stack, boolean simulate);

    ItemStack extractItem(int slot, int amount, boolean simulate);

    int getSlotLimit(int slot);

    boolean isItemValid(int slot, ItemStack stack);
}