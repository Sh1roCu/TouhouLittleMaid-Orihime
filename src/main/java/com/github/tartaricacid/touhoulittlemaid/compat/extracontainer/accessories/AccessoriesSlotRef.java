package com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.accessories;

import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.ContainerRef;
import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.ExtraContainerManager;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public abstract class AccessoriesSlotRef implements ContainerRef {
    public final String slotType;
    public final int slotIndex;
    public final int priority;

    protected AccessoriesSlotRef(String slotType, int slotIndex) {
        this.slotType = slotType;
        this.slotIndex = slotIndex;
        this.priority = ExtraContainerManager.getSlotPriority(slotType);
    }

    protected ItemStack getCuriosStack(EntityMaid maid) {
        var inventory = AccessoriesCapability.getOptionally(maid);
        return inventory.map(handler -> Optional.ofNullable(handler.getContainers().get(slotType))
                .map(stacksHandler -> {
                    var stacks = stacksHandler.getAccessories();
                    if (slotIndex >= stacks.getContainerSize()) {
                        return ItemStack.EMPTY;
                    }
                    return stacks.getItem(slotIndex);
                }).orElse(ItemStack.EMPTY)
        ).orElse(ItemStack.EMPTY);
    }

    public int compareTo(AccessoriesSlotRef other) {
        int pc = Integer.compare(this.priority, other.priority);
        return pc != 0 ? pc : Integer.compare(this.slotIndex, other.slotIndex);
    }
}