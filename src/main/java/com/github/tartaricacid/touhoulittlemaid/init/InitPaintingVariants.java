package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class InitPaintingVariants {
    public static final ResourceKey<PaintingVariant> WINE_FOX = ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocationUtil.getResourceLocation("wine_fox"));

    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        context.register(WINE_FOX, new PaintingVariant(2, 3, WINE_FOX.location()));
    }
}
