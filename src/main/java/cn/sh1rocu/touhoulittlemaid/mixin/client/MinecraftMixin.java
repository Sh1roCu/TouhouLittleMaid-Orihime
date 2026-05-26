package cn.sh1rocu.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.api.event.RegisterClientReloadListenersEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow
    @Final
    private ReloadableResourceManager resourceManager;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateVsync(Z)V"))
    private void sbm$onInit(CallbackInfo ci) {
        RegisterClientReloadListenersEvent.CALLBACK.invoker().post(new RegisterClientReloadListenersEvent(this.resourceManager));
    }
}