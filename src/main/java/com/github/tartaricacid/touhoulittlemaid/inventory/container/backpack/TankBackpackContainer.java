package com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.backpack.data.TankBackpackData;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import com.github.tartaricacid.touhoulittlemaid.util.MaidFluidUtil;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TankBackpackContainer extends MaidMainContainer {
    public static final MenuType<TankBackpackContainer> TYPE = new ExtendedMenuType<>(TankBackpackContainer::new, ByteBufCodecs.INT);
    private static final Identifier INPUT_SLOT = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "container/slot/tank_input");
    private static final Identifier OUTPUT_SLOT = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "container/slot/tank_output");
    private final ContainerData data;

    public TankBackpackContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
        TankBackpackData tankData;
        if (this.getMaid().getBackpackData() instanceof TankBackpackData) {
            tankData = (TankBackpackData) this.getMaid().getBackpackData();
        } else {
            tankData = new TankBackpackData(this.getMaid());
        }
        this.data = tankData.getDataAccess();
        this.addSlot(new TankInputSlot(tankData, 0, 161, 101));
        this.addSlot(new TankOutputSlot(tankData, 1, 161, 140));
        this.addDataSlots(this.data);
    }

    @Override
    protected void addBackpackInv(Inventory inventory) {
        for (int i = 0; i < 6; i++) {
            addSlot(BackpackSlot.create(maid, 6 + i, 143 + 18 * i, 57));
        }
        for (int i = 0; i < 6; i++) {
            addSlot(BackpackSlot.create(maid, 12 + i, 143 + 18 * i, 75));
        }
    }

    // *81将mB转为Fabric的流体滴数
    public long getFluidCount() {
        return this.data.get(0) * 81L;
    }

    public static class TankInputSlot extends Slot {
        public TankInputSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return MaidFluidUtil.fluidOnItem(stack) != null;
        }

        @Override
        public Identifier getNoItemIcon() {
            return INPUT_SLOT;
        }
    }

    public static class TankOutputSlot extends Slot {
        public TankOutputSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return MaidFluidUtil.fluidOnItem(stack) != null;
        }

        @Override
        public Identifier getNoItemIcon() {
            return OUTPUT_SLOT;
        }
    }
}
