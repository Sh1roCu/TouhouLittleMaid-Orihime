package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.event.EntityJoinLevelEvent;
import cn.sh1rocu.touhoulittlemaid.api.extension.IEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin<T extends EntityAccess> {
    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    private void tlm$entityJoinLevelEvent(T entityAccess, boolean loadedFromDisk, CallbackInfoReturnable<Boolean> cir) {
        if (entityAccess instanceof Entity entity) {
            EntityJoinLevelEvent event = new EntityJoinLevelEvent(entity, entity.level(), loadedFromDisk);
            EntityJoinLevelEvent.CALLBACK.invoker().post(event);
            if (event.isCanceled())
                cir.setReturnValue(false);
        }
    }

    // method_31857: processPendingLoads.11
    // method_31863: addWorldGenChunkEntities.1
    // method_31864: addLegacyChunkEntities.0
    @Inject(method = {"method_31857", "method_31863", "method_31864"}, at = @At("TAIL"))
    private void tlm$addedToWorld(EntityAccess entityAccess, CallbackInfo ci) {
        if (entityAccess instanceof IEntity entity)
            entity.onAddedToWorld();
    }
}