package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.event.EntityJoinLevelEvent;
import cn.sh1rocu.touhoulittlemaid.api.extension.IEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
    protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Inject(method = "addPlayer", at = @At("HEAD"), cancellable = true)
    public void tlm$addEntityEvent(ServerPlayer serverPlayer, CallbackInfo ci) {
        EntityJoinLevelEvent event = new EntityJoinLevelEvent(serverPlayer, this);
        EntityJoinLevelEvent.CALLBACK.invoker().post(event);
        if (event.isCanceled())
            ci.cancel();
    }

    @WrapOperation(method = "addEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/entity/PersistentEntitySectionManager;addNewEntity(Lnet/minecraft/world/level/entity/EntityAccess;)Z"))
    private <T extends EntityAccess> boolean tlm$addedToLevel(PersistentEntitySectionManager<Entity> instance, T entity, Operation<Boolean> original) {
        Boolean call = original.call(instance, entity);
        if (entity instanceof IEntity iEntity && call) {
            iEntity.onAddedToWorld();
        }
        return call;
    }

    @Mixin(targets = "net.minecraft.server.level.ServerLevel$EntityCallbacks")
    public static abstract class EntityCallbacksMixin {
        @Inject(method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
        private void tlm$removedFromLevel(Entity entity, CallbackInfo ci) {
            if (entity instanceof IEntity iEntity)
                iEntity.onRemovedFromWorld();
        }
    }
}