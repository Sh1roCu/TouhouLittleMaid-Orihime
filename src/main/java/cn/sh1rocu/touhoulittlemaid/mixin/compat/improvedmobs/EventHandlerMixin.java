package cn.sh1rocu.touhoulittlemaid.mixin.compat.improvedmobs;

import cn.sh1rocu.touhoulittlemaid.util.Dummy;
import org.spongepowered.asm.mixin.Mixin;

// TODO
//@Mixin(EventHandler.class)
@Mixin(Dummy.class)
public class EventHandlerMixin {
//    @WrapWithCondition(
//            method = "onEntityLoad",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lio/github/flemmli97/improvedmobs/common/events/EventCalls;onEntityLoad(Lnet/minecraft/world/entity/Mob;)V"
//            )
//    )
//    private static boolean tlm$onEntityLoad(Mob mob) {
//        return !(mob instanceof EntityMaid);
//    }
}
