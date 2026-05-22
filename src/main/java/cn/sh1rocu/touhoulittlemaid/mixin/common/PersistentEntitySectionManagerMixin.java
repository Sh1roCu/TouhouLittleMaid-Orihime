package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.IEntity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin<T extends EntityAccess> {
    @Inject(remap = false, method = {"lambda$processPendingLoads$0", "lambda$addWorldGenChunkEntities$0", "lambda$addLegacyChunkEntities$0"}, at = @At("TAIL"))
    private void tlm$addedToWorld(EntityAccess entityAccess, CallbackInfo ci) {
        if (entityAccess instanceof IEntity entity)
            entity.onAddedToLevel();
    }
}