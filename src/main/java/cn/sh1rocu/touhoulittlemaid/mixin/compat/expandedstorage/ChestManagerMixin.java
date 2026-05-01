package cn.sh1rocu.touhoulittlemaid.mixin.compat.expandedstorage;

import com.github.tartaricacid.touhoulittlemaid.compat.expandedstorage.ExpandedChestType;
import com.github.tartaricacid.touhoulittlemaid.inventory.chest.ChestManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChestManager.class)
public class ChestManagerMixin {
    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/tartaricacid/touhoulittlemaid/compat/ironchest/IronChestType;register(Lcom/github/tartaricacid/touhoulittlemaid/inventory/chest/ChestManager;)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void tlm$initExpandedStorageChestType(CallbackInfo ci, ChestManager manager) {
        ExpandedChestType.register(manager);
    }
}
