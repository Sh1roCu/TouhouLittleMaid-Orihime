package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TagItem extends FabricTagsProvider.ItemTagsProvider {
    /**
     * 能够附魔御币专属附魔的物品
     */
    public static final TagKey<Item> GOHEI_ENCHANTABLE = createTagKey("gohei_enchantable");
    /**
     * 女仆可种植的种子
     * <p>
     * 默认已经把所有带有 <code>#forge:villager_plantable_seeds</code> 和 <code>#forge:seeds</code> 标签的物品加入其中了
     */
    public static final TagKey<Item> MAID_PLANTABLE_SEEDS = createTagKey("maid_plantable_seeds");
    /**
     * 能够驯服女仆的物品
     * <p>
     * 默认已经把所有带有 <code>#forge:cakes</code> 和 <code>#c:cakes</code> 标签的物品加入其中了
     */
    public static final TagKey<Item> MAID_TAMED_ITEM = createTagKey("maid_tamed_item");

    /**
     * 能够吸引女仆的物品
     * <p>
     * 默认已经把所有带有 <code>#forge:cakes</code> 和 <code>#c:cakes</code> 标签的物品加入其中了
     */
    public static final TagKey<Item> MAID_TEMPTATION_ITEM = createTagKey("maid_temptation_item");

    /**
     * 物品拥有经验修补后，女仆在吸收经验或者 P 点时能够进行修复；
     * <p>
     * 但是部分物品不能这么做，可将其加入此 tag 下
     */
    public static final TagKey<Item> MAID_MENDING_BLOCKLIST_ITEM = createTagKey("maid_mending_blocklist_item");

    /**
     * 女仆和玩家类似，在穿戴拥有消失诅咒附魔的装备（或者饰品）后死亡，其对应的物品会直接消失；
     * <p>
     * 但是部分物品不能这么做，可将其加入此 tag 下
     */
    public static final TagKey<Item> MAID_VANISHING_BLOCKLIST_ITEM = createTagKey("maid_vanishing_blocklist_item");

    /**
     * 女仆进食黑名单，与配置文件协同作用，方便拓展兼容
     * <p>
     * 全局的，适用于工作餐、回血餐和家庭餐
     */
    public static final TagKey<Item> MAID_EAT_BLOCKLIST_ITEM = createTagKey("maid_eat_blocklist_item");

    /**
     * 所有颜色的女仆床物品
     */
    public static final TagKey<Item> MAID_BED = createTagKey("maid_bed");

    public TagItem(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    public static TagKey<Item> createTagKey(String name) {
        return TagKey.create(Registries.ITEM, IdentifierUtil.modLoc(name));
    }

    public static TagKey<Item> createTagKey(Identifier resourceLocation) {
        return TagKey.create(Registries.ITEM, resourceLocation);
    }

    public static ResourceKey<Item> createResourceKey(Identifier resourceLocation) {
        return ResourceKey.create(Registries.ITEM, resourceLocation);
    }

    private static ResourceKey<Item> itemKey(Item item) {
        return item.builtInRegistryHolder().key();
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.builder(GOHEI_ENCHANTABLE).add(itemKey(InitItems.HAKUREI_GOHEI));
        this.builder(GOHEI_ENCHANTABLE).add(itemKey(InitItems.SANAE_GOHEI));

        this.builder(ItemTags.DURABILITY_ENCHANTABLE)
                .add(itemKey(InitItems.HAKUREI_GOHEI))
                .add(itemKey(InitItems.SANAE_GOHEI))
                .add(itemKey(InitItems.ULTRAMARINE_ORB_ELIXIR))
                .add(itemKey(InitItems.EXPLOSION_PROTECT_BAUBLE))
                .add(itemKey(InitItems.FIRE_PROTECT_BAUBLE))
                .add(itemKey(InitItems.PROJECTILE_PROTECT_BAUBLE))
                .add(itemKey(InitItems.MAGIC_PROTECT_BAUBLE))
                .add(itemKey(InitItems.FALL_PROTECT_BAUBLE))
                .add(itemKey(InitItems.DROWN_PROTECT_BAUBLE))
                .add(itemKey(InitItems.NIMBLE_FABRIC));

        this.builder(MAID_PLANTABLE_SEEDS)
                .forceAddTag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                .forceAddTag(ConventionalItemTags.SEEDS)
                .addOptionalTag(createTagKey(Identifier.parse("kaleidoscope_cookery:cookery_mod_seeds")));
        this.builder(MAID_PLANTABLE_SEEDS).add(BlockItemIds.NETHER_WART);

        this.addCakeItems(MAID_TAMED_ITEM);
        this.addCakeItems(MAID_TEMPTATION_ITEM);

        this.builder(MAID_MENDING_BLOCKLIST_ITEM).add(itemKey(InitItems.ULTRAMARINE_ORB_ELIXIR));
        this.builder(MAID_VANISHING_BLOCKLIST_ITEM).add(itemKey(InitItems.ULTRAMARINE_ORB_ELIXIR));

        // 森罗物语辣椒
        this.builder(MAID_EAT_BLOCKLIST_ITEM)
                .addOptional(createResourceKey(Identifier.parse("kaleidoscope_cookery:red_chili")))
                .addOptional(createResourceKey(Identifier.parse("kaleidoscope_cookery:green_chili")));

        this.builder(MAID_BED)
                .add(itemKey(InitItems.PINK_MAID_BED))
                .add(itemKey(InitItems.WHITE_MAID_BED))
                .add(itemKey(InitItems.BLACK_MAID_BED))
                .add(itemKey(InitItems.YELLOW_MAID_BED))
                .add(itemKey(InitItems.BLUE_MAID_BED))
                .add(itemKey(InitItems.GREEN_MAID_BED))
                .add(itemKey(InitItems.PURPLE_MAID_BED));
    }

    private void addCakeItems(TagKey<Item> tagKey) {
        this.builder(tagKey)
                .add(BlockItemIds.CAKE)
                .addOptionalTag(createTagKey(Identifier.parse("forge:cakes")))
                .addOptionalTag(createTagKey(Identifier.parse("c:cakes")))
                .addOptionalTag(createTagKey(Identifier.parse("jmc:cakes")));
        this.builder(tagKey)
                .addOptional(createResourceKey(Identifier.parse("kawaiidishes:cheese_cake")))
                .addOptional(createResourceKey(Identifier.parse("kawaiidishes:honey_cheese_cake")))
                .addOptional(createResourceKey(Identifier.parse("kawaiidishes:chocolate_cheese_cake")))
                .addOptional(createResourceKey(Identifier.parse("kawaiidishes:piece_of_cake")))
                .addOptional(createResourceKey(Identifier.parse("kawaiidishes:piece_of_cheesecake")))
                .addOptional(createResourceKey(Identifier.parse("kawaiidishes:piece_of_chocolate_cheesecake")))
                .addOptional(createResourceKey(Identifier.parse("kawaiidishes:piece_of_honey_cheesecake")));
    }
}
