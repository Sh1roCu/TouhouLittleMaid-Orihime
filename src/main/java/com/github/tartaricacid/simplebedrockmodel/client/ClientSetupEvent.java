package com.github.tartaricacid.simplebedrockmodel.client;

import cn.sh1rocu.touhoulittlemaid.api.event.RegisterClientReloadListenersEvent;
import com.github.tartaricacid.simplebedrockmodel.client.compat.sodium.SodiumCompat;
import com.github.tartaricacid.simplebedrockmodel.client.manager.BedrockEntityModelRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientSetupEvent {
    public static void onClientSetup() {
        RegisterClientReloadListenersEvent.CALLBACK.register(BedrockEntityModelRegister::onRegisterClientReloadListenersEvent);
        SodiumCompat.init();
    }
}