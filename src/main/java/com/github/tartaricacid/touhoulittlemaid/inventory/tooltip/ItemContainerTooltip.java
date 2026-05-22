package com.github.tartaricacid.touhoulittlemaid.inventory.tooltip;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record ItemContainerTooltip(IItemHandler handler) implements TooltipComponent {
}
