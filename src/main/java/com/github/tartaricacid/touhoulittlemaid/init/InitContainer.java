package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.compat.accessories.menu.CuriosContainer;
import com.github.tartaricacid.touhoulittlemaid.init.registry.CompatRegistry;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack.*;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.config.MaidConfigContainer;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.other.PicnicBasketContainer;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.other.WirelessIOContainer;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.task.AttackTaskConfigContainer;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.task.DefaultMaidTaskConfigContainer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public final class InitContainer {
    public static void init() {
        if (FabricLoader.getInstance().isModLoaded(CompatRegistry.ACCESSORIES)) {
            register("curios_container", CuriosContainer.TYPE);
        }
    }

    public static final MenuType<EmptyBackpackContainer> MAID_EMPTY_BACKPACK_CONTAINER = register("maid_empty_backpack_container", EmptyBackpackContainer.TYPE);
    public static final MenuType<SmallBackpackContainer> MAID_SMALL_BACKPACK_CONTAINER = register("maid_small_backpack_container", SmallBackpackContainer.TYPE);
    public static final MenuType<MiddleBackpackContainer> MAID_MIDDLE_BACKPACK_CONTAINER = register("maid_middle_backpack_container", MiddleBackpackContainer.TYPE);
    public static final MenuType<BigBackpackContainer> MAID_BIG_BACKPACK_CONTAINER = register("maid_big_backpack_container", BigBackpackContainer.TYPE);
    public static final MenuType<CraftingTableBackpackContainer> MAID_CRAFTING_TABLE_BACKPACK_CONTAINER = register("maid_crafting_table_backpack_container", CraftingTableBackpackContainer.TYPE);
    public static final MenuType<EnderChestBackpackContainer> MAID_ENDER_CHEST_CONTAINER = register("maid_ender_chest_container", EnderChestBackpackContainer.TYPE);
    public static final MenuType<FurnaceBackpackContainer> MAID_FURNACE_CONTAINER = register("maid_furnace_container", FurnaceBackpackContainer.TYPE);
    public static final MenuType<TankBackpackContainer> MAID_TANK_CONTAINER = register("maid_tank_container", TankBackpackContainer.TYPE);
    public static final MenuType<BaubleContainer> MAID_BAUBLE_CONTAINER = register("maid_bauble_container", BaubleContainer.TYPE);

    public static final MenuType<MaidConfigContainer> MAID_CONFIG_CONTAINER = register("maid_config_container", MaidConfigContainer.TYPE);
    public static final MenuType<WirelessIOContainer> WIRELESS_IO_CONTAINER = register("wireless_io_container", WirelessIOContainer.TYPE);
    public static final MenuType<PicnicBasketContainer> PICNIC_BASKET_CONTAINER = register("picnic_basket_container", PicnicBasketContainer.TYPE);
    public static final MenuType<DefaultMaidTaskConfigContainer> DEFAULT_MAIK_TASK_CONFIG = register("default_maid_task_config_container", DefaultMaidTaskConfigContainer.TYPE);
    public static final MenuType<AttackTaskConfigContainer> ATTACK_TASK_CONFIG = register("attack_task_config_container", AttackTaskConfigContainer.TYPE);

    private static <T extends MenuType<?>> T register(String id, T type) {
        return Registry.register(BuiltInRegistries.MENU, new ResourceLocation(TouhouLittleMaid.MOD_ID, id), type);
    }
}
