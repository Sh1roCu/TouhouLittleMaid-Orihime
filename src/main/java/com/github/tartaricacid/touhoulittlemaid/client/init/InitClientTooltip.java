package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.client.tooltip.ClientBoardStateTooltip;
import com.github.tartaricacid.touhoulittlemaid.client.tooltip.ClientItemContainerTooltip;
import com.github.tartaricacid.touhoulittlemaid.client.tooltip.ClientMaidTooltip;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.BoardStateTooltip;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.ItemContainerTooltip;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.ItemMaidTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

@Environment(EnvType.CLIENT)
public class InitClientTooltip {
    public static ClientTooltipComponent onRegisterClientTooltip(TooltipComponent component) {
        if (component instanceof ItemMaidTooltip itemMaidTooltip) {
            return new ClientMaidTooltip(itemMaidTooltip);
        }
        if (component instanceof ItemContainerTooltip itemContainerTooltip) {
            return new ClientItemContainerTooltip(itemContainerTooltip);
        }
        if (component instanceof BoardStateTooltip boardStateTooltip) {
            return new ClientBoardStateTooltip(boardStateTooltip);
        }
        return null;
    }
}
