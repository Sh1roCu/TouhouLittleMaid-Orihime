package cn.sh1rocu.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.api.extension.HasClientExtensionsBlock;
import cn.sh1rocu.touhoulittlemaid.api.extension.IEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level {
    @Shadow
    @Final
    private Minecraft minecraft;

    protected ClientLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Inject(method = "addEntity", at = @At("TAIL"))
    public void tlm$addedToWorld(Entity entity, CallbackInfo ci) {
        if (entity instanceof IEntity iEntity)
            iEntity.onAddedToLevel();
    }

    @WrapOperation(
            method = "addBreakingBlockEffect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;shouldSpawnTerrainParticles()Z")
    )
    private boolean tlm$addHitEffects(BlockState instance, Operation<Boolean> original) {
        if (instance.getBlock() instanceof HasClientExtensionsBlock block)
            return !block.getClientBlockExtensions().addHitEffects(instance, this, this.minecraft.hitResult, this.minecraft.particleEngine);
        return original.call(instance);
    }

    @WrapOperation(
            method = "addDestroyBlockEffect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;shouldSpawnTerrainParticles()Z")
    )
    private boolean tlm$addDestroyEffects(BlockState instance, Operation<Boolean> original, @Local BlockPos pos) {
        if (instance.getBlock() instanceof HasClientExtensionsBlock block)
            return !block.getClientBlockExtensions().addDestroyEffects(instance, this, pos, this.minecraft.particleEngine);
        return original.call(instance);
    }

    @Mixin(targets = "net/minecraft/client/multiplayer/ClientLevel$EntityCallbacks")
    public abstract static class EntityCallbacksMixin {
        @Inject(method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
        private void tlm$removedFromLevel(Entity entity, CallbackInfo ci) {
            if (entity instanceof IEntity iEntity)
                iEntity.onRemovedFromLevel();
        }
    }
}