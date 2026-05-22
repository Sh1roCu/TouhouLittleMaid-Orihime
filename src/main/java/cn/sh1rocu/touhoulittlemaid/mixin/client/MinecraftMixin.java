package cn.sh1rocu.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.api.event.AddPackFindersEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.RegisterClientReloadListenersEvent;
import cn.sh1rocu.touhoulittlemaid.api.extension.IBlock;
import cn.sh1rocu.touhoulittlemaid.api.extension.IPickedResult;
import cn.sh1rocu.touhoulittlemaid.api.mixin.PackRepositoryExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    public abstract PackRepository getResourcePackRepository();

    @Shadow
    @Final
    private ReloadableResourceManager resourceManager;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateVsync(Z)V"))
    private void sbm$onInit(CallbackInfo ci) {
        RegisterClientReloadListenersEvent.CALLBACK.invoker().post(new RegisterClientReloadListenersEvent(this.resourceManager));
    }

    @WrapWithCondition(
            method = "continueAttack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleEngine;crack(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)V"
            )
    )
    private boolean tlm$addHitEffects(ParticleEngine engine, BlockPos pos, Direction side) {
        BlockState state = this.level.getBlockState(pos);
        if (state.getBlock() instanceof IBlock block)
            return !block.tlm$addHitEffects(state, level, this.hitResult, engine);
        return true;
    }

    @ModifyExpressionValue(
            method = "pickBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getPickResult()Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tlm$pickBlock(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IPickedResult item)
            return item.getPickedResult(this.hitResult);
        return stack;
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;reload()V"))
    private void tlm$addPacks(GameConfig gameConfig, CallbackInfo ci) {
        AddPackFindersEvent event = new AddPackFindersEvent(PackType.CLIENT_RESOURCES, ((PackRepositoryExtension) this.getResourcePackRepository())::tlm$addPackFinder, false);
        AddPackFindersEvent.CALLBACK.invoker().onAddPackFinders(event);
    }
}