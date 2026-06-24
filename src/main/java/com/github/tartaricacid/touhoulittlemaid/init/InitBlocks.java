package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.block.*;
import com.github.tartaricacid.touhoulittlemaid.blockentity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Function;

public final class InitBlocks {
    public static void init() {

    }

    public static Block PINK_MAID_BED = registerBlock("pink_maid_bed", id -> new BlockMaidBed(id, DyeColor.PINK));
    public static Block WHITE_MAID_BED = registerBlock("white_maid_bed", id -> new BlockMaidBed(id, DyeColor.WHITE));
    public static Block BLACK_MAID_BED = registerBlock("black_maid_bed", id -> new BlockMaidBed(id, DyeColor.BLACK));
    public static Block YELLOW_MAID_BED = registerBlock("yellow_maid_bed", id -> new BlockMaidBed(id, DyeColor.YELLOW));
    public static Block BLUE_MAID_BED = registerBlock("blue_maid_bed", id -> new BlockMaidBed(id, DyeColor.BLUE));
    public static Block GREEN_MAID_BED = registerBlock("green_maid_bed", id -> new BlockMaidBed(id, DyeColor.GREEN));
    public static Block PURPLE_MAID_BED = registerBlock("purple_maid_bed", id -> new BlockMaidBed(id, DyeColor.PURPLE));

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

    public static BlockEntityType<BlockEntityAltar> ALTAR_BE = registerBlockEntityType("altar", BlockEntityAltar::new, ALTAR);
    public static BlockEntityType<BlockEntityStatue> STATUE_BE = registerBlockEntityType("statue", BlockEntityStatue::new, STATUE);
    public static BlockEntityType<BlockEntityGarageKit> GARAGE_KIT_BE = registerBlockEntityType("garage_kit", BlockEntityGarageKit::new, GARAGE_KIT);
    public static BlockEntityType<BlockEntityMaidBeacon> MAID_BEACON_BE = registerBlockEntityType("maid_beacon", BlockEntityMaidBeacon::new, MAID_BEACON);
    public static BlockEntityType<BlockEntityModelSwitcher> MODEL_SWITCHER_BE = registerBlockEntityType("model_switcher", BlockEntityModelSwitcher::new, MODEL_SWITCHER);
    public static BlockEntityType<BlockEntityGomoku> GOMOKU_BE = registerBlockEntityType("gomoku", BlockEntityGomoku::new, GOMOKU);
    public static BlockEntityType<BlockEntityCChess> CCHESS_BE = registerBlockEntityType("cchess", BlockEntityCChess::new, CCHESS);
    public static BlockEntityType<BlockEntityWChess> WCHESS_BE = registerBlockEntityType("wchess", BlockEntityWChess::new, WCHESS);
    public static BlockEntityType<BlockEntityKeyboard> KEYBOARD_BE = registerBlockEntityType("keyboard", BlockEntityKeyboard::new, KEYBOARD);
    public static BlockEntityType<BlockEntityBookshelf> BOOKSHELF_BE = registerBlockEntityType("bookshelf", BlockEntityBookshelf::new, BOOKSHELF);
    public static BlockEntityType<BlockEntityComputer> COMPUTER_BE = registerBlockEntityType("computer", BlockEntityComputer::new, COMPUTER);
    public static BlockEntityType<BlockEntityShrine> SHRINE_BE = registerBlockEntityType("shrine", BlockEntityShrine::new, SHRINE);
    public static BlockEntityType<BlockEntityPicnicMat> PICNIC_MAT_BE = registerBlockEntityType("picnic_mat", BlockEntityPicnicMat::new, PICNIC_MAT);
    public static BlockEntityType<BlockEntitySnackCabinet> SNACK_CABINET_BE = registerBlockEntityType("snack_cabinet", BlockEntitySnackCabinet::new, SNACK_CABINET);
    public static BlockEntityType<BlockEntityMaidBed> MAID_BED_BE = registerBlockEntityType("maid_bed", BlockEntityMaidBed::new,
            PINK_MAID_BED, WHITE_MAID_BED, BLACK_MAID_BED,
            YELLOW_MAID_BED, BLUE_MAID_BED, GREEN_MAID_BED,
            PURPLE_MAID_BED
    );

    private static <B extends Block> B registerBlock(String id, Function<Identifier, ? extends B> func) {
        Identifier loc = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id);
        return Registry.register(BuiltInRegistries.BLOCK, loc, func.apply(loc));
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        var type = FabricBlockEntityTypeBuilder.create(factory, blocks).build();
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), type);
    }
}
