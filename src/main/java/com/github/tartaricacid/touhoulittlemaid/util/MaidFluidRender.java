package com.github.tartaricacid.touhoulittlemaid.util;

import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

/**
 * From JEI: <a href="https://github.com/mezz/JustEnoughItems/blob/1.20/Forge/src/main/java/mezz/jei/forge/platform/FluidHelper.java">...</a>
 */
public final class MaidFluidRender {
    private static final int TEXTURE_SIZE = 16;

    public static Component getFluidName(String fluidId, long amount) {
        Fluid fluid = BuiltInRegistries.FLUID.getValue(Identifier.parse(fluidId));
        if (amount <= 0 || fluid == null || fluid.isSame(Fluids.EMPTY)) {
            return Component.translatable("tooltips.touhou_little_maid.tank_backpack.empty_fluid");
        }
        //return fluid.getFluidType().getDescription();
        return FluidVariantAttributes.getName(FluidVariant.of(fluid));
    }

    public static void drawFluid(GuiGraphicsExtractor graphics, int x, int y, int width, int height, String fluidId, long amount, long capacity) {
        Fluid fluid = BuiltInRegistries.FLUID.getValue(Identifier.parse(fluidId));
        if (amount <= 0 || fluid == null || fluid.isSame(Fluids.EMPTY)) {
            return;
        }
        FluidVariant fluidStack = FluidVariant.of(fluid);
        getStillFluidSprite(fluidStack).ifPresent(fluidStillSprite -> {
            int fluidColor = getColorTint(fluidStack);
            long scaledAmount = (amount * height) / capacity;
            if (scaledAmount < 1) {
                // 至少渲染一行像素，让人知道里面有东西
                scaledAmount = 1;
            }
            if (scaledAmount > height) {
                scaledAmount = height;
            }
            graphics.pose().pushMatrix();
            graphics.pose().translate(x, y);
            drawTiledSprite(graphics, width, height, fluidColor, scaledAmount, fluidStillSprite);
            graphics.pose().popMatrix();
        });
    }

    public static int getColorTint(FluidVariant stack) {
        FluidModel model = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(stack.getFluid().defaultFluidState());
        if (model == null) {
            return 0xFFFFFFFF;
        }
        int c = FluidVariantRendering.getColor(stack);
        if ((c >>> 24) == 0) {
            c |= 0xFF000000;
        }
        return c;
    }

    public static Optional<TextureAtlasSprite> getStillFluidSprite(FluidVariant fluidStack) {
        FluidModel model = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluidStack.getFluid().defaultFluidState());
        if (model == null || model.stillMaterial() == null) {
            return Optional.empty();
        }
        TextureAtlasSprite sprite = model.stillMaterial().sprite();
        if (sprite.contents().name().equals(MissingTextureAtlasSprite.getLocation())) {
            return Optional.empty();
        }
        return Optional.of(sprite);
    }

    private static void drawTiledSprite(GuiGraphicsExtractor guiGraphics, final int tiledWidth, final int tiledHeight, int color, long scaledAmount, TextureAtlasSprite sprite) {
        Minecraft minecraft = Minecraft.getInstance();
        AbstractTexture abstractTexture = minecraft.getTextureManager().getTexture(sprite.atlasLocation());
        int atlasWidth = abstractTexture.getTexture().getWidth(0);
        int atlasHeight = abstractTexture.getTexture().getHeight(0);

        final int xTileCount = tiledWidth / TEXTURE_SIZE;
        final int xRemainder = tiledWidth - (xTileCount * TEXTURE_SIZE);
        final long yTileCount = scaledAmount / TEXTURE_SIZE;
        final long yRemainder = scaledAmount - (yTileCount * TEXTURE_SIZE);

        for (int xTile = 0; xTile <= xTileCount; xTile++) {
            for (int yTile = 0; yTile <= yTileCount; yTile++) {
                int width = (xTile == xTileCount) ? xRemainder : TEXTURE_SIZE;
                long height = (yTile == yTileCount) ? yRemainder : TEXTURE_SIZE;
                int x = (xTile * TEXTURE_SIZE);
                int y = tiledHeight - ((yTile + 1) * TEXTURE_SIZE);
                if (width > 0 && height > 0) {
                    long maskTop = TEXTURE_SIZE - height;
                    int maskRight = TEXTURE_SIZE - width;
                    blitMaskedFluidTile(guiGraphics, sprite, atlasWidth, atlasHeight, x, y, maskTop, maskRight, color);
                }
            }
        }
    }

    private static void blitMaskedFluidTile(GuiGraphicsExtractor graphics, TextureAtlasSprite sprite, int atlasWidth, int atlasHeight, float xCoord, float yCoord, long maskTop, int maskRight, int color) {
        float uMin = sprite.getU0();
        float uMax = sprite.getU1();
        float vMin = sprite.getV0();
        float vMax = sprite.getV1();
        uMax = uMax - (maskRight / 16F * (uMax - uMin));
        vMax = vMax - (maskTop / 16F * (vMax - vMin));

        float uPixel = uMin * atlasWidth;
        float vPixel = vMin * atlasHeight;
        int srcW = Math.max(1, Math.round((uMax - uMin) * atlasWidth));
        int srcH = Math.max(1, Math.round((vMax - vMin) * atlasHeight));
        int drawW = TEXTURE_SIZE - maskRight;
        int drawH = TEXTURE_SIZE - (int) maskTop;

        graphics.blit(RenderPipelines.GUI_TEXTURED, sprite.atlasLocation(), (int) xCoord, (int) yCoord, uPixel, vPixel, drawW, drawH, srcW, srcH, atlasWidth, atlasHeight, color);
    }
}
