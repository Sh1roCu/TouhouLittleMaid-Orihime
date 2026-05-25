package com.github.tartaricacid.touhoulittlemaid.compat.ipn;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.util.GuiTools;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public class SortButtonScreen {
    private static final Identifier SIDE = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "textures/gui/maid_gui_side.png");
    private static final String IPN_ID = "inventoryprofilesnext";

    public static void renderBackground(GuiGraphicsExtractor graphics, int x, int y) {
        if (FabricLoader.getInstance().isModLoaded(IPN_ID)) {
            GuiTools.blit(graphics, SIDE, x, y, 0, 73, 17, 48);
        }
    }
}
