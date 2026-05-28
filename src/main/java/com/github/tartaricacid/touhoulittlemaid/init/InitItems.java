package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.TypedEntityData;

import java.util.function.Function;

public final class InitItems {
    public static void init() {

    }

    public static Item MAID_BACKPACK_SMALL = register("maid_backpack_small", ItemMaidBackpack::new);
    public static Item MAID_BACKPACK_MIDDLE = register("maid_backpack_middle", ItemMaidBackpack::new);
    public static Item MAID_BACKPACK_BIG = register("maid_backpack_big", ItemMaidBackpack::new);
    public static Item CHAIR = register("chair", ItemChair::new);
    public static Item HAKUREI_GOHEI = register("hakurei_gohei", ItemHakureiGohei::new);
    public static Item SANAE_GOHEI = register("sanae_gohei", ItemHakureiGohei::new);
    public static Item MAID_BED = register("maid_bed", ItemMaidBed::new);
    public static Item EXTINGUISHER = register("extinguisher", ItemExtinguisher::new);
    public static Item ULTRAMARINE_ORB_ELIXIR = register("ultramarine_orb_elixir", id -> new ItemDamageableBauble(id, 6));
    public static Item EXPLOSION_PROTECT_BAUBLE = register("explosion_protect_bauble", id -> new ItemDamageableBauble(id, 32));
    public static Item FIRE_PROTECT_BAUBLE = register("fire_protect_bauble", id -> new ItemDamageableBauble(id, 128));
    public static Item PROJECTILE_PROTECT_BAUBLE = register("projectile_protect_bauble", id -> new ItemDamageableBauble(id, 64));
    public static Item MAGIC_PROTECT_BAUBLE = register("magic_protect_bauble", id -> new ItemDamageableBauble(id, 128));
    public static Item FALL_PROTECT_BAUBLE = register("fall_protect_bauble", id -> new ItemDamageableBauble(id, 32));
    public static Item DROWN_PROTECT_BAUBLE = register("drown_protect_bauble", id -> new ItemDamageableBauble(id, 64));
    public static Item NIMBLE_FABRIC = register("nimble_fabric", id -> new ItemDamageableBauble(id, 64));
    public static Item ITEM_MAGNET_BAUBLE = register("item_magnet_bauble", ItemNormalBauble::new);
    public static Item MUTE_BAUBLE = register("mute_bauble", ItemNormalBauble::new);
    //public static Item ENTITY_PLACEHOLDER = register("entity_placeholder", ItemEntityPlaceholder::new);
    public static Item SUBSTITUTE_JIZO = register("substitute_jizo", ItemSubstituteJizo::new);
    public static Item POWER_POINT = register("power_point", ItemPowerPoint::new);
    public static Item CAMERA = register("camera", ItemCamera::new);
    public static Item PHOTO = register("photo", ItemPhoto::new);
    public static Item FILM = register("film", ItemFilm::new);
    public static Item CHISEL = register("chisel", ItemChisel::new);
    public static Item GARAGE_KIT = register("garage_kit", ItemGarageKit::new);
    public static Item SMART_SLAB_INIT = register("smart_slab_init", id -> new ItemSmartSlab(id, ItemSmartSlab.Type.INIT));
    public static Item SMART_SLAB_EMPTY = register("smart_slab_empty", id -> new ItemSmartSlab(id, ItemSmartSlab.Type.EMPTY));
    public static Item SMART_SLAB_HAS_MAID = register("smart_slab_has_maid", id -> new ItemSmartSlab(id, ItemSmartSlab.Type.HAS_MAID));
    public static Item TRUMPET = register("trumpet", ItemTrumpet::new);
    public static Item WIRELESS_IO = register("wireless_io", ItemWirelessIO::new);
    public static Item MAID_BEACON = register("maid_beacon", ItemMaidBeacon::new);
    public static Item MODEL_SWITCHER = register("model_switcher", ItemModelSwitcher::new);
    public static Item CHAIR_SHOW = register("chair_show", ItemChairShow::new);

    public static Item GOMOKU = register("gomoku", id ->
            new BlockItem(InitBlocks.GOMOKU, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static Item CCHESS = register("cchess", id ->
            new BlockItem(InitBlocks.CCHESS, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static Item WCHESS = register("wchess", id ->
            new BlockItem(InitBlocks.WCHESS, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static Item RED_FOX_SCROLL = register("red_fox_scroll", ItemFoxScroll::new);
    public static Item WHITE_FOX_SCROLL = register("white_fox_scroll", ItemFoxScroll::new);

    public static Item KEYBOARD = register("keyboard", id ->
            new BlockItem(InitBlocks.KEYBOARD, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static Item BOOKSHELF = register("bookshelf", id ->
            new BlockItem(InitBlocks.BOOKSHELF, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    public static Item COMPUTER = register("computer", id ->
            new BlockItem(InitBlocks.COMPUTER, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static Item FAVORABILITY_TOOL_ADD = register("favorability_tool_add", id -> new ItemFavorabilityTool(id, "add"));
    public static Item FAVORABILITY_TOOL_REDUCE = register("favorability_tool_reduce", id -> new ItemFavorabilityTool(id, "reduce"));
    public static Item FAVORABILITY_TOOL_FULL = register("favorability_tool_full", id -> new ItemFavorabilityTool(id, "full"));

    public static Item SHRINE = register("shrine", id ->
            new BlockItem(InitBlocks.SHRINE, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).rarity(Rarity.RARE)));

    public static Item KAPPA_COMPASS = register("kappa_compass", ItemKappaCompass::new);
    public static Item BROOM = register("broom", ItemBroom::new);
    public static Item PICNIC_BASKET = register("picnic_basket", id -> new ItemPicnicBasket(id, InitBlocks.PICNIC_MAT));
    public static Item SCARECROW = register("scarecrow", ItemScarecrow::new);
    public static Item SERVANT_BELL = register("servant_bell", ItemServantBell::new);
    public static Item ENTITY_ID_COPY = register("entity_id_copy", ItemEntityIdCopy::new);

    public static Item OWNER_CONVERSION_TOOL = register("owner_conversion_tool", id ->
            new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).setId(ResourceKey.create(Registries.ITEM, id))));

    public static Item SNACK_CABINET = register("snack_cabinet", ItemSnackCabinet::new);

    public static Item MAID_SPAWN_EGG = register("maid_spawn_egg", id ->
            new SpawnEggItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))
                    .component(DataComponents.ENTITY_DATA, TypedEntityData.of(EntityMaid.TYPE, new CompoundTag()))));

    public static Item FAIRY_SPAWN_EGG = register("fairy_spawn_egg", ItemFairySpawnEgg::new);

    // public static final Identifier MEMORIZABLE_GENSOKYO_LOCATION = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "memorizable_gensokyo");

    // 成就图标
    public static Item CHANGE_CHAIR_MODEL = register("change_chair_model", ItemAdvancementIcon::new);
    public static Item CHANGE_MAID_MODEL = register("change_maid_model", ItemAdvancementIcon::new);
    public static Item MAID_100_HEALTHY = register("maid_100_healthy", ItemAdvancementIcon::new);
    public static Item KILL_100 = register("kill_100", ItemAdvancementIcon::new);
    public static Item KILL_SLIME_300 = register("kill_slime_300", ItemAdvancementIcon::new);
    public static Item ALL_NETHERITE_EQUIPMENT = register("all_netherite_equipment", ItemAdvancementIcon::new);
    public static Item KILL_WITHER = register("kill_wither", ItemAdvancementIcon::new);
    public static Item KILL_DRAGON = register("kill_dragon", ItemAdvancementIcon::new);
    public static Item TACZ_GUN_ICON = register("tacz_gun_icon", ItemAdvancementIcon::new);

    private static Item register(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), item);
    }

    private static Item register(String id, Function<Identifier, Item> func) {
        Identifier loc = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id);
        return Registry.register(BuiltInRegistries.ITEM, loc, func.apply(loc));
    }
}