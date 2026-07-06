package com.github.tartaricacid.touhoulittlemaid.blockentity;

import cn.sh1rocu.touhoulittlemaid.util.neoforge.ValueInputUtil;
import cn.sh1rocu.touhoulittlemaid.util.neoforge.ValueOutputUtil;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemUtil;
import com.github.tartaricacid.touhoulittlemaid.init.InitBlocks;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.ShrineItemHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class BlockEntityShrine extends BlockEntityBase {
    private static final String STORAGE_ITEM = "StorageItem";
    private final ItemStacksResourceHandler handler = new ShrineItemHandler();

    public BlockEntityShrine(BlockPos pos, BlockState blockState) {
        super(InitBlocks.SHRINE_BE, pos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ValueOutputUtil.putChild(output, STORAGE_ITEM, handler);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ValueInputUtil.readChild(input, STORAGE_ITEM, handler);
    }

    public ItemStack getStorageItem() {
        return ItemUtil.getStack(handler, 0);
    }

    public void insertStorageItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        try (Transaction tx = Transaction.openOuter()) {
            int insert = handler.insert(ItemVariant.of(stack), stack.count(), tx);
            if (insert > 0) {
                tx.commit();
                this.refresh();
            }
        }
    }

    public ItemStack extractStorageItem() {
        try (Transaction tx = Transaction.openOuter()) {
            ItemVariant resource = handler.getResource(0);
            if (resource.isBlank()) {
                return ItemStack.EMPTY;
            }
            int extract = handler.extract(0, resource, 1, tx);
            if (extract > 0) {
                tx.commit();
                this.refresh();
                return resource.toStack(extract);
            } else {
                return ItemStack.EMPTY;
            }
        }
    }

    public boolean isEmpty() {
        return handler.getResource(0).isBlank();
    }

    public boolean canInsert(ItemStack stack) {
        return handler.isValid(0, ItemVariant.of(stack));
    }
}
