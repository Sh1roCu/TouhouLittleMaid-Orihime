package cn.sh1rocu.touhoulittlemaid.mixin.client;

import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        var self = (Entity) (Object) this;
        if (self.level().isClientSide() && self instanceof EntityMaid maid && !maid.hasAttached(GeckoMaidEntity.TYPE)) {
            maid.setAttached(GeckoMaidEntity.TYPE, new GeckoMaidEntity<>(maid));
        }
    }
}
