package com.github.tartaricacid.touhoulittlemaid.tileentity;

import cn.sh1rocu.touhoulittlemaid.api.extension.IBlockEntityPersistentData;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.ItemStackHandler;
import com.github.tartaricacid.touhoulittlemaid.init.InitBlocks;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.AltarItemHandler;
import com.github.tartaricacid.touhoulittlemaid.util.PosListData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TileEntityAltar extends BlockEntity implements IBlockEntityPersistentData {
    public static final BlockEntityType<TileEntityAltar> TYPE = BlockEntityType.Builder.of(TileEntityAltar::new, InitBlocks.ALTAR).build(null);
    private static final String STORAGE_ITEM = "StorageItem";
    private static final String IS_RENDER = "IsRender";
    private static final String CAN_PLACE_ITEM = "CanPlaceItem";
    private static final String STORAGE_STATE_ID = "StorageBlockStateId";
    private static final String DIRECTION = "Direction";
    private static final String STORAGE_BLOCK_LIST = "StorageBlockList";
    private static final String CAN_PLACE_ITEM_POS_LIST = "CanPlaceItemPosList";
    public final ItemStackHandler handler = new AltarItemHandler();
    private boolean isRender = false;
    private boolean canPlaceItem = false;
    private BlockState storageState = Blocks.AIR.defaultBlockState();
    private PosListData blockPosList = new PosListData();
    private PosListData canPlaceItemPosList = new PosListData();
    private Direction direction = Direction.SOUTH;

    public TileEntityAltar(BlockPos blockPos, BlockState blockState) {
        super(TYPE, blockPos, blockState);
    }

    public void setForgeData(BlockState storageState, boolean isRender, boolean canPlaceItem, Direction direction,
                             PosListData blockPosList, PosListData canPlaceItemPosList) {
        this.isRender = isRender;
        this.canPlaceItem = canPlaceItem;
        this.storageState = storageState;
        this.direction = direction;
        this.blockPosList = blockPosList;
        this.canPlaceItemPosList = canPlaceItemPosList;
        refresh();
    }

    @Override
    public void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        tlm$getPersistentData().putBoolean(IS_RENDER, isRender);
        tlm$getPersistentData().putBoolean(CAN_PLACE_ITEM, canPlaceItem);
        tlm$getPersistentData().putInt(STORAGE_STATE_ID, Block.getId(storageState));
        tlm$getPersistentData().put(STORAGE_ITEM, handler.serializeNBT(pRegistries));
        tlm$getPersistentData().putString(DIRECTION, direction.getSerializedName());
        tlm$getPersistentData().put(STORAGE_BLOCK_LIST, blockPosList.serialize());
        tlm$getPersistentData().put(CAN_PLACE_ITEM_POS_LIST, canPlaceItemPosList.serialize());
        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        isRender = tlm$getPersistentData().getBoolean(IS_RENDER);
        canPlaceItem = tlm$getPersistentData().getBoolean(CAN_PLACE_ITEM);
        storageState = Block.stateById(tlm$getPersistentData().getInt(STORAGE_STATE_ID));
        handler.deserializeNBT(pRegistries, tlm$getPersistentData().getCompound(STORAGE_ITEM));
        direction = Direction.byName(tlm$getPersistentData().getString(DIRECTION));
        blockPosList.deserialize(tlm$getPersistentData().getList(STORAGE_BLOCK_LIST, Tag.TAG_INT_ARRAY));
        canPlaceItemPosList.deserialize(tlm$getPersistentData().getList(CAN_PLACE_ITEM_POS_LIST, Tag.TAG_INT_ARRAY));
    }

    public BlockPos getWorldPosition() {
        return this.worldPosition;
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

    public boolean isRender() {
        return isRender;
    }

    public boolean isCanPlaceItem() {
        return canPlaceItem;
    }

    public BlockState getStorageState() {
        return storageState;
    }

    public PosListData getBlockPosList() {
        return blockPosList;
    }

    public PosListData getCanPlaceItemPosList() {
        return canPlaceItemPosList;
    }

    public ItemStack getStorageItem() {
        if (canPlaceItem) {
            return handler.getStackInSlot(0);
        }
        return ItemStack.EMPTY;
    }

    public Direction getDirection() {
        return direction;
    }
}
