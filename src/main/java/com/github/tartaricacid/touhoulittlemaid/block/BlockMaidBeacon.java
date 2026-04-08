package com.github.tartaricacid.touhoulittlemaid.block;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.item.ItemMaidBeacon;
import com.github.tartaricacid.touhoulittlemaid.network.message.OpenBeaconGuiMessage;
import com.github.tartaricacid.touhoulittlemaid.tileentity.TileEntityMaidBeacon;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Locale;

public class BlockMaidBeacon extends BaseEntityBlock {
    public static final EnumProperty<Position> POSITION = EnumProperty.create("position", Position.class);
    private static final VoxelShape UP_AABB = Block.box(3, 1, 3, 13, 16, 13);
    private static final VoxelShape DOWN_AABB = Block.box(6.5, 0, 6.5, 9.5, 26, 9.5);

    public BlockMaidBeacon() {
        super(Properties.of().sound(SoundType.WOOD).strength(2, 2).noOcclusion().pushReaction(PushReaction.BLOCK)
                .lightLevel(s -> s.getValue(POSITION) == Position.DOWN ? 0 : 15));
        this.registerDefaultState(this.stateDefinition.any().setValue(POSITION, Position.DOWN));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(BlockMaidBeacon.POSITION) != Position.DOWN) {
            return new TileEntityMaidBeacon(pos, state);
        }
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, TileEntityMaidBeacon.TYPE, TileEntityMaidBeacon::serverTick);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.getBlockEntity(pos) instanceof TileEntityMaidBeacon) {
            if (!worldIn.isClientSide) {
                ServerPlayNetworking.send((ServerPlayer) player, OpenBeaconGuiMessage.ID, OpenBeaconGuiMessage.encode(pos));
            }
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Position position = state.getValue(POSITION);
        return position == Position.DOWN ? DOWN_AABB : UP_AABB;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing.getAxis() == Direction.Axis.Y) {
            Position position = stateIn.getValue(POSITION);
            if (position == Position.DOWN && facing == Direction.UP) {
                if (!facingState.is(this) || facingState.getValue(POSITION) == Position.DOWN) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
            if (position != Position.DOWN && facing == Direction.DOWN) {
                if (!facingState.is(this) || facingState.getValue(POSITION) == Position.UP_W_E || facingState.getValue(POSITION) == Position.UP_N_S) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide && player.isCreative()) {
            Position position = state.getValue(POSITION);
            if (position != Position.DOWN) {
                BlockPos belowPos = pos.below();
                BlockState belowState = worldIn.getBlockState(belowPos);
                if (belowState.is(this) && belowState.getValue(POSITION) == Position.DOWN) {
                    worldIn.setBlock(belowPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
                    worldIn.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, belowPos, Block.getId(belowState));
                }
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && !isMoving) {
            BlockEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof TileEntityMaidBeacon) {
                popResource(worldIn, pos, ItemMaidBeacon.tileEntityToItemStack((TileEntityMaidBeacon) te));
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    //public ItemStack getCloneItemStack(@NotNull BlockState state, @NotNull HitResult target, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Player player) {
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(InitItems.MAID_BEACON);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level world = context.getLevel();
        int maxHeight = world.getMaxBuildHeight() - 1;
        if (blockpos.getY() < maxHeight && world.getBlockState(blockpos.above()).canBeReplaced(context)) {
            return super.getStateForPlacement(context);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction facing = getHorizontalDirection(placer);
        BlockState stateUp;
        if (facing == Direction.SOUTH || facing == Direction.NORTH) {
            stateUp = this.defaultBlockState().setValue(BlockMaidBeacon.POSITION, Position.UP_N_S);
        } else {
            stateUp = this.defaultBlockState().setValue(BlockMaidBeacon.POSITION, Position.UP_W_E);
        }
        worldIn.setBlock(pos.above(), stateUp, Block.UPDATE_ALL);
        BlockEntity te = worldIn.getBlockEntity(pos.above());
        if (te instanceof TileEntityMaidBeacon tileEntityMaidBeacon) {
            ItemMaidBeacon.itemStackToTileEntity(stack, tileEntityMaidBeacon);
            tileEntityMaidBeacon.refresh();
        }
    }

    @Override
    public BlockState rotate(BlockState state, /*LevelAccessor world, BlockPos pos,*/ Rotation direction) {
        switch (direction) {
            case CLOCKWISE_90:
            case COUNTERCLOCKWISE_90:
                if (state.getValue(POSITION) == Position.UP_N_S) {
                    return state.setValue(POSITION, Position.UP_W_E);
                }
                if (state.getValue(POSITION) == Position.UP_W_E) {
                    return state.setValue(POSITION, Position.UP_N_S);
                }
                return state;
            default:
                return state;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POSITION);
    }

    private Direction getHorizontalDirection(@Nullable LivingEntity placer) {
        return placer == null ? Direction.NORTH : placer.getDirection();
    }

    public enum Position implements StringRepresentable {
        // Beacon State
        UP_N_S, UP_W_E, DOWN;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.US);
        }

        @Override
        public String toString() {
            return getSerializedName();
        }
    }
}
