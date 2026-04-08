package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;

public final class InitItems {
    public static void init() {

    }

    public static Item MAID_BACKPACK_SMALL = register("maid_backpack_small", new ItemMaidBackpack());
    public static Item MAID_BACKPACK_MIDDLE = register("maid_backpack_middle", new ItemMaidBackpack());
    public static Item MAID_BACKPACK_BIG = register("maid_backpack_big", new ItemMaidBackpack());
    public static Item CRAFTING_TABLE_BACKPACK = register("crafting_table_backpack", new ItemMaidBackpack());
    public static Item ENDER_CHEST_BACKPACK = register("ender_chest_backpack", new ItemMaidBackpack());
    public static Item FURNACE_BACKPACK = register("furnace_backpack", new ItemMaidBackpack());
    public static Item TANK_BACKPACK = register("tank_backpack", new ItemTankBackpack());
    public static Item CHAIR = register("chair", new ItemChair());
    public static Item HAKUREI_GOHEI = register("hakurei_gohei", new ItemHakureiGohei());
    public static Item SANAE_GOHEI = register("sanae_gohei", new ItemHakureiGohei());
    public static Item MAID_BED = register("maid_bed", new ItemMaidBed());
    public static Item EXTINGUISHER = register("extinguisher", new ItemExtinguisher());
    public static Item ULTRAMARINE_ORB_ELIXIR = register("ultramarine_orb_elixir", new ItemDamageableBauble(6));
    public static Item EXPLOSION_PROTECT_BAUBLE = register("explosion_protect_bauble", new ItemDamageableBauble(32));
    public static Item FIRE_PROTECT_BAUBLE = register("fire_protect_bauble", new ItemDamageableBauble(128));
    public static Item PROJECTILE_PROTECT_BAUBLE = register("projectile_protect_bauble", new ItemDamageableBauble(64));
    public static Item MAGIC_PROTECT_BAUBLE = register("magic_protect_bauble", new ItemDamageableBauble(128));
    public static Item FALL_PROTECT_BAUBLE = register("fall_protect_bauble", new ItemDamageableBauble(32));
    public static Item DROWN_PROTECT_BAUBLE = register("drown_protect_bauble", new ItemDamageableBauble(64));
    public static Item NIMBLE_FABRIC = register("nimble_fabric", new ItemDamageableBauble(64));
    public static Item ITEM_MAGNET_BAUBLE = register("item_magnet_bauble", new ItemNormalBauble());
    public static Item MUTE_BAUBLE = register("mute_bauble", new ItemNormalBauble());
    public static Item ENTITY_PLACEHOLDER = register("entity_placeholder", new ItemEntityPlaceholder());
    public static Item SUBSTITUTE_JIZO = register("substitute_jizo", new ItemSubstituteJizo());
    public static Item POWER_POINT = register("power_point", new ItemPowerPoint());
    public static Item CAMERA = register("camera", new ItemCamera());
    public static Item PHOTO = register("photo", new ItemPhoto());
    public static Item FILM = register("film", new ItemFilm());
    public static Item CHISEL = register("chisel", new ItemChisel());
    public static Item GARAGE_KIT = register("garage_kit", new ItemGarageKit());
    public static Item SMART_SLAB_INIT = register("smart_slab_init", new ItemSmartSlab(ItemSmartSlab.Type.INIT));
    public static Item SMART_SLAB_EMPTY = register("smart_slab_empty", new ItemSmartSlab(ItemSmartSlab.Type.EMPTY));
    public static Item SMART_SLAB_HAS_MAID = register("smart_slab_has_maid", new ItemSmartSlab(ItemSmartSlab.Type.HAS_MAID));
    public static Item TRUMPET = register("trumpet", new ItemTrumpet());
    public static Item WIRELESS_IO = register("wireless_io", new ItemWirelessIO());
    public static Item MAID_BEACON = register("maid_beacon", new ItemMaidBeacon());
    public static Item MODEL_SWITCHER = register("model_switcher", new ItemModelSwitcher());
    public static Item CHAIR_SHOW = register("chair_show", new ItemChairShow());
    public static Item GOMOKU = register("gomoku", new BlockItem(InitBlocks.GOMOKU, new Item.Properties()));
    public static Item CCHESS = register("cchess", new BlockItem(InitBlocks.CCHESS, new Item.Properties()));
    public static Item WCHESS = register("wchess", new BlockItem(InitBlocks.WCHESS, new Item.Properties()));
    public static Item RED_FOX_SCROLL = register("red_fox_scroll", new ItemFoxScroll());
    public static Item WHITE_FOX_SCROLL = register("white_fox_scroll", new ItemFoxScroll());
    public static Item KEYBOARD = register("keyboard", new BlockItem(InitBlocks.KEYBOARD, new Item.Properties()));
    public static Item BOOKSHELF = register("bookshelf", new BlockItem(InitBlocks.BOOKSHELF, new Item.Properties()));
    public static Item COMPUTER = register("computer", new BlockItem(InitBlocks.COMPUTER, new Item.Properties()));
    public static Item FAVORABILITY_TOOL_ADD = register("favorability_tool_add", new ItemFavorabilityTool("add"));
    public static Item FAVORABILITY_TOOL_REDUCE = register("favorability_tool_reduce", new ItemFavorabilityTool("reduce"));
    public static Item FAVORABILITY_TOOL_FULL = register("favorability_tool_full", new ItemFavorabilityTool("full"));
    public static Item SHRINE = register("shrine", new BlockItem(InitBlocks.SHRINE, new Item.Properties().rarity(Rarity.RARE)));
    public static Item KAPPA_COMPASS = register("kappa_compass", new ItemKappaCompass());
    public static Item BROOM = register("broom", new ItemBroom());
    public static Item PICNIC_BASKET = register("picnic_basket", new ItemPicnicBasket(InitBlocks.PICNIC_MAT));
    public static Item SCARECROW = register("scarecrow", new BlockItem(InitBlocks.SCARECROW, new Item.Properties()));
    public static Item SERVANT_BELL = register("servant_bell", new ItemServantBell());
    public static Item ENTITY_ID_COPY = register("entity_id_copy", new ItemEntityIdCopy());
    public static Item OWNER_CONVERSION_TOOL = register("owner_conversion_tool", new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static Item GOMOKU_BOARD_STATE = register("gomoku_board_state", new ItemBoardState());
    public static Item CCHESS_BOARD_STATE = register("cchess_board_state", new ItemBoardState());
    public static Item WCHESS_BOARD_STATE = register("wchess_board_state", new ItemBoardState());
    public static Item SNACK_CABINET = register("snack_cabinet", new BlockItem(InitBlocks.SNACK_CABINET, new Item.Properties()));
    @SuppressWarnings("deprecation")
    public static Item MONSTER_LIST = register("monster_list", new ItemMonsterList());

    public static Item MAID_SPAWN_EGG = register("maid_spawn_egg", new SpawnEggItem(EntityMaid.TYPE, 0xffffff, 0xffffff, new Item.Properties()));
    public static Item FAIRY_SPAWN_EGG = register("fairy_spawn_egg", new ItemFairySpawnEgg());

    public static final ResourceLocation MEMORIZABLE_GENSOKYO_LOCATION = new ResourceLocation(TouhouLittleMaid.MOD_ID, "memorizable_gensokyo");

    // 成就图标
    public static Item CHANGE_CHAIR_MODEL = register("change_chair_model", new ItemAdvancementIcon());
    public static Item CHANGE_MAID_MODEL = register("change_maid_model", new ItemAdvancementIcon());
    public static Item MAID_100_HEALTHY = register("maid_100_healthy", new ItemAdvancementIcon());
    public static Item KILL_100 = register("kill_100", new ItemAdvancementIcon());
    public static Item KILL_SLIME_300 = register("kill_slime_300", new ItemAdvancementIcon());
    public static Item ALL_NETHERITE_EQUIPMENT = register("all_netherite_equipment", new ItemAdvancementIcon());
    public static Item KILL_WITHER = register("kill_wither", new ItemAdvancementIcon());
    public static Item KILL_DRAGON = register("kill_dragon", new ItemAdvancementIcon());
    public static Item TACZ_GUN_ICON = register("tacz_gun_icon", new ItemAdvancementIcon());

    private static Item register(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), item);
    }
}