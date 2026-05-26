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
    public static Block ALTAR = registerBlock("altar", BlockExplodedAltar::new);
    public static Block STATUE = registerBlock("statue", BlockStatue::new);
    public static Block GARAGE_KIT = registerBlock("garage_kit", BlockGarageKit::new);
    public static Block MAID_BEACON = registerBlock("maid_beacon", BlockMaidBeacon::new);
    public static Block MODEL_SWITCHER = registerBlock("model_switcher", BlockModelSwitcher::new);
    public static Block PICNIC_MAT = registerBlock("picnic_mat", BlockExplodedPicnicMat::new);
    public static Block GOMOKU = registerBlock("gomoku", BlockGomoku::new);
    public static Block CCHESS = registerBlock("cchess", BlockExplodedCChess::new);
    public static Block WCHESS = registerBlock("wchess", BlockExplodedWChess::new);
    public static Block KEYBOARD = registerBlock("keyboard", BlockKeyboard::new);
    public static Block BOOKSHELF = registerBlock("bookshelf", BlockBookshelf::new);
    public static Block COMPUTER = registerBlock("computer", BlockComputer::new);
    public static Block SHRINE = registerBlock("shrine", BlockShrine::new);
    public static Block SCARECROW = registerBlock("scarecrow", BlockScarecrow::new);
    public static Block SNACK_CABINET = registerBlock("snack_cabinet", BlockSnackCabinet::new);

    public static BlockEntityType<TileEntityAltar> ALTAR_TE = registerBlockEntityType("altar", TileEntityAltar::new, ALTAR);
    public static BlockEntityType<TileEntityStatue> STATUE_TE = registerBlockEntityType("statue", TileEntityStatue::new, STATUE);
    public static BlockEntityType<TileEntityGarageKit> GARAGE_KIT_TE = registerBlockEntityType("garage_kit", TileEntityGarageKit::new, GARAGE_KIT);
    public static BlockEntityType<TileEntityMaidBeacon> MAID_BEACON_TE = registerBlockEntityType("maid_beacon", TileEntityMaidBeacon::new, MAID_BEACON);
    public static BlockEntityType<TileEntityModelSwitcher> MODEL_SWITCHER_TE = registerBlockEntityType("model_switcher", TileEntityModelSwitcher::new, MODEL_SWITCHER);
    public static BlockEntityType<TileEntityGomoku> GOMOKU_TE = registerBlockEntityType("gomoku", TileEntityGomoku::new, GOMOKU);
    public static BlockEntityType<TileEntityCChess> CCHESS_TE = registerBlockEntityType("cchess", TileEntityCChess::new, CCHESS);
    public static BlockEntityType<TileEntityWChess> WCHESS_TE = registerBlockEntityType("wchess", TileEntityWChess::new, WCHESS);
    public static BlockEntityType<TileEntityKeyboard> KEYBOARD_TE = registerBlockEntityType("keyboard", TileEntityKeyboard::new, KEYBOARD);
    public static BlockEntityType<TileEntityBookshelf> BOOKSHELF_TE = registerBlockEntityType("bookshelf", TileEntityBookshelf::new, BOOKSHELF);
    public static BlockEntityType<TileEntityComputer> COMPUTER_TE = registerBlockEntityType("computer", TileEntityComputer::new, COMPUTER);
    public static BlockEntityType<TileEntityShrine> SHRINE_TE = registerBlockEntityType("shrine", TileEntityShrine::new, SHRINE);
    public static BlockEntityType<TileEntityPicnicMat> PICNIC_MAT_TE = registerBlockEntityType("picnic_mat", TileEntityPicnicMat::new, PICNIC_MAT);
    public static BlockEntityType<TileEntityMaidBed> MAID_BED_TE = registerBlockEntityType("maid_bed", TileEntityMaidBed::new, MAID_BED);
    public static BlockEntityType<TileEntitySnackCabinet> SNACK_CABINET_TE = registerBlockEntityType("snack_cabinet", TileEntitySnackCabinet::new, SNACK_CABINET);

    private static <B extends Block> B registerBlock(String id, Function<Identifier, ? extends B> func) {
        Identifier loc = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id);
        return Registry.register(BuiltInRegistries.BLOCK, loc, func.apply(loc));
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        var type = FabricBlockEntityTypeBuilder.create(factory, blocks).build();
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), type);
        return type;
    }
}
