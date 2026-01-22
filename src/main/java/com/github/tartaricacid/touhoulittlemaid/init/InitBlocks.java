package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.block.*;
import com.github.tartaricacid.touhoulittlemaid.tileentity.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class InitBlocks {
    public static void init() {

    }

    public static Block MAID_BED = registerBlock("maid_bed", new BlockMaidBed());
    public static Block ALTAR = registerBlock("altar", new BlockAltar());
    public static Block STATUE = registerBlock("statue", new BlockStatue());
    public static Block GARAGE_KIT = registerBlock("garage_kit", new BlockGarageKit());
    public static Block MAID_BEACON = registerBlock("maid_beacon", new BlockMaidBeacon());
    public static Block MODEL_SWITCHER = registerBlock("model_switcher", new BlockModelSwitcher());
    public static Block PICNIC_MAT = registerBlock("picnic_mat", new BlockPicnicMat());
    public static Block GOMOKU = registerBlock("gomoku", new BlockGomoku());
    public static Block CCHESS = registerBlock("cchess", new BlockCChess());
    public static Block WCHESS = registerBlock("wchess", new BlockWChess());
    public static Block KEYBOARD = registerBlock("keyboard", new BlockKeyboard());
    public static Block BOOKSHELF = registerBlock("bookshelf", new BlockBookshelf());
    public static Block COMPUTER = registerBlock("computer", new BlockComputer());
    public static Block SHRINE = registerBlock("shrine", new BlockShrine());
    public static Block SCARECROW = registerBlock("scarecrow", new BlockScarecrow());
    public static Block SNACK_CABINET = registerBlock("snack_cabinet", new BlockSnackCabinet());

    public static BlockEntityType<TileEntityAltar> ALTAR_TE = registerBlockEntityType("altar", TileEntityAltar.TYPE);
    public static BlockEntityType<TileEntityStatue> STATUE_TE = registerBlockEntityType("statue", TileEntityStatue.TYPE);
    public static BlockEntityType<TileEntityGarageKit> GARAGE_KIT_TE = registerBlockEntityType("garage_kit", TileEntityGarageKit.TYPE);
    public static BlockEntityType<TileEntityMaidBeacon> MAID_BEACON_TE = registerBlockEntityType("maid_beacon", TileEntityMaidBeacon.TYPE);
    public static BlockEntityType<TileEntityModelSwitcher> MODEL_SWITCHER_TE = registerBlockEntityType("model_switcher", TileEntityModelSwitcher.TYPE);
    public static BlockEntityType<TileEntityGomoku> GOMOKU_TE = registerBlockEntityType("gomoku", TileEntityGomoku.TYPE);
    public static BlockEntityType<TileEntityCChess> CCHESS_TE = registerBlockEntityType("cchess", TileEntityCChess.TYPE);
    public static BlockEntityType<TileEntityWChess> WCHESS_TE = registerBlockEntityType("wchess", TileEntityWChess.TYPE);
    public static BlockEntityType<TileEntityKeyboard> KEYBOARD_TE = registerBlockEntityType("keyboard", TileEntityKeyboard.TYPE);
    public static BlockEntityType<TileEntityBookshelf> BOOKSHELF_TE = registerBlockEntityType("bookshelf", TileEntityBookshelf.TYPE);
    public static BlockEntityType<TileEntityComputer> COMPUTER_TE = registerBlockEntityType("computer", TileEntityComputer.TYPE);
    public static BlockEntityType<TileEntityShrine> SHRINE_TE = registerBlockEntityType("shrine", TileEntityShrine.TYPE);
    public static BlockEntityType<TileEntityPicnicMat> PICNIC_MAT_TE = registerBlockEntityType("picnic_mat", TileEntityPicnicMat.TYPE);
    public static BlockEntityType<TileEntityMaidBed> MAID_BED_TE = registerBlockEntityType("maid_bed", TileEntityMaidBed.TYPE);
    public static BlockEntityType<TileEntitySnackCabinet> SNACK_CABINET_TE = registerBlockEntityType("snack_cabinet", TileEntitySnackCabinet.TYPE);

    private static Block registerBlock(String id, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), block);
    }

    private static <T extends BlockEntityType<?>> T registerBlockEntityType(String id, T block) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), block);
    }
}
