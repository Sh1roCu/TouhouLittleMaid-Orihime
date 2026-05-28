package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.backpack.*;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.config.MaidConfigContainerGui;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.task.AttackTaskConfigGui;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.task.DefaultMaidTaskConfigGui;
import com.github.tartaricacid.touhoulittlemaid.client.gui.item.PicnicBasketContainerScreen;
import com.github.tartaricacid.touhoulittlemaid.client.gui.item.WirelessIOContainerGui;
import com.github.tartaricacid.touhoulittlemaid.compat.curios.CuriosCompat;
import com.github.tartaricacid.touhoulittlemaid.init.InitContainer;
import com.github.tartaricacid.touhoulittlemaid.init.registry.CompatRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.MenuScreens;

public final class InitContainerGui {
    public static void clientSetup() {
        MenuScreens.register(InitContainer.MAID_EMPTY_BACKPACK_CONTAINER, EmptyBackpackContainerScreen::new);
        MenuScreens.register(InitContainer.MAID_SMALL_BACKPACK_CONTAINER, SmallBackpackContainerScreen::new);
        MenuScreens.register(InitContainer.MAID_MIDDLE_BACKPACK_CONTAINER, MiddleBackpackContainerScreen::new);
        MenuScreens.register(InitContainer.MAID_BIG_BACKPACK_CONTAINER, BigBackpackContainerScreen::new);

        MenuScreens.register(InitContainer.MAID_BAUBLE_CONTAINER, BaubleContainerScreen::new);
        MenuScreens.register(InitContainer.MAID_CONFIG_CONTAINER, MaidConfigContainerGui::new);
        MenuScreens.register(InitContainer.WIRELESS_IO_CONTAINER, WirelessIOContainerGui::new);
        MenuScreens.register(InitContainer.PICNIC_BASKET_CONTAINER, PicnicBasketContainerScreen::new);

        MenuScreens.register(InitContainer.DEFAULT_MAIK_TASK_CONFIG, DefaultMaidTaskConfigGui::new);
        MenuScreens.register(InitContainer.ATTACK_TASK_CONFIG, AttackTaskConfigGui::new);

        // trinkets 兼容
        if (FabricLoader.getInstance().isModLoaded(CompatRegistry.TRINKETS)) {
            CuriosCompat.registerScreen();
        }
    }
}
