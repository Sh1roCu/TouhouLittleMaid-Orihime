package cn.sh1rocu.touhoulittlemaid.client;

import cn.sh1rocu.touhoulittlemaid.api.event.*;
import cn.sh1rocu.touhoulittlemaid.api.extension.IItemRenderer;
import com.github.tartaricacid.simplebedrockmodel.client.manager.BedrockEntityModelRegisterEvent;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaidClient;
import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.api.event.client.RenderMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.client.download.InfoGetManager;
import com.github.tartaricacid.touhoulittlemaid.client.event.*;
import com.github.tartaricacid.touhoulittlemaid.client.init.*;
import com.github.tartaricacid.touhoulittlemaid.client.input.DismountBroomKey;
import com.github.tartaricacid.touhoulittlemaid.client.input.STTChatKey;
import com.github.tartaricacid.touhoulittlemaid.client.resource.BedrockModelLoader;
import com.github.tartaricacid.touhoulittlemaid.debug.target.DebugClientRenderEvent;
import com.github.tartaricacid.touhoulittlemaid.event.ClientExtensionsEvent;
import com.github.tartaricacid.touhoulittlemaid.event.ClientTickEvent;
import com.github.tartaricacid.touhoulittlemaid.event.maid.UseNameTagEvent;
import com.github.tartaricacid.touhoulittlemaid.network.NetworkHandler;
import com.github.tartaricacid.touhoulittlemaid.util.EntityCacheUtil;
import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

public class TouhouLittleMaidFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TouhouLittleMaidClient.setup();
        NetworkHandler.registerClientReceivers();
        ClientExtensionsEvent.RegisterClientExtensions();
        InfoGetManager.onClientSetup();

        com.github.tartaricacid.simplebedrockmodel.client.ClientSetupEvent.onClientSetup();

        ClientReloadListenerRegistry.onRegisterClientReloadListeners();

        BuiltInRegistries.ITEM.stream().filter(item -> item instanceof IItemRenderer).forEach(clientEx ->
                BuiltinItemRendererRegistry.INSTANCE.register(clientEx,
                        (stack, mode, matrices, vertexConsumers, light, overlay) ->
                                ((IItemRenderer) clientEx).getCustomRenderer().renderByItem(stack, mode, matrices, vertexConsumers, light, overlay)));

        BedrockEntityModelRegisterEvent.CALLBACK.register(BedrockModelLoader::onRegisterBedrockModelRenderers);

        ItemTooltipCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, LOW);
        ItemTooltipCallback.EVENT.addPhaseOrdering(LOW, LOWEST);
        ItemTooltipCallback.EVENT.register(LOWEST, AddInformationEvent::onRenderTooltips);

        RenderHandEvent.CALLBACK.register(CarryMaidHideArmEvent::onRenderHandEvent);
        ModConfigEvents.loading(TouhouLittleMaid.MOD_ID).register(ClientPackDownloadEvent::onLoadingConfig);
        ModConfigEvents.reloading(TouhouLittleMaid.MOD_ID).register(ClientPackDownloadEvent::onReloadingConfig);
        LevelRenderEvents.AFTER_SOLID_FEATURES.register(CompassRenderEvent::onRender);
        LevelRenderEvents.AFTER_SOLID_FEATURES.register(MaidAreaRenderEvent::onRender);
        InteractMaidEvent.CALLBACK.register(UseNameTagEvent::onInteractClient);
        ServerPlayerEvents.JOIN.register(PlayerLoggedInNotice::onEnterGame);
        PlaySoundEvent.CALLBACK.register(MaidSoundFreqEvent::onPlaySoundEvent);
        PlaySoundSourceEvent.CALLBACK.register(PlayMaidSoundEvent::onPlaySoundSource);
        KeyInputCallback.EVENT.register(PressAIChatKeyEvent::onOpenConfig);
        KeyInputCallback.EVENT.register(STTChatKey::onSttChatPress);
        KeyInputCallback.EVENT.register(DismountBroomKey::onDismountPress);
        LevelRenderEvents.AFTER_SOLID_FEATURES.register(ScrollRenderEvent::onRenderWorldLastEvent);
        ScreenEvents.AFTER_INIT.register(ShowOptifineScreen::showOptifineWarning);

        RenderMaidEvent.CALLBACK.register(HIGHEST, SpecialMaidRenderEvent::onRenderPlayerNamedMaid);
        RenderMaidEvent.CALLBACK.register(Event.DEFAULT_PHASE, SpecialMaidRenderEvent::onRenderEncryptNamedMaid);
        RenderMaidEvent.CALLBACK.register(LOW, SpecialMaidRenderEvent::onRenderNormalNamedMaid);
        RenderMaidEvent.CALLBACK.register(LOWEST, SpecialMaidRenderEvent::onRenderEasterEggModel);

        LevelRenderEvents.AFTER_SOLID_FEATURES.register(WirelessIORenderEvent::onRender);
        ClientSetupEvent.onClientSetup();
        ClientSetupEvent.onRegisterGuiLayers();
        ClientTooltipComponentCallback.EVENT.register(InitClientTooltip::onRegisterClientTooltip);
        InitContainerGui.clientSetup();
        InitEntitiesRender.onEntityRenderers();
        InitEntitiesRender.onRegisterLayers();
        ModelLoadingPlugin.register(new InitSpecialItemRender());
        ClientEntityEvents.ENTITY_LOAD.register(EntityCacheUtil::onChangeDim);
        LevelRenderEvents.AFTER_SOLID_FEATURES.register(DebugClientRenderEvent::onRender);
        ClientTickEvents.START_CLIENT_TICK.register(ClientTickEvent::onClientTick);
    }
}
