package cn.sh1rocu.touhoulittlemaid.api.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class RenderHandEvent extends CancellableEvent {
    private final AbstractClientPlayer player;
    private final InteractionHand hand;
    private final ItemStack stack;
    private final PoseStack matrices;
    private final MultiBufferSource vertexConsumers;
    private final float tickDelta;
    private final float pitch;
    private final float swingProgress;
    private final float equipProgress;
    private final int light;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> (handEvent) -> {
        for (Callback callback : callbacks) {
            callback.post(handEvent);
        }
    });

    public RenderHandEvent(AbstractClientPlayer player, InteractionHand hand, ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, float tickDelta, float pitch, float swingProgress, float equipProgress, int light) {
        this.player = player;
        this.hand = hand;
        this.stack = stack;
        this.matrices = matrices;
        this.vertexConsumers = vertexConsumers;
        this.tickDelta = tickDelta;
        this.pitch = pitch;
        this.swingProgress = swingProgress;
        this.equipProgress = equipProgress;
        this.light = light;
    }

    public AbstractClientPlayer getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public PoseStack getPoseStack() {
        return matrices;
    }

    public MultiBufferSource getMultiBufferSource() {
        return vertexConsumers;
    }

    public int getPackedLight() {
        return light;
    }

    public float getPartialTicks() {
        return tickDelta;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public float getPitch() {
        return pitch;
    }

    public float getEquipProgress() {
        return equipProgress;
    }

    public float getSwingProgress() {
        return swingProgress;
    }

    public interface Callback {
        void post(RenderHandEvent event);
    }
}