package cn.sh1rocu.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.api.event.KeyInputCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * From Porting_Lib
 */
@Environment(EnvType.CLIENT)
@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    // First return opcode is jumped over if condition is met.
    @Inject(
            method = "keyPress",
            slice = @Slice(
                    from = @At(
                            value = "RETURN",
                            ordinal = 0,
                            shift = Shift.AFTER
                    )
            ),
            at = @At(value = "RETURN")
    )
    public void tlm$onHandleKeyInput(long handle, int action, KeyEvent event, CallbackInfo ci) {
        KeyInputCallback.EVENT.invoker().onKeyInput(event.key(), event.scancode(), action, event.modifiers());
    }
}