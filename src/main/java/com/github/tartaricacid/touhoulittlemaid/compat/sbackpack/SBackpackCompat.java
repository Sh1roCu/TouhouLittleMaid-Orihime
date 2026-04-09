package com.github.tartaricacid.touhoulittlemaid.compat.sbackpack;


import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.accessories.SBackpackAccessoriesCompat;
import com.github.tartaricacid.touhoulittlemaid.init.registry.CompatRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.ItemStack;

public class SBackpackCompat {
    private static boolean IS_LOADED = false;

    public static void init() {
        IS_LOADED = true;
        InteractMaidEvent.CALLBACK.register(BackpackRightClickMaidEvent::onClickMaid);
        // 女仆与精妙背包的 Accessories(Trinkets) 兼容
        if (FabricLoader.getInstance().isModLoaded(CompatRegistry.ACCESSORIES)) {
            SBackpackAccessoriesCompat.init();
        }
    }

    public static boolean isLoaded() {
        return IS_LOADED;
    }

    public static boolean isBackpack(ItemStack stack) {
        if (isLoaded()) {
            return SBackpackCompatInner.isBackpack(stack);
        }
        return false;
    }
}