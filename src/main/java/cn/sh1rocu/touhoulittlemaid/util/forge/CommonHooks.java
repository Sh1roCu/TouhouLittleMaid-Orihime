package cn.sh1rocu.touhoulittlemaid.util.forge;

import cn.sh1rocu.touhoulittlemaid.api.event.FarmlandTrampleEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * From NeoForge
 */
public class CommonHooks {
    public static Optional<BlockPos> isLivingOnLadder(BlockState state, Level level, BlockPos pos, LivingEntity entity) {
        boolean isSpectator = (entity instanceof Player && entity.isSpectator());
        if (isSpectator)
            return Optional.empty();
//        if (!NeoForgeConfig.SERVER.fullBoundingBoxLadders.get()) {
//            return state.isLadder(level, pos, entity) ? Optional.of(pos) : Optional.empty();
//        } else {
//            AABB bb = entity.getBoundingBox();
//            int mX = Mth.floor(bb.minX);
//            int mY = Mth.floor(bb.minY);
//            int mZ = Mth.floor(bb.minZ);
//            for (int y2 = mY; y2 < bb.maxY; y2++) {
//                for (int x2 = mX; x2 < bb.maxX; x2++) {
//                    for (int z2 = mZ; z2 < bb.maxZ; z2++) {
//                        BlockPos tmp = new BlockPos(x2, y2, z2);
//                        state = level.getBlockState(tmp);
//                        /*if (state.isLadder(level, tmp, entity)) {*/
//                        if (isLadder(state, level, tmp, entity)) {
//                            return Optional.of(tmp);
//                        }
//                    }
//                }
//            }
//            return Optional.empty();
//        }
        return isLadder(state, level, pos, entity) ? Optional.of(pos) : Optional.empty();
    }

    public static boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return state.is(BlockTags.CLIMBABLE);
    }

    public static boolean onFarmlandTrample(Level level, BlockPos pos, BlockState state, float fallDistance, Entity entity) {
        FarmlandTrampleEvent event = new FarmlandTrampleEvent(level, pos, state, fallDistance, entity);
        FarmlandTrampleEvent.CALLBACK.invoker().post(event);
        return !event.isCanceled();
    }
}
