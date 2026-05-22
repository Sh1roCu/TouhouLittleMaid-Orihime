package com.github.tartaricacid.touhoulittlemaid.compat.ironchest;

import anner.ironchest.blocks.blockentities.GenericChestEntity;
import com.github.tartaricacid.touhoulittlemaid.api.bauble.IChestType;
import com.github.tartaricacid.touhoulittlemaid.inventory.chest.ChestManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class IronChestType implements IChestType {
    private static final String IRON_CHEST_ID = "ironchest";

    public static void register(ChestManager manager) {
        if (FabricLoader.getInstance().isModLoaded(IRON_CHEST_ID)) {
            manager.add(new IronChestType());
        }
    }

    @Override
    public boolean isChest(BlockEntity chest) {
        if (FabricLoader.getInstance().isModLoaded(IRON_CHEST_ID)) {
            return chest instanceof GenericChestEntity;
        }
        return false;
    }

    @Override
    public boolean canOpenByPlayer(BlockEntity chest, Player player) {
        if (FabricLoader.getInstance().isModLoaded(IRON_CHEST_ID) && chest instanceof GenericChestEntity ironChestBlock) {
            return ironChestBlock.canOpen(player);
        }
        return false;
    }

    @Override
    public int getOpenCount(BlockGetter level, BlockPos pos, BlockEntity chest) {
        if (FabricLoader.getInstance().isModLoaded(IRON_CHEST_ID) && chest instanceof GenericChestEntity) {
            return GenericChestEntity.getOpenCount(level, pos);
        }
        return DENY_COUNT;
    }
}
