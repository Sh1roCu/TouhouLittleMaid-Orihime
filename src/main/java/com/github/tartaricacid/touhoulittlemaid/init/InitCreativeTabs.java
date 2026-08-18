package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.block.BlockGarageKit;
import com.github.tartaricacid.touhoulittlemaid.datagen.EnchantmentKeys;
import com.github.tartaricacid.touhoulittlemaid.item.ItemChair;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import vazkii.patchouli.common.item.ItemModBook;

import java.util.Optional;

import static com.github.tartaricacid.touhoulittlemaid.init.InitItems.*;

public class InitCreativeTabs {
    public static void init() {

    }

    public static CreativeModeTab MAIN_TAB = register("main", FabricCreativeModeTab.builder()
            .title(Component.translatable("item_group.touhou_little_maid.main"))
            .icon(HAKUREI_GOHEI::getDefaultInstance)
            .displayItems((par, output) -> {
                if (FabricLoader.getInstance().isModLoaded("patchouli")) {
                    Identifier book = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "memorizable_gensokyo");
                    output.accept(ItemModBook.forBook(book).create());
                }
                output.accept(MAID_SPAWN_EGG);
                output.accept(FAIRY_SPAWN_EGG);
                output.accept(HAKUREI_GOHEI);
                output.accept(SANAE_GOHEI);
                output.accept(POWER_POINT);
                output.accept(SMART_SLAB_EMPTY);
                output.accept(SMART_SLAB_INIT);
                output.accept(MAID_BACKPACK_SMALL);
                output.accept(MAID_BACKPACK_MIDDLE);
                output.accept(MAID_BACKPACK_BIG);
                output.accept(SUBSTITUTE_JIZO);
                output.accept(ULTRAMARINE_ORB_ELIXIR);
                output.accept(EXPLOSION_PROTECT_BAUBLE);
                output.accept(FIRE_PROTECT_BAUBLE);
                output.accept(PROJECTILE_PROTECT_BAUBLE);
                output.accept(MAGIC_PROTECT_BAUBLE);
                output.accept(FALL_PROTECT_BAUBLE);
                output.accept(DROWN_PROTECT_BAUBLE);
                output.accept(NIMBLE_FABRIC);
                output.accept(ITEM_MAGNET_BAUBLE);
                output.accept(MUTE_BAUBLE);
                output.accept(WIRELESS_IO);
                output.accept(TRUMPET);
                output.accept(RED_FOX_SCROLL);
                output.accept(WHITE_FOX_SCROLL);
                output.accept(SERVANT_BELL);
                output.accept(KAPPA_COMPASS);
                output.accept(EXTINGUISHER);
                output.accept(GOMOKU);
                output.accept(CCHESS);
                output.accept(WCHESS);
                output.accept(KEYBOARD);
                output.accept(BOOKSHELF);
                output.accept(COMPUTER);
                output.accept(FAVORABILITY_TOOL_ADD);
                output.accept(FAVORABILITY_TOOL_REDUCE);
                output.accept(FAVORABILITY_TOOL_FULL);
                output.accept(CAMERA);
                output.accept(PHOTO);
                output.accept(FILM);
                output.accept(CHISEL);
                output.accept(PINK_MAID_BED);
                output.accept(WHITE_MAID_BED);
                output.accept(BLACK_MAID_BED);
                output.accept(YELLOW_MAID_BED);
                output.accept(BLUE_MAID_BED);
                output.accept(GREEN_MAID_BED);
                output.accept(PURPLE_MAID_BED);
                output.accept(PICNIC_BASKET);
                output.accept(MAID_BEACON);
                output.accept(SNACK_CABINET);
                output.accept(SHRINE);
                output.accept(MODEL_SWITCHER);
                output.accept(CHAIR_SHOW);
                output.accept(BROOM);
                output.accept(SCARECROW);
                output.accept(ENTITY_ID_COPY);
                output.accept(OWNER_CONVERSION_TOOL);
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                    //ItemEntityPlaceholder.fillItemCategory(output);
                }
                par.holders().lookup(Registries.ENCHANTMENT).ifPresent(reg -> {
                    addEnchantmentBook(reg.get(EnchantmentKeys.IMPEDING), output);
                    addEnchantmentBook(reg.get(EnchantmentKeys.SPEEDY), output);
                    addEnchantmentBook(reg.get(EnchantmentKeys.ENDERS_ENDER), output);
                });
            }).build());

    public static CreativeModeTab GARAGE_KIT_TAB = register("chair", FabricCreativeModeTab.builder()
            .title(Component.translatable("item_group.touhou_little_maid.chair"))
            .icon(CHAIR::getDefaultInstance)
            .displayItems((par, output) -> {
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                    ItemChair.fillItemCategory(output);
                }
            }).build());

    public static CreativeModeTab CHAIR_TAB = register("garage_kit", FabricCreativeModeTab.builder()
            .title(Component.translatable("item_group.touhou_little_maid.garage_kit"))
            .icon(GARAGE_KIT::getDefaultInstance)
            .displayItems((par, output) -> {
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                    BlockGarageKit.fillItemCategory(output);
                }
            }).build());

    private static void addEnchantmentBook(Optional<Holder.Reference<Enchantment>> holder, CreativeModeTab.Output output) {
        holder.ifPresent(ref -> {
            EnchantmentInstance instance = new EnchantmentInstance(ref, ref.value().getMaxLevel());
            output.accept(EnchantmentHelper.createBook(instance));
        });
    }

    private static CreativeModeTab register(String id, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), tab);
    }
}
