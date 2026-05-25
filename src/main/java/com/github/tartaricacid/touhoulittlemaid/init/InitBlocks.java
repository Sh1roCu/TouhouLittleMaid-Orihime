package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.block.*;
import com.github.tartaricacid.touhoulittlemaid.tileentity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Function;

public final class InitBlocks {
    public static void init() {

    }

    public static Block MAID_BED = registerBlock("maid_bed", BlockMaidBed::new);
    public static Block ALTAR = registerBlock("altar", BlockAltar::new);
    public static Block STATUE = registerBlock("statue", BlockStatue::new);
    public static Block GARAGE_KIT = registerBlock("garage_kit", BlockGarageKit::new);
    public static Block MAID_BEACON = registerBlock("maid_beacon", BlockMaidBeacon::new);
    public static Block MODEL_SWITCHER = registerBlock("model_switcher", BlockModelSwitcher::new);
    public static Block PICNIC_MAT = registerBlock("picnic_mat", BlockPicnicMat::new);
    public static Block GOMOKU = registerBlock("gomoku", BlockGomoku::new);
    public static Block CCHESS = registerBlock("cchess", BlockCChess::new);
    public static Block WCHESS = registerBlock("wchess", BlockWChess::new);
    public static Block KEYBOARD = registerBlock("keyboard", BlockKeyboard::new);
    public static Block BOOKSHELF = registerBlock("bookshelf", BlockBookshelf::new);
    public static Block COMPUTER = registerBlock("computer", BlockComputer::new);
    public static Block SHRINE = registerBlock("shrine", BlockShrine::new);
    public static Block SCARECROW = registerBlock("scarecrow", BlockScarecrow::new);
    public static Block SNACK_CABINET = registerBlock("snack_cabinet", BlockSnackCabinet::new);

    public static BlockEntityType<TileEntityAltar> ALTAR_TE = registerBlockEntityType(TileEntityAltar::new, ALTAR);
    public static BlockEntityType<TileEntityStatue> STATUE_TE = registerBlockEntityType(TileEntityStatue::new, STATUE);
    public static BlockEntityType<TileEntityGarageKit> GARAGE_KIT_TE = registerBlockEntityType(TileEntityGarageKit::new, GARAGE_KIT);
    public static BlockEntityType<TileEntityMaidBeacon> MAID_BEACON_TE = registerBlockEntityType(TileEntityMaidBeacon::new, MAID_BEACON);
    public static BlockEntityType<TileEntityModelSwitcher> MODEL_SWITCHER_TE = registerBlockEntityType(TileEntityModelSwitcher::new, MODEL_SWITCHER);
    public static BlockEntityType<TileEntityGomoku> GOMOKU_TE = registerBlockEntityType(TileEntityGomoku::new, GOMOKU);
    public static BlockEntityType<TileEntityCChess> CCHESS_TE = registerBlockEntityType(TileEntityCChess::new, CCHESS);
    public static BlockEntityType<TileEntityWChess> WCHESS_TE = registerBlockEntityType(TileEntityWChess::new, WCHESS);
    public static BlockEntityType<TileEntityKeyboard> KEYBOARD_TE = registerBlockEntityType(TileEntityKeyboard::new, KEYBOARD);
    public static BlockEntityType<TileEntityBookshelf> BOOKSHELF_TE = registerBlockEntityType(TileEntityBookshelf::new, BOOKSHELF);
    public static BlockEntityType<TileEntityComputer> COMPUTER_TE = registerBlockEntityType(TileEntityComputer::new, COMPUTER);
    public static BlockEntityType<TileEntityShrine> SHRINE_TE = registerBlockEntityType(TileEntityShrine::new, SHRINE);
    public static BlockEntityType<TileEntityPicnicMat> PICNIC_MAT_TE = registerBlockEntityType(TileEntityPicnicMat::new, PICNIC_MAT);
    public static BlockEntityType<TileEntityMaidBed> MAID_BED_TE = registerBlockEntityType(TileEntityMaidBed::new, MAID_BED);
    public static BlockEntityType<TileEntitySnackCabinet> SNACK_CABINET_TE = registerBlockEntityType(TileEntitySnackCabinet::new, SNACK_CABINET);

    private static <B extends Block> B registerBlock(String id, Function<Identifier, ? extends B> func) {
        Identifier loc = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id);
        return Registry.register(BuiltInRegistries.BLOCK, loc, func.apply(loc));
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory, blocks).build();
    }
}
