package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.BaubleItemHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.getResourceLocation;

public class SyncBaubleMessage {
    public static final ResourceLocation ID = getResourceLocation("sync_bauble");

    /**
     * 全量同步还是增量同步
     */
    private final boolean isFull;
    private final int entityId;
    private final Int2ObjectSortedMap<ItemStack> baubles;

    public static FriendlyByteBuf fullSync(int entityId, Int2ObjectSortedMap<ItemStack> baubles) {
        return encode(true, entityId, baubles);
    }

    public static FriendlyByteBuf partialSync(int entityId, int slot, ItemStack stack) {
        Int2ObjectSortedMap<ItemStack> baubles = new Int2ObjectRBTreeMap<>();
        baubles.put(slot, stack);
        return encode(false, entityId, baubles);
    }

    public static FriendlyByteBuf partialDel(int entityId, int slot) {
        Int2ObjectSortedMap<ItemStack> baubles = new Int2ObjectRBTreeMap<>();
        baubles.put(slot, ItemStack.EMPTY);
        return encode(false, entityId, baubles);
    }

    private SyncBaubleMessage(boolean isFull, int entityId, Int2ObjectSortedMap<ItemStack> baubles) {
        this.isFull = isFull;
        this.entityId = entityId;
        this.baubles = baubles;
    }

    public static FriendlyByteBuf encode(boolean isFull, int entityId, Int2ObjectSortedMap<ItemStack> baubles) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(isFull);
        buf.writeVarInt(entityId);
        buf.writeVarInt(baubles.size());
        baubles.forEach((slot, stack) -> {
            buf.writeVarInt(slot);
            buf.writeItem(stack);
        });
        return buf;
    }

    public static SyncBaubleMessage decode(FriendlyByteBuf buf) {
        boolean action = buf.readBoolean();
        int entityId = buf.readVarInt();
        Int2ObjectSortedMap<ItemStack> baubles = new Int2ObjectRBTreeMap<>();
        int size = buf.readVarInt();
        for (int i = 0; i < size; i++) {
            int slot = buf.readVarInt();
            ItemStack stack = buf.readItem();
            baubles.put(slot, stack);
        }
        return new SyncBaubleMessage(action, entityId, baubles);
    }

    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var message = decode(buf);
        client.execute(() -> handleClient(message));
    }

    @Environment(EnvType.CLIENT)
    private static void handleClient(SyncBaubleMessage message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        Entity entity = mc.level.getEntity(message.entityId);
        if (entity instanceof EntityMaid maid) {
            BaubleItemHandler maidBauble = maid.getMaidBauble();
            // 全量同步前需要清空
            if (message.isFull) {
                maidBauble.clearAll();
            }
            message.baubles.forEach(maidBauble::setStackInSlot);
        }
    }
}