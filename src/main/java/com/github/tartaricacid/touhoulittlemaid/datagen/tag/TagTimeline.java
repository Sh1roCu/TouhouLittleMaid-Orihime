package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.datagen.TimelinesProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TimelineTags;
import net.minecraft.world.timeline.Timeline;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TagTimeline extends FabricTagsProvider<Timeline> {
    public TagTimeline(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.TIMELINE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.builder(TimelineTags.UNIVERSAL).add(TimelinesProvider.MAID_SCHEDULE);
    }
}
