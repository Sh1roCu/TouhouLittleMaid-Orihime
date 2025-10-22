package com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.implement;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.implement.EmojiChatBubbleRenderer;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.IChatBubbleData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class EmojiChatBubbleData implements IChatBubbleData {
    public static final ResourceLocation ID = new ResourceLocation(TouhouLittleMaid.MOD_ID, "emoji");
    private final ResourceLocation bg;

    @Environment(EnvType.CLIENT)
    private IChatBubbleRenderer renderer;

    public EmojiChatBubbleData(ResourceLocation bg) {
        this.bg = bg;
    }

    public static EmojiChatBubbleData create() {
        return new EmojiChatBubbleData(TYPE_2);
    }

    @Override
    public int existTick() {
        return DEFAULT_EXIST_TICK;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public IChatBubbleRenderer getRenderer(IChatBubbleRenderer.Position position) {
        if (this.renderer == null) {
            this.renderer = new EmojiChatBubbleRenderer(this.bg);
        }
        return this.renderer;
    }

    public static class EmojiChatSerializer implements IChatBubbleData.ChatSerializer {
        @Override
        public IChatBubbleData readFromBuff(FriendlyByteBuf buf) {
            ResourceLocation bg = buf.readResourceLocation();
            return new EmojiChatBubbleData(bg);
        }

        @Override
        public void writeToBuff(FriendlyByteBuf buf, IChatBubbleData data) {
            EmojiChatBubbleData emojiChat = (EmojiChatBubbleData) data;
            buf.writeResourceLocation(emojiChat.bg);
        }
    }
}
