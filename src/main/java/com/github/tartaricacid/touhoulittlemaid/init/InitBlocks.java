package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.block.*;
import com.github.tartaricacid.touhoulittlemaid.blockentity.*;
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

    public static BlockEntityType<BlockEntityAltar> ALTAR_TE = registerBlockEntityType("altar", BlockEntityAltar::new, ALTAR);
    public static BlockEntityType<BlockEntityStatue> STATUE_TE = registerBlockEntityType("statue", BlockEntityStatue::new, STATUE);
    public static BlockEntityType<BlockEntityGarageKit> GARAGE_KIT_TE = registerBlockEntityType("garage_kit", BlockEntityGarageKit::new, GARAGE_KIT);
    public static BlockEntityType<BlockEntityMaidBeacon> MAID_BEACON_TE = registerBlockEntityType("maid_beacon", BlockEntityMaidBeacon::new, MAID_BEACON);
    public static BlockEntityType<BlockEntityModelSwitcher> MODEL_SWITCHER_TE = registerBlockEntityType("model_switcher", BlockEntityModelSwitcher::new, MODEL_SWITCHER);
    public static BlockEntityType<BlockEntityGomoku> GOMOKU_TE = registerBlockEntityType("gomoku", BlockEntityGomoku::new, GOMOKU);
    public static BlockEntityType<BlockEntityCChess> CCHESS_TE = registerBlockEntityType("cchess", BlockEntityCChess::new, CCHESS);
    public static BlockEntityType<BlockEntityWChess> WCHESS_TE = registerBlockEntityType("wchess", BlockEntityWChess::new, WCHESS);
    public static BlockEntityType<BlockEntityKeyboard> KEYBOARD_TE = registerBlockEntityType("keyboard", BlockEntityKeyboard::new, KEYBOARD);
    public static BlockEntityType<BlockEntityBookshelf> BOOKSHELF_TE = registerBlockEntityType("bookshelf", BlockEntityBookshelf::new, BOOKSHELF);
    public static BlockEntityType<BlockEntityComputer> COMPUTER_TE = registerBlockEntityType("computer", BlockEntityComputer::new, COMPUTER);
    public static BlockEntityType<BlockEntityShrine> SHRINE_TE = registerBlockEntityType("shrine", BlockEntityShrine::new, SHRINE);
    public static BlockEntityType<BlockEntityPicnicMat> PICNIC_MAT_TE = registerBlockEntityType("picnic_mat", BlockEntityPicnicMat::new, PICNIC_MAT);
    public static BlockEntityType<BlockEntityMaidBed> MAID_BED_TE = registerBlockEntityType("maid_bed", BlockEntityMaidBed::new, MAID_BED);
    public static BlockEntityType<BlockEntitySnackCabinet> SNACK_CABINET_TE = registerBlockEntityType("snack_cabinet", BlockEntitySnackCabinet::new, SNACK_CABINET);

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
