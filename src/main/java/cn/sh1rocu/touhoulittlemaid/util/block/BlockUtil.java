package cn.sh1rocu.touhoulittlemaid.util.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtil {
    public static boolean canEntityDestroy(Block block, BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        if (entity instanceof EnderDragon) {
            return !block.defaultBlockState().is(BlockTags.DRAGON_IMMUNE);
        } else if ((entity instanceof WitherBoss) ||
                (entity instanceof WitherSkull)) {
            return state.isAir() || WitherBoss.canDestroy(state);
        }

        return true;
    }
}
