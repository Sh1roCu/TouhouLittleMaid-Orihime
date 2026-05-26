package cn.sh1rocu.touhoulittlemaid.client;

import cn.sh1rocu.touhoulittlemaid.api.event.KeyInputCallback;
import cn.sh1rocu.touhoulittlemaid.api.event.PlaySoundEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.PlaySoundSourceEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.RenderHandEvent;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaidClient;
import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.client.download.InfoGetManager;
import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import com.github.tartaricacid.touhoulittlemaid.client.event.*;
import com.github.tartaricacid.touhoulittlemaid.client.init.*;
import com.github.tartaricacid.touhoulittlemaid.client.input.DismountBroomKey;
import com.github.tartaricacid.touhoulittlemaid.client.input.STTChatKey;
import com.github.tartaricacid.touhoulittlemaid.debug.target.DebugClientRenderEvent;
import com.github.tartaricacid.touhoulittlemaid.event.ClientExtensionsEvent;
import com.github.tartaricacid.touhoulittlemaid.event.ClientTickEvent;
import com.github.tartaricacid.touhoulittlemaid.event.maid.UseNameTagEvent;
import com.github.tartaricacid.touhoulittlemaid.network.NetworkHandler;
import com.github.tartaricacid.touhoulittlemaid.util.EntityCacheUtil;
import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.Event;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.LOW;
import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.LOWEST;
import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public class TouhouLittleMaidFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TouhouLittleMaidClient.setup();
        NetworkHandler.registerClientReceivers();
        ClientExtensionsEvent.RegisterClientExtensions();
        InfoGetManager.onClientSetup();

        ClientReloadListenerRegistry.onRegisterClientReloadListeners();

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

        LevelRenderEvents.AFTER_SOLID_FEATURES.register(WirelessIORenderEvent::onRender);
        ClientSetupEvent.onClientSetup();
        ClientSetupEvent.onRegisterGuiLayers();
        ClientTooltipComponentCallback.EVENT.register(InitClientTooltip::onRegisterClientTooltip);
        InitContainerGui.clientSetup();
        InitEntitiesRender.onEntityRenderers();
        InitEntitiesRender.onRegisterLayers();
        ClientEntityEvents.ENTITY_LOAD.register(EntityCacheUtil::onChangeDim);
        LevelRenderEvents.AFTER_SOLID_FEATURES.register(DebugClientRenderEvent::onRender);
        ClientTickEvents.START_CLIENT_TICK.register(ClientTickEvent::onClientTick);
    }
}
