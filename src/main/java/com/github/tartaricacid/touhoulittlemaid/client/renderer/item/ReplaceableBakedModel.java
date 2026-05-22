package com.github.tartaricacid.touhoulittlemaid.client.renderer.item;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ReplaceableBakedModel extends ForwardingBakedModel {
    private final BakedModel replacedBakedModel;
    private final Supplier<Boolean> isReplace;

    public ReplaceableBakedModel(BakedModel rawBakedModel, BakedModel replacedBakedModel, Supplier<Boolean> isReplace) {
        this.wrapped = rawBakedModel;
        this.replacedBakedModel = replacedBakedModel;
        this.isReplace = isReplace;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, RandomSource random) {
        if (isReplace.get()) {
            return this.replacedBakedModel.getQuads(pState, pDirection, random);
        } else {
            return this.wrapped.getQuads(pState, pDirection, random);
        }
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public ItemOverrides getOverrides() {
        if (isReplace.get()) {
            return this.replacedBakedModel.getOverrides();
        } else {
            return this.wrapped.getOverrides();
        }
    }

    @Override
    public ItemTransforms getTransforms() {
        if (isReplace.get()) {
            return this.replacedBakedModel.getTransforms();
        } else {
            return this.wrapped.getTransforms();
        }
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        if (isReplace.get()) {
            this.replacedBakedModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        } else {
            this.wrapped.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        if (isReplace.get()) {
            this.replacedBakedModel.emitItemQuads(stack, randomSupplier, context);
        } else {
            this.wrapped.emitItemQuads(stack, randomSupplier, context);
        }
    }
/*    @Override
    public BakedModel applyTransform(ItemDisplayContext type, PoseStack mat, boolean applyLeftHandTransform) {
        if (isReplace.get()) {
            return this.replacedBakedModel.applyTransform(type, mat, applyLeftHandTransform);
        } else {
            return this.rawBakedModel.applyTransform(type, mat, applyLeftHandTransform);
        }
    }*/
}
