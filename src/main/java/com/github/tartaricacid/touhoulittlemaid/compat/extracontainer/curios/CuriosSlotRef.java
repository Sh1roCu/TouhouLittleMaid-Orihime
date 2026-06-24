package com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.curios;

import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.ContainerRef;
import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.ExtraContainerManager;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.world.item.ItemStack;

public abstract class CuriosSlotRef implements ContainerRef {
    public final String slotType;
    public final int slotIndex;
    public final int priority;

    protected CuriosSlotRef(String slotType, int slotIndex) {
        this.slotType = slotType;
        this.slotIndex = slotIndex;
        this.priority = ExtraContainerManager.getSlotPriority(slotType);
    }

    protected ItemStack getCuriosStack(EntityMaid maid) {
        return TrinketsApi.getAttachment(maid).allEquipped(false).stream()
                .filter(t -> t.slotType().getId().equals(slotType))
                .map(tuple -> {
                    if (slotIndex >= tuple.inventory().getContainerSize()) {
                        return ItemStack.EMPTY;
                    }
                    return tuple.inventory().getItem(slotIndex);
                }).findFirst().orElse(ItemStack.EMPTY);
    }

    public int compareTo(CuriosSlotRef other) {
        int pc = Integer.compare(this.priority, other.priority);
        return pc != 0 ? pc : Integer.compare(this.slotIndex, other.slotIndex);
    }
}