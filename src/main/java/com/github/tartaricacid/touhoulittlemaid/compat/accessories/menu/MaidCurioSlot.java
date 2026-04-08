package com.github.tartaricacid.touhoulittlemaid.compat.accessories.menu;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.EntityAccessor;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.menu.AccessoriesBasedSlot;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

// FIXME
public class MaidCurioSlot extends AccessoriesBasedSlot {
    private final String identifier;
    private final EntityMaid maid;
    private final SlotReference slotContext;

    private List<Boolean> renderStatuses;
    private boolean canToggleRender;
    private boolean showCosmeticToggle;
    private boolean isCosmetic;

    public MaidCurioSlot(EntityMaid maid, AccessoriesContainer handler, int index, String identifier,
                         int xPosition, int yPosition, List<Boolean> renders,
                         boolean canToggleRender, boolean showCosmeticToggle, boolean isCosmetic) {
        this(maid, handler, index, identifier, xPosition, yPosition, renders, canToggleRender, isCosmetic);
        this.showCosmeticToggle = showCosmeticToggle;
    }

    public MaidCurioSlot(EntityMaid maid, AccessoriesContainer handler, int index, String identifier,
                         int xPosition, int yPosition, List<Boolean> renders,
                         boolean canToggleRender, boolean isCosmetic) {
        super(handler, isCosmetic ? handler.getCosmeticAccessories() : handler.getAccessories(), index, xPosition, yPosition);
        this.identifier = identifier;
        this.renderStatuses = renders;
        this.maid = maid;
        this.canToggleRender = canToggleRender;
        this.isCosmetic = isCosmetic;
        this.slotContext = handler.createReference(index);
        Optional.ofNullable(slotContext.slotContainer()).map(AccessoriesContainer::slotType).ifPresent(slotType
                -> this.setBackground(InventoryMenu.BLOCK_ATLAS, slotType.icon()));
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public boolean canToggleRender() {
        return this.canToggleRender;
    }

    public boolean isCosmetic() {
        return this.isCosmetic;
    }

    public boolean showCosmeticToggle() {
        return this.showCosmeticToggle;
    }

    public boolean getRenderStatus() {
        if (!this.canToggleRender) {
            return true;
        }
        return this.renderStatuses.size() > this.getSlotIndex() &&
                this.renderStatuses.get(this.getSlotIndex());
    }

    @Environment(EnvType.CLIENT)
    public String getSlotName() {
        StringBuilder builder = new StringBuilder();

        if (this.isCosmetic) {
            builder.append(I18n.get("accessories.cosmetic_slot.tooltip.singular"));
        }
        String key = "accessories.slot." + this.identifier;
        if (I18n.exists(key)) {
            builder.append(I18n.get(key));
            return builder.toString();
        }
        builder.append(Character.toUpperCase(this.identifier.charAt(0)))
                .append(this.identifier.substring(1).toLowerCase());
        return builder.toString();
    }

    @Override
    public void set(@Nonnull ItemStack stack) {
        ItemStack current = this.getItem();
        boolean flag = current.isEmpty() && stack.isEmpty();
        super.set(stack);

        if (!flag && !ItemStack.matches(current, stack) &&
                !((EntityAccessor) maid).tlm$firstTick()) {
            Optional.ofNullable(AccessoriesAPI.getAccessory(stack)).ifPresent(curio -> curio.onEquipFromUse(stack, this.slotContext));
        }
    }

    @Override
    public boolean allowModification(@Nonnull Player pPlayer) {
        return true;
    }
}