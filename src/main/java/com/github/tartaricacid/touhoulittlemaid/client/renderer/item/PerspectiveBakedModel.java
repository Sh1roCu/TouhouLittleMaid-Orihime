package com.github.tartaricacid.touhoulittlemaid.client.renderer.item;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PerspectiveBakedModel extends ForwardingBakedModel {
    private final BakedModel bakedModel3d;

    public PerspectiveBakedModel(BakedModel bakedModel2d, BakedModel bakedModel3d) {
        this.wrapped = bakedModel2d;
        this.bakedModel3d = bakedModel3d;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Nonnull
    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        ItemDisplayContext type = context.itemTransformationMode();
        if (type == ItemDisplayContext.GUI || type == ItemDisplayContext.FIXED) {
            wrapped.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        } else {
            bakedModel3d.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        ItemDisplayContext type = context.itemTransformationMode();
        if (type == ItemDisplayContext.GUI || type == ItemDisplayContext.FIXED) {
            wrapped.emitItemQuads(stack, randomSupplier, context);
        } else {
            bakedModel3d.emitItemQuads(stack, randomSupplier, context);
        }
    }

/*    @Override
    public BakedModel applyTransform(ItemDisplayContext type, PoseStack mat, boolean applyLeftHandTransform) {
        if (type == ItemDisplayContext.GUI || type == ItemDisplayContext.FIXED) {
            return bakedModel2d.applyTransform(type, mat, applyLeftHandTransform);
        } else {
            return bakedModel3d.applyTransform(type, mat, applyLeftHandTransform);
        }
    }*/
}
