package cn.sh1rocu.touhoulittlemaid.mixin.compat.improvedmobs;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import io.github.flemmli97.improvedmobs.fabric.events.EventHandler;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EventHandler.class)
public class EventHandlerMixin {
    @WrapWithCondition(
            method = "onEntityLoad",
            at = @At(
                    value = "INVOKE",
                    target = "Lio/github/flemmli97/improvedmobs/common/events/EventCalls;onEntityLoad(Lnet/minecraft/world/entity/Mob;)V"
            )
    )
    private static boolean tlm$onEntityLoad(Mob mob) {
        return !(mob instanceof EntityMaid);
    }
}
