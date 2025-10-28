package com.github.tartaricacid.touhoulittlemaid.client.init;

import cn.sh1rocu.touhoulittlemaid.api.event.AddPackFindersEvent;
import com.github.tartaricacid.touhoulittlemaid.client.animation.HardcodedAnimationManger;
import com.github.tartaricacid.touhoulittlemaid.client.animation.gecko.AnimationRegister;
import com.github.tartaricacid.touhoulittlemaid.client.event.ShowOptifineScreen;
import com.github.tartaricacid.touhoulittlemaid.client.input.DismountBroomKey;
import com.github.tartaricacid.touhoulittlemaid.client.input.STTChatKey;
import com.github.tartaricacid.touhoulittlemaid.client.overlay.BroomTipsOverlay;
import com.github.tartaricacid.touhoulittlemaid.client.overlay.MaidTipsOverlay;
import com.github.tartaricacid.touhoulittlemaid.client.overlay.ShowPowerOverlay;
import com.github.tartaricacid.touhoulittlemaid.client.resource.LegacyPackRepositorySource;
import com.github.tartaricacid.touhoulittlemaid.client.resource.listener.EmojiReloadListener;
import com.github.tartaricacid.touhoulittlemaid.compat.embeddium.EmbeddiumCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.immersivemelodies.ImmersiveMelodiesCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.oculus.OculusCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.patpat.PatPatCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.ponder.PonderCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.simplehats.SimpleHatsCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.sodium.SodiumCompat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

@Environment(EnvType.CLIENT)
public class ClientSetupEvent {
    // private static final ResourceLocation CROSSHAIR = ResourceLocation.withDefaultNamespace("hud/crosshair");
    // private static final ResourceLocation HOTBAR = ResourceLocation.withDefaultNamespace("hud/hotbar");

    public static void onClientSetup() {
        AnimationRegister.registerAnimationState();
        MaidTipsOverlay.init();
        ShowOptifineScreen.checkOptifineIsLoaded();
        HardcodedAnimationManger.init();
        resisterKeyMappings();
        AddPackFindersEvent.CALLBACK.register(ClientSetupEvent::    onAddPackFinders);

        // 客户端兼容
        SimpleHatsCompat.init();
        ImmersiveMelodiesCompat.init();
        OculusCompat.init();
        SodiumCompat.init();
        EmbeddiumCompat.init();
        PatPatCompat.init();
        PonderCompat.register();
    }

    public static void onRegisterGuiLayers() {
        // event.registerAbove(CROSSHAIR, getResourceLocation("tlm_maid_tips"), new MaidTipsOverlay());
        // event.registerAbove(CROSSHAIR, getResourceLocation("tlm_broom_tips"), new BroomTipsOverlay());
        // event.registerAbove(HOTBAR, getResourceLocation("tlm_show_power"), new ShowPowerOverlay());
        HudRenderCallback.EVENT.register(MaidTipsOverlay.INSTANCE::render);
        HudRenderCallback.EVENT.register(BroomTipsOverlay.INSTANCE::render);
        HudRenderCallback.EVENT.register(ShowPowerOverlay.INSTANCE::render);
    }

    public static void resisterKeyMappings() {
        KeyBindingHelper.registerKeyBinding(STTChatKey.STT_CHAT_KEY);
        KeyBindingHelper.registerKeyBinding(DismountBroomKey.DISMOUNT_KEY);
    }

    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addRepositorySource(new LegacyPackRepositorySource());
        }
    }

    public static void onRegisterClientReloadListeners() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new EmojiReloadListener());
    }
}