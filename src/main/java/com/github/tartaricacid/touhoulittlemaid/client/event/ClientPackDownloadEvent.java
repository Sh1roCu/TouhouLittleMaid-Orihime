package com.github.tartaricacid.touhoulittlemaid.client.event;

import com.github.tartaricacid.touhoulittlemaid.client.download.ClientPackDownloadManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.neoforged.fml.config.ModConfig;

@Environment(EnvType.CLIENT)
public class ClientPackDownloadEvent {
    private static final String CONFIG_NAME = "touhou_little_maid-server.toml";

    /**
     * 客户端启动并读取配置时，会触发此事件
     */
    public static void onLoadingConfig(ModConfig config) {
        String fileName = config.getFileName();
        if (CONFIG_NAME.equals(fileName)) {
            ClientPackDownloadManager.downloadClientPack();
        }
    }

    /**
     * 玩家进入服务端，或者服务端自动重置配置时，会触发此方法
     */
    public static void onReloadingConfig(ModConfig config) {
        String fileName = config.getFileName();
        if (CONFIG_NAME.equals(fileName)) {
            ClientPackDownloadManager.downloadClientPack();
        }
    }
}
