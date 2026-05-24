package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.client.animation.HardcodedAnimationManger;
import com.github.tartaricacid.touhoulittlemaid.client.animation.gecko.AnimationRegister;
import com.github.tartaricacid.touhoulittlemaid.client.animation.gecko.magic.MagicCastingAnimationManager;
import com.github.tartaricacid.touhoulittlemaid.client.event.ShowOptifineScreen;
import com.github.tartaricacid.touhoulittlemaid.client.overlay.BroomTipsOverlay;
import com.github.tartaricacid.touhoulittlemaid.client.overlay.MaidTipsOverlay;
import com.github.tartaricacid.touhoulittlemaid.client.overlay.ShowPowerOverlay;
import com.github.tartaricacid.touhoulittlemaid.compat.embeddium.EmbeddiumCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.immersivemelodies.client.ImmersiveMelodiesCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.iris.IrisCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.patpat.PatPatCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.ponder.PonderCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.simplehats.SimpleHatsCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.sodium.SodiumCompat;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public class ClientSetupEvent {
    public static void onClientSetup() {
        AnimationRegister.registerAnimationState();
        MaidTipsOverlay.init();
        ShowOptifineScreen.checkOptifineIsLoaded();
        HardcodedAnimationManger.init();
        MagicCastingAnimationManager.init();
        KeyMappingRegister.onRegisterKeyMappings();

        // 客户端兼容
        SimpleHatsCompat.init();
        ImmersiveMelodiesCompat.init();
        SodiumCompat.init();
        EmbeddiumCompat.init();
        IrisCompat.init();
        PatPatCompat.init();
        PonderCompat.register();
    }

    public static void onRegisterGuiLayers() {
        HudElementRegistry.attachElementBefore(VanillaHudElements.CROSSHAIR, getResourceLocation("tlm_maid_tips"), MaidTipsOverlay.INSTANCE);
        HudElementRegistry.attachElementBefore(VanillaHudElements.CROSSHAIR, getResourceLocation("tlm_broom_tips"), BroomTipsOverlay.INSTANCE);
        HudElementRegistry.attachElementBefore(VanillaHudElements.HOTBAR, getResourceLocation("tlm_show_power"), ShowPowerOverlay.INSTANCE);
    }
}