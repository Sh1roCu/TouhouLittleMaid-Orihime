package com.github.tartaricacid.touhoulittlemaid.compat.curios.menu;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandlerSlot;

public class MaidCurioSlot /*extends ResourceHandlerSlot*/ {
//    private final String identifier;
//    private final EntityMaid maid;
//    private final TrinketSlotReference slotContext;
//
//    private List<Boolean> renderStatuses;
//    private boolean canToggleRender;
//    private boolean showCosmeticToggle;
//    private boolean isVisible;
//
//    public MaidCurioSlot(EntityMaid maid, TrinketInventoryImpl handler, int index, String identifier,
//                         int xPosition, int yPosition, boolean isVisible) {
//        super(new ResourceHandlerSlot(), index, xPosition, yPosition);
//        this.maid = maid;
//        this.identifier = identifier;
//        this.isVisible = isVisible;
//    }
//
//    public String getIdentifier() {
//        return this.identifier;
//    }
//
//
//    @Override
//    public @Nullable Pair<Identifier, Identifier> getNoItemIcon() {
//        var slotType = slotContext.type();
//        return Pair.of(InventoryMenu.BLOCK_ATLAS, slotType == null ? null : slotType.icon());
//    }
//
//    public String getSlotName() {
//        StringBuilder builder = new StringBuilder();
//
//        if (this.isVisible) {
//            builder.append(I18n.get("accessories.cosmetic_slot.tooltip.singular"));
//        }
//        String key = "accessories.slot." + this.identifier;
//        if (I18n.exists(key)) {
//            builder.append(I18n.get(key));
//            return builder.toString();
//        }
//        builder.append(Character.toUpperCase(this.identifier.charAt(0)))
//                .append(this.identifier.substring(1).toLowerCase());
//        return builder.toString();
//    }
//
//    @Override
//    public void set(@Nonnull ItemStack stack) {
//        ItemStack current = this.getItem();
//        boolean flag = current.isEmpty() && stack.isEmpty();
//        super.set(stack);
//
//        if (!flag && !ItemStack.matches(current, stack) &&
//                !((EntityAccessor) maid).tlm$firstTick()) {
//            Optional.ofNullable(AccessoriesAPI.getAccessory(stack)).ifPresent(curio -> curio.onEquipFromUse(stack, this.slotContext));
//        }
//    }
//
//    @Override
//    public boolean allowModification(@Nonnull Player pPlayer) {
//        return true;
//    }
}
