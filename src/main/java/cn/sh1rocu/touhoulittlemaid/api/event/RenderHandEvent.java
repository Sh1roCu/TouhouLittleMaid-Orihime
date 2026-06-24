package cn.sh1rocu.touhoulittlemaid.api.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class RenderHandEvent extends CancellableEvent {
    private final InteractionHand hand;
    private final PoseStack poseStack;
    private final SubmitNodeCollector submitNodeCollector;
    private final int packedLight;
    private final float partialTick;
    private final float interpolatedPitch;
    private final float swingProgress;
    private final float equipProgress;
    private final ItemStack stack;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> (handEvent) -> {
        for (Callback callback : callbacks) {
            callback.post(handEvent);
        }
    });

    public RenderHandEvent(InteractionHand hand, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight,
                           float partialTick, float interpolatedPitch,
                           float swingProgress, float equipProgress, ItemStack stack) {
        this.hand = hand;
        this.poseStack = poseStack;
        this.submitNodeCollector = submitNodeCollector;
        this.packedLight = packedLight;
        this.partialTick = partialTick;
        this.interpolatedPitch = interpolatedPitch;
        this.swingProgress = swingProgress;
        this.equipProgress = equipProgress;
        this.stack = stack;
    }


    public InteractionHand getHand() {
        return hand;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public SubmitNodeCollector getSubmitNodeCollector() {
        return submitNodeCollector;
    }

    public int getPackedLight() {
        return packedLight;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public float getInterpolatedPitch() {
        return interpolatedPitch;
    }

    public float getSwingProgress() {
        return swingProgress;
    }

    public float getEquipProgress() {
        return equipProgress;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public interface Callback {
        void post(RenderHandEvent event);
    }
}