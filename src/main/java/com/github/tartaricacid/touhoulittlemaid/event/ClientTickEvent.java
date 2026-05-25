package com.github.tartaricacid.touhoulittlemaid.event;

import net.minecraft.client.Minecraft;

public class ClientTickEvent {
    private static int tickCount;
    private static int refreshRate = 60;

    public static void onClientTick(Minecraft client) {
        tickCount++;
        refreshRate = client.getWindow().getRefreshRate();
    }

    public static int getTickCount() {
        return tickCount;
    }

    public static int getRefreshRate() {
        return refreshRate;
    }
}
