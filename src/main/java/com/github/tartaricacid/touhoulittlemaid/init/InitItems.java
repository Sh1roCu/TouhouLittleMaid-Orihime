package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Function;

public interface InitItems {
    static void init() {

    }

    // 生物蛋
    Item MAID_SPAWN_EGG = register("maid_spawn_egg", id -> new SpawnEggItem(
            new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).spawnEgg(EntityMaid.TYPE)));
    Item FAIRY_SPAWN_EGG = register("fairy_spawn_egg", id -> new SpawnEggItem(
            new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).spawnEgg(EntityFairy.TYPE)));

    // 女仆背包
    Item MAID_BACKPACK_SMALL = register("maid_backpack_small", ItemMaidBackpack::new);
    Item MAID_BACKPACK_MIDDLE = register("maid_backpack_middle", ItemMaidBackpack::new);
    Item MAID_BACKPACK_BIG = register("maid_backpack_big", ItemMaidBackpack::new);

    // 御币
    Item HAKUREI_GOHEI = register("hakurei_gohei", ItemGohei::new);
    Item SANAE_GOHEI = register("sanae_gohei", ItemGohei::new);

    // 有耐久的女仆饰品
    Item EXPLOSION_PROTECT_BAUBLE = register("explosion_protect_bauble", id -> new ItemDamageableBauble(id, 32));
    Item FIRE_PROTECT_BAUBLE = register("fire_protect_bauble", id -> new ItemDamageableBauble(id, 128));
    Item PROJECTILE_PROTECT_BAUBLE = register("projectile_protect_bauble", id -> new ItemDamageableBauble(id, 64));
    Item MAGIC_PROTECT_BAUBLE = register("magic_protect_bauble", id -> new ItemDamageableBauble(id, 128));
    Item FALL_PROTECT_BAUBLE = register("fall_protect_bauble", id -> new ItemDamageableBauble(id, 32));
    Item DROWN_PROTECT_BAUBLE = register("drown_protect_bauble", id -> new ItemDamageableBauble(id, 64));

    // 特殊饰品
    Item ULTRAMARINE_ORB_ELIXIR = register("ultramarine_orb_elixir", id -> new ItemDamageableBauble(id, 6));
    Item NIMBLE_FABRIC = register("nimble_fabric", id -> new ItemDamageableBauble(id, 64));

    // 无耐久的女仆饰品
    Item ITEM_MAGNET_BAUBLE = register("item_magnet_bauble", ItemNormalBauble::new);
    Item MUTE_BAUBLE = register("mute_bauble", ItemNormalBauble::new);

    // 女仆存储道具
    Item SMART_SLAB_INIT = register("smart_slab_init", id -> new ItemSmartSlab(id, ItemSmartSlab.Type.INIT));
    Item SMART_SLAB_EMPTY = register("smart_slab_empty", id -> new ItemSmartSlab(id, ItemSmartSlab.Type.EMPTY));
    Item SMART_SLAB_HAS_MAID = register("smart_slab_has_maid", id -> new ItemSmartSlab(id, ItemSmartSlab.Type.HAS_MAID));

    // 相机、照片与胶片
    Item CAMERA = register("camera", ItemCamera::new);
    Item PHOTO = register("photo", ItemPhoto::new);
    Item FILM = register("film", ItemFilm::new);

    // 寻回道具
    Item RED_FOX_SCROLL = register("red_fox_scroll", ItemFoxScroll::new);
    Item WHITE_FOX_SCROLL = register("white_fox_scroll", ItemFoxScroll::new);
    Item SERVANT_BELL = register("servant_bell", ItemServantBell::new);
    Item TRUMPET = register("trumpet", ItemTrumpet::new);

    // 女仆床
    Item PINK_MAID_BED = register("pink_maid_bed", id -> new ItemMaidBed(id, InitBlocks.PINK_MAID_BED));
    Item WHITE_MAID_BED = register("white_maid_bed", id -> new ItemMaidBed(id, InitBlocks.WHITE_MAID_BED));
    Item BLACK_MAID_BED = register("black_maid_bed", id -> new ItemMaidBed(id, InitBlocks.BLACK_MAID_BED));
    Item YELLOW_MAID_BED = register("yellow_maid_bed", id -> new ItemMaidBed(id, InitBlocks.YELLOW_MAID_BED));
    Item BLUE_MAID_BED = register("blue_maid_bed", id -> new ItemMaidBed(id, InitBlocks.BLUE_MAID_BED));
    Item GREEN_MAID_BED = register("green_maid_bed", id -> new ItemMaidBed(id, InitBlocks.GREEN_MAID_BED));
    Item PURPLE_MAID_BED = register("purple_maid_bed", id -> new ItemMaidBed(id, InitBlocks.PURPLE_MAID_BED));

    // 家具
    Item CHAIR = register("chair", ItemChair::new);
    Item PICNIC_BASKET = register("picnic_basket", ItemPicnicBasket::new);
    Item SNACK_CABINET = register("snack_cabinet", ItemSnackCabinet::new);
    Item SCARECROW = register("scarecrow", ItemScarecrow::new);
    Item MAID_BEACON = register("maid_beacon", ItemMaidBeacon::new);

    // 杂项工具
    Item POWER_POINT = register("power_point", ItemPowerPoint::new);
    Item WIRELESS_IO = register("wireless_io", ItemWirelessIO::new);
    Item KAPPA_COMPASS = register("kappa_compass", ItemKappaCompass::new);
    Item EXTINGUISHER = register("extinguisher", ItemExtinguisher::new);
    Item ENTITY_ID_COPY = register("entity_id_copy", ItemEntityIdCopy::new);
    Item BROOM = register("broom", ItemBroom::new);

    // 模型与展示相关
    Item GARAGE_KIT = register("garage_kit", ItemGarageKit::new);
    Item MODEL_SWITCHER = register("model_switcher", ItemModelSwitcher::new);
    Item CHAIR_SHOW = register("chair_show", ItemChairShow::new);
    Item CHISEL = register("chisel", ItemChisel::new);

    // 调试与辅助工具
    Item FAVORABILITY_TOOL_ADD = register("favorability_tool_add", id -> new ItemFavorabilityTool(id, "add"));
    Item FAVORABILITY_TOOL_REDUCE = register("favorability_tool_reduce", id -> new ItemFavorabilityTool(id, "reduce"));
    Item FAVORABILITY_TOOL_FULL = register("favorability_tool_full", id -> new ItemFavorabilityTool(id, "full"));
    Item OWNER_CONVERSION_TOOL = register("owner_conversion_tool", ItemOwnerConversionTool::new);
    Item SUBSTITUTE_JIZO = register("substitute_jizo", ItemSubstituteJizo::new);

    // 成就图标
    Item CHANGE_CHAIR_MODEL = register("change_chair_model", ItemAdvancementIcon::new);
    Item CHANGE_MAID_MODEL = register("change_maid_model", ItemAdvancementIcon::new);
    Item MAID_100_HEALTHY = register("maid_100_healthy", ItemAdvancementIcon::new);
    Item KILL_100 = register("kill_100", ItemAdvancementIcon::new);
    Item KILL_SLIME_300 = register("kill_slime_300", ItemAdvancementIcon::new);
    Item ALL_NETHERITE_EQUIPMENT = register("all_netherite_equipment", ItemAdvancementIcon::new);
    Item KILL_WITHER = register("kill_wither", ItemAdvancementIcon::new);
    Item KILL_DRAGON = register("kill_dragon", ItemAdvancementIcon::new);
    Item TACZ_GUN_ICON = register("tacz_gun_icon", ItemAdvancementIcon::new);

    // 棋类
    Item GOMOKU = register("gomoku", id ->
            new BlockItem(InitBlocks.GOMOKU, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id))
                    .overrideDescription("block.touhou_little_maid.gomoku"))
    );
    Item CCHESS = register("cchess", id ->
            new BlockItem(InitBlocks.CCHESS, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id))
                    .overrideDescription("block.touhou_little_maid.cchess"))
    );
    Item WCHESS = register("wchess", id ->
            new BlockItem(InitBlocks.WCHESS, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id))
                    .overrideDescription("block.touhou_little_maid.wchess"))
    );

    // 娱乐方块
    Item KEYBOARD = register("keyboard", id ->
            new BlockItem(InitBlocks.KEYBOARD, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id))
                    .overrideDescription("block.touhou_little_maid.keyboard"))
    );
    Item BOOKSHELF = register("bookshelf", id ->
            new BlockItem(InitBlocks.BOOKSHELF, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id))
                    .overrideDescription("block.touhou_little_maid.bookshelf"))
    );
    Item COMPUTER = register("computer", id ->
            new BlockItem(InitBlocks.COMPUTER, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id))
                    .overrideDescription("block.touhou_little_maid.computer"))
    );

    // 神龛
    Item SHRINE = register("shrine", id ->
            new BlockItem(InitBlocks.SHRINE, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id))
                    .rarity(Rarity.RARE)
                    .overrideDescription("block.touhou_little_maid.shrine"))
    );

    private static Item register(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), item);
    }

    private static Item register(String id, Function<Identifier, Item> func) {
        Identifier loc = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id);
        return Registry.register(BuiltInRegistries.ITEM, loc, func.apply(loc));
    }
}