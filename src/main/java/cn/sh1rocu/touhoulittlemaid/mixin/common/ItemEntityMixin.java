package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.IItemEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow
    public abstract ItemStack getItem();

    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    public void tlm$tick(CallbackInfo ci) {
        ItemStack stack = this.getItem();
        if (stack.getItem() instanceof IItemEntity item) {
            if (item.tlm$onEntityItemUpdate(stack, (ItemEntity) (Object) this))
                ci.cancel();
        }
    }
}