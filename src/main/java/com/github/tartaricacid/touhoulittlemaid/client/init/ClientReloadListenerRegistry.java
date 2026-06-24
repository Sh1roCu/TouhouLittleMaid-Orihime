package com.github.tartaricacid.touhoulittlemaid.client.init;

import com.github.tartaricacid.touhoulittlemaid.client.resource.bedrock.InternalBedrockModelManager;
import com.github.tartaricacid.touhoulittlemaid.client.resource.listener.CustomPackReloadListener;
import com.github.tartaricacid.touhoulittlemaid.client.resource.listener.EmojiReloadListener;
import com.github.tartaricacid.touhoulittlemaid.util.IdentifierUtil;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

public final class ClientReloadListenerRegistry {
    private static final Identifier BEDROCK_MODEL = id("bedrock_model");
    private static final Identifier BEDROCK_ENTITY_MODEL = id("bedrock_entity_model");
    private static final Identifier CUSTOM_PACK = id("custom_pack");
    private static final Identifier MAID_EMOJI_RELOAD = id("maid_emoji_reload");

    public static void onRegisterClientReloadListeners() {
        InternalBedrockModelManager.INSTANCE = InternalBedrockModelManager.create();

        var registry = ResourceLoader.get(PackType.CLIENT_RESOURCES);
        registry.registerReloadListener(BEDROCK_MODEL, InternalBedrockModelManager.INSTANCE.getModelSet());
        registry.registerReloadListener(BEDROCK_ENTITY_MODEL, InternalBedrockModelManager.INSTANCE.getEntityModelSet());
        registry.registerReloadListener(CUSTOM_PACK, new CustomPackReloadListener());
        registry.registerReloadListener(MAID_EMOJI_RELOAD, new EmojiReloadListener());
    }

    private static Identifier id(String path) {
        return IdentifierUtil.modLoc(path);
    }
}
