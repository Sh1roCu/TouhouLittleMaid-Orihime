package com.github.tartaricacid.touhoulittlemaid.inventory.tooltip;

import cn.sh1rocu.touhoulittlemaid.util.transfer.ResourceHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record ItemContainerTooltip(ResourceHandler<ItemVariant> handler) implements TooltipComponent {
}
