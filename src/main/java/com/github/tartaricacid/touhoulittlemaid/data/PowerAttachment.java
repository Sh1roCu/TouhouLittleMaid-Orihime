package com.github.tartaricacid.touhoulittlemaid.data;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class PowerAttachment {
    public static final float MAX_POWER = 5.0f;

    public static final Codec<PowerAttachment> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.FLOAT.fieldOf("power").forGetter(o -> o.power)
    ).apply(ins, PowerAttachment::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PowerAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, PowerAttachment::get,
            PowerAttachment::new
    );

    public static final AttachmentType<PowerAttachment> TYPE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "power"),
            builder -> builder
                    .initializer(() -> new PowerAttachment(0))
                    .copyOnDeath()
                    .persistent(CODEC)
                    .syncWith(STREAM_CODEC, AttachmentSyncPredicate.all()));

    private float power;

    public PowerAttachment(float power) {
        this.power = power;
    }

    public void add(float points) {
        if (points + this.power <= MAX_POWER) {
            this.power += points;
        } else {
            this.power = MAX_POWER;
        }
    }

    public void min(float points) {
        if (points <= this.power) {
            this.power -= points;
        } else {
            this.power = 0.0f;
        }
    }

    public void set(float points) {
        this.power = Mth.clamp(points, 0, MAX_POWER);
    }

    public float get() {
        return this.power;
    }
}
