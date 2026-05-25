package com.github.tartaricacid.touhoulittlemaid.entity.data;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskIdle;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record TaskData(String taskId) {
    private static final Codec<TaskData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.STRING.fieldOf("task_id").forGetter(TaskData::taskId)
    ).apply(ins, TaskData::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, TaskData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, TaskData::taskId,
            TaskData::new
    );

    public static final AttachmentType<TaskData> TYPE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "task"), builder -> builder
                    .initializer(() -> new TaskData(TaskIdle.UID.toString()))
                    .persistent(CODEC)
                    .syncWith(STREAM_CODEC, AttachmentSyncPredicate.all())
                    .copyOnDeath());
}
