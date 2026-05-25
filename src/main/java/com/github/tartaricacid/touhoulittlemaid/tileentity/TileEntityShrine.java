package com.github.tartaricacid.touhoulittlemaid.tileentity;

import cn.sh1rocu.touhoulittlemaid.api.extension.IBlockEntityPersistentData;
import cn.sh1rocu.touhoulittlemaid.util.neoforge.ValueInputUtil;
import cn.sh1rocu.touhoulittlemaid.util.neoforge.ValueOutputUtil;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemStacksResourceHandler;
import cn.sh1rocu.touhoulittlemaid.util.transfer.ItemUtil;
import com.github.tartaricacid.touhoulittlemaid.init.InitBlocks;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;

public class TileEntityShrine extends BlockEntity implements IBlockEntityPersistentData {
    private static final String STORAGE_ITEM = "StorageItem";
    private final ItemStacksResourceHandler handler = new ItemStacksResourceHandler(1) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            // 当物品栏内容发生变化时，这个方法会被调用
            // 我们需要在这里调用 refresh() 来通知 Minecraft 该方块实体的数据已更新，需要保存并同步到客户端
            refresh();
        }

        @Override
        public boolean isValid(int index, ItemVariant resource) {
            return resource.is(InitItems.FILM);
        }

        @Override
        protected int getCapacity(int index, ItemVariant resource) {
            return 1;
        }
    };

    public TileEntityShrine(BlockPos pos, BlockState blockState) {
        super(InitBlocks.SHRINE_TE, pos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ValueOutputUtil.putChild(output, STORAGE_ITEM, handler);
        super.saveAdditional(output);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ValueInputUtil.readChild(input, STORAGE_ITEM, handler);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return this.saveWithoutMetadata(pRegistries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void refresh() {
        this.setChanged();
        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
        }
    }

    public ItemStack getStorageItem() {
        return ItemUtil.getStack(handler, 0);
    }

    public void insertStorageItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        try (Transaction tx = Transaction.openOuter()) {
            handler.insert(ItemVariant.of(stack), stack.count(), tx);
            tx.commit();
        }
    }

    public ItemStack extractStorageItem() {
        try (Transaction tx = Transaction.openOuter()) {
            ItemVariant resource = handler.getResource(0);
            int extract = handler.extract(0, resource, 1, tx);
            if (extract > 0) {
                tx.commit();
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
