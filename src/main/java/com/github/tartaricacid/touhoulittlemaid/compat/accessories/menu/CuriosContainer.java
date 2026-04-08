package com.github.tartaricacid.touhoulittlemaid.compat.accessories.menu;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.AbstractContainerMenuAccessor;
import com.github.tartaricacid.touhoulittlemaid.api.backpack.ITriggerSlotChange;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import com.github.tartaricacid.touhoulittlemaid.network.message.CuriosS2CUpdateMessage;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class CuriosContainer extends MaidMainContainer {
    public static final MenuType<CuriosContainer> TYPE = new ExtendedScreenHandlerType<>(
            (windowId, inv, data) ->
                    new CuriosContainer(windowId, inv, data.readInt())
    );

    private static final int PREV = 0;
    private static final int NEXT = 1;
    private static final int SLOTS_PER_PAGE = 36;

    private final Optional<AccessoriesCapability> curiosHandler;

    private int maxPages;
    private int page;

    public CuriosContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
        this.curiosHandler = AccessoriesCapability.getOptionally(this.maid);
        int curiosSlotsCount = getVisibleSlots();
        this.maxPages = (curiosSlotsCount - 1) / SLOTS_PER_PAGE;
        this.page = Math.min(page, this.maxPages);
        // 延迟添加 Curios 物品栏
        if (maid != null) {
            this.curiosHandler.ifPresent(this::addCuriosSlotsForPage);
        }
    }

    private int getVisibleSlots() {
        return curiosHandler.map(handler -> {
            int totalSlots = 0;
            for (AccessoriesContainer container : handler.getContainers().values()) {
                totalSlots += container.getSize();
            }
            return totalSlots;
        }).orElse(0);
    }

    public static MenuProvider create(EntityMaid maid) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public boolean shouldCloseCurrentScreen() {
                return false;
            }

            @Override
            public void writeScreenOpeningData(ServerPlayer serverPlayer, FriendlyByteBuf buf) {
                buf.writeInt(maid.getId());
            }

            @Override
            public Component getDisplayName() {
                return Component.literal("Maid Curios Container");
            }

            @Override
            public AbstractContainerMenu createMenu(int index, Inventory inventory, Player player) {
                int entityId = maid.getId();
                return new CuriosContainer(index, inventory, entityId);
            }
        };
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (player instanceof ServerPlayer) {
            if (id == PREV && page > 0) {
                this.updatePage(this.page - 1, player);
                return true;
            }
            if (id == NEXT && page < maxPages) {
                this.updatePage(this.page + 1, player);
                return true;
            }
        }
        return true;
    }

    public void updatePage(int page, Player player) {
        int curiosSlotsCount = getVisibleSlots();
        this.maxPages = (curiosSlotsCount - 1) / SLOTS_PER_PAGE;
        this.page = Math.min(page, this.maxPages);

        this.curiosHandler.ifPresent(handler -> {
            // 清空当前所有槽位，重新添加
            this.slots.clear();
            ((AbstractContainerMenuAccessor) this).tlm$lastSlots().clear();
            ((AbstractContainerMenuAccessor) this).tlm$remoteSlots().clear();

            // 重新添加槽位
            this.addPlayerInv(player.getInventory());
            this.addMaidArmorInv();
            this.addMaidHandInv();
            this.addCuriosSlotsForPage(handler);
        });

        // 发送更新数据包到客户端
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, CuriosS2CUpdateMessage.ID, CuriosS2CUpdateMessage.encode(this.page));
        }
    }

    public void resetPage(Player player) {
        this.updatePage(this.page, player);
    }

    @Override
    protected void addMainDefaultInv() {
        // 留空，表示不添加女仆物品栏
    }

    @Override
    protected void addBackpackInv(Inventory inventory) {
        // 留空，因为父子类执行顺序的问题，我们需要延迟添加 Curios 物品栏
    }

    @Override
    public void setItem(int slotId, int pStateId, ItemStack pStack) {
        if (slotId < this.slots.size()) {
            super.setItem(slotId, pStateId, pStack);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack1 = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack2 = slot.getItem();
            stack1 = stack2.copy();

            if (index < PLAYER_INVENTORY_SIZE) {
                // 先尝试移动到饰品栏
                int baubleSlots = PLAYER_INVENTORY_SIZE + 6;
                if (!this.moveItemStackTo(stack2, baubleSlots, this.slots.size(), false)) {
                    // 如果失败，再尝试移动到女仆物品栏
                    if (!this.moveItemStackTo(stack2, PLAYER_INVENTORY_SIZE, baubleSlots, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(stack2, 0, PLAYER_INVENTORY_SIZE, true)) {
                return ItemStack.EMPTY;
            }

            if (stack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack2.getCount() == stack1.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack2);
            // 触发 Shift 点击取出事件
            if (slot instanceof ITriggerSlotChange slotChange) {
                slotChange.onShiftTakeoff(player, stack1);
            }

            // 用来修正护甲值不变化的问题
            if (PLAYER_INVENTORY_SIZE <= index && index < PLAYER_INVENTORY_SIZE + 4) {
                EquipmentSlot equipmentSlot = SLOT_IDS[index - PLAYER_INVENTORY_SIZE];
                maid.setLastArmorItem(equipmentSlot, stack1);
            }
            // 还有主副手
            if (PLAYER_INVENTORY_SIZE + 4 <= index && index < PLAYER_INVENTORY_SIZE + 6) {
                int slotIndex = index - PLAYER_INVENTORY_SIZE - 4;
                EquipmentSlot equipmentSlot = slotIndex == 0 ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                maid.setLastHandItem(equipmentSlot, stack1);
            }
        }
        return stack1;
    }

    private void addCuriosSlotsForPage(AccessoriesCapability capability) {
        int total = 0;

        int start = page * SLOTS_PER_PAGE;
        int end = start + SLOTS_PER_PAGE;

        for (var entry : capability.getContainers().entrySet()) {
            AccessoriesContainer handler = entry.getValue();

            var stacks = handler.getAccessories();
            int maxIndex = stacks.getContainerSize();

            // 跳过完全在当前页之前的 handler
            if (total + maxIndex <= start) {
                total += maxIndex;
                continue;
            }

            // 如果已经超过当前页范围，直接返回
            if (total >= end) {
                return;
            }

            // 添加当前 handler 中属于本页的槽位
            String identifier = entry.getKey();
            for (int i = 0; i < maxIndex && total < end; i++, total++) {
                if (total >= start) {
                    int displayIndex = total - start;
                    int x = 143 + (displayIndex % 6) * 18;
                    int y = 37 + (displayIndex / 6) * 18;
                    this.addSlot(new MaidCurioSlot(maid, handler, i, identifier, x, y,
                            handler.renderOptions(), false, false));
                }
            }
        }
    }
}