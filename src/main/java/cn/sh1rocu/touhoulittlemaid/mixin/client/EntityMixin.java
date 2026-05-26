package cn.sh1rocu.touhoulittlemaid.mixin.client;

import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        var self = (Entity) (Object) this;
        if (self.level().isClientSide() && self instanceof Mob mob && !mob.hasAttached(GeckoMaidEntity.TYPE)) {
            IMaid maid = IMaid.convert(mob);
            if (maid != null) {
                mob.setAttached(GeckoMaidEntity.TYPE, new GeckoMaidEntity<>(mob, maid));
            }
        }
    }
}
