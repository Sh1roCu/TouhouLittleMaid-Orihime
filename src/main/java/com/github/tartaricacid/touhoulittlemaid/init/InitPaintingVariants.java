package com.github.tartaricacid.touhoulittlemaid.init;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import java.util.Optional;

import static com.github.tartaricacid.touhoulittlemaid.datagen.tag.TagPaintingVariant.WINE_FOX;

public class InitPaintingVariants {
    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        context.register(WINE_FOX, new PaintingVariant(2, 3, WINE_FOX.identifier(), Optional.empty(), Optional.empty()));
    }
}
