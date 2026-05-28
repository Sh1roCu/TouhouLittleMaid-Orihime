package com.github.tartaricacid.touhoulittlemaid;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.compat.aquaculture.AquacultureCompat;
import com.github.tartaricacid.touhoulittlemaid.entity.info.CommonDefaultPack;
import com.github.tartaricacid.touhoulittlemaid.init.*;
import com.github.tartaricacid.touhoulittlemaid.init.registry.CommandRegistry;
import com.github.tartaricacid.touhoulittlemaid.network.NetworkHandler;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class TouhouLittleMaid {
    public static final String MOD_ID = "touhou_little_maid";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static List<ILittleMaid> EXTENSIONS = Lists.newArrayList();
    public static boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

    public static void commonSetup() {
        initRegister();

        CommonDefaultPack.initCommonDefaultPack();
        AquacultureCompat.init();
    }

    private static void initRegister() {
        InitEntities.init();
        InitAttribute.init();
        InitBlocks.init();
        InitItems.init();
        InitEnchantments.init();
        InitCreativeTabs.init();
        InitContainer.init();
        InitSounds.init();
        InitRecipes.init();
        InitCommand.init();
        InitPoi.init();
        InitPaintingVariants.init();
        InitTrigger.init();
        InitDataAttachment.init();
        InitLootModifier.init();

        NetworkHandler.registerC2SPackets();
        InitCapabilities.registerGenericItemHandlers();

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, context, selection) ->
                        CommandRegistry.onServerStaring(dispatcher));
    }
}
