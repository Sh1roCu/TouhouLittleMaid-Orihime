package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

public interface IPickedResult {
    ItemStack getPickedResult(HitResult target);
}
