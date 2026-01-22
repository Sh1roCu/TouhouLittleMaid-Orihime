package com.github.tartaricacid.touhoulittlemaid.compat.trinkets.menu;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.EntityAccessor;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.ItemStackHandler;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.SlotItemHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.datafixers.util.Pair;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class MaidCurioSlot extends SlotItemHandler {
    private final String identifier;
    private final EntityMaid maid;
    private final SlotReference slotContext;

    private boolean canToggleRender;
    private boolean showCosmeticToggle;
    private boolean isCosmetic;

    public MaidCurioSlot(EntityMaid maid, TrinketInventory trinketInventory, IItemHandler handler, int index, String identifier,
                         int xPosition, int yPosition,
                         boolean canToggleRender, boolean showCosmeticToggle, boolean isCosmetic) {
        this(maid, trinketInventory, handler, index, identifier, xPosition, yPosition, canToggleRender);
        this.showCosmeticToggle = showCosmeticToggle;
        this.isCosmetic = isCosmetic;
    }

    public MaidCurioSlot(EntityMaid maid, TrinketInventory trinketInventory, IItemHandler handler, int index, String identifier,
                         int xPosition, int yPosition, boolean canToggleRender) {
        super(handler, index, xPosition, yPosition);
        this.identifier = identifier;
        this.maid = maid;
        this.canToggleRender = canToggleRender;
        this.slotContext = new SlotReference(trinketInventory, index);
    }

    @Environment(EnvType.CLIENT)
    public String getSlotName() {
        var slotType = slotContext.inventory().getSlotType();
        return slotType != null ? slotType.getTranslation().getString() : identifier;
    }

    @Override
    public @Nullable Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        var slotType = slotContext.inventory().getSlotType();
        return Pair.of(InventoryMenu.BLOCK_ATLAS, slotType.getIcon());
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

    @Override
    public void set(@Nonnull ItemStack stack) {
        ItemStack current = this.getItem();
        boolean flag = current.isEmpty() && stack.isEmpty();
        super.set(stack);

        if (!flag && !ItemStack.matches(current, stack) &&
                !((EntityAccessor) maid).tlm$firstTick()) {
            Trinket trinket = TrinketsApi.getTrinket(stack.getItem());
            if (trinket.canEquip(stack, this.slotContext, maid)) {
                trinket.onEquip(stack, this.slotContext, maid);
                slotContext.inventory().update();
            }
        }
    }

    @Override
    public boolean allowModification(@Nonnull Player pPlayer) {
        return true;
    }

    public static class DynamicStackHandler extends ItemStackHandler {
        protected Function<Integer, SlotReference> ctxBuilder;

        public DynamicStackHandler(int size, Function<Integer, SlotReference> ctxBuilder) {
            super(size);
            this.ctxBuilder = ctxBuilder;
        }

        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            SlotReference ctx = this.ctxBuilder.apply(slot);
            return TrinketsApi.getTrinket(stack.getItem()).canEquip(stack, ctx, ctx.inventory().getComponent().getEntity());
        }

        @Nonnull
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack existing = this.stacks.get(slot);
            SlotReference ctx = this.ctxBuilder.apply(slot);
            if (!TrinketsApi.getTrinket(existing.getItem()).canUnequip(existing, ctx, ctx.inventory().getComponent().getEntity())) {
                return ItemStack.EMPTY;
            }
            boolean isCreative = ctx.inventory().getComponent().getEntity() instanceof Player player && player.isCreative();

            if ((existing.isEmpty() || isCreative || !EnchantmentHelper.hasBindingCurse(existing))) {
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }
    }
}