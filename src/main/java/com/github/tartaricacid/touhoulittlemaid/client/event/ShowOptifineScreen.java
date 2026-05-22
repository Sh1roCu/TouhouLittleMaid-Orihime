package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.client.gui.mod.OptifineScreen;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MiscConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

@Environment(EnvType.CLIENT)
public final class ShowOptifineScreen {
    private static boolean optifinePresent = false;
    private static boolean firstTitleScreenShown = false;

    public static void showOptifineWarning(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }
        if (!MiscConfig.CLOSE_OPTIFINE_WARNING.get() && optifinePresent) {
            Minecraft.getInstance().setScreen(new OptifineScreen(screen));
        }
        firstTitleScreenShown = true;
    }

    public static void checkOptifineIsLoaded() {
        try {
            Class.forName("net.optifine.Config");
            optifinePresent = true;
        } catch (ClassNotFoundException e) {
            optifinePresent = false;
        }
    }
}
