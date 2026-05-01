package com.github.tartaricacid.touhoulittlemaid.compat.expandedstorage;

import com.github.tartaricacid.touhoulittlemaid.api.bauble.IChestType;
import com.github.tartaricacid.touhoulittlemaid.inventory.chest.ChestManager;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public final class ExpandedChestType implements IChestType {
    private static final String EXPANDED_CHEST_ID = "expandedstorage";

    private static boolean isExpandedStorageContainer(BlockEntity chest) {
        if (chest == null || chest.getLevel() == null) {
            return false;
        }
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(chest.getBlockState().getBlock());
        if (blockId == null || !EXPANDED_CHEST_ID.equals(blockId.getNamespace())) {
            return false;
        }
        return ItemStorage.SIDED.find(chest.getLevel(), chest.getBlockPos(), chest.getBlockState(), chest, null) != null;
    }

    public static void register(ChestManager manager) {
        if (FabricLoader.getInstance().isModLoaded(EXPANDED_CHEST_ID)) {
            manager.add(new ExpandedChestType());
        }
    }

    @Override
    public boolean isChest(BlockEntity chest) {
        if (!FabricLoader.getInstance().isModLoaded(EXPANDED_CHEST_ID)) {
            return false;
        }
        return isExpandedStorageContainer(chest);
    }

    @Override
    public boolean canOpenByPlayer(BlockEntity chest, Player player) {
        if (!FabricLoader.getInstance().isModLoaded(EXPANDED_CHEST_ID) || !isExpandedStorageContainer(chest)) {
            return false;
        }
        if (chest instanceof Container container) {
            return container.stillValid(player);
        }
        return false;
    }

    @Override
    public int getOpenCount(BlockGetter level, BlockPos pos, BlockEntity chest) {
        if (!FabricLoader.getInstance().isModLoaded(EXPANDED_CHEST_ID) || !isExpandedStorageContainer(chest)) {
            return DENY_COUNT;
        }
        // 优先使用方块的 OPEN 属性判断是否正在被打开，避免并发访问。
        if (chest.getBlockState().hasProperty(BlockStateProperties.OPEN)
                && chest.getBlockState().getValue(BlockStateProperties.OPEN)) {
            return 1;
        }
        // 无 OPEN 属性时退化为允许，范围已限定为 expandedstorage 命名空间。
        return ALLOW_COUNT;
    }
}
