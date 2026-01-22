package cn.sh1rocu.touhoulittlemaid.mixin.compat.trinkets;

import com.github.tartaricacid.touhoulittlemaid.compat.trinkets.event.SlotModifiersUpdatedEvent;
import com.llamalad7.mixinextras.sugar.Local;
import dev.emi.trinkets.api.SlotType;
import dev.emi.trinkets.api.TrinketInventory;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(TrinketInventory.class)
public abstract class TrinketInventoryMixin {
    @Shadow(remap = false)
    @Final
    private SlotType slotType;

    @Inject(remap = false, method = "update", at = @At(remap = true, value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    private void tlm$slotsUpdate(CallbackInfo ci, @Local(name = "entity") LivingEntity entity) {
        SlotModifiersUpdatedEvent.EVENT.invoker().post(new SlotModifiersUpdatedEvent(entity, Set.of(this.slotType)));
    }
}
