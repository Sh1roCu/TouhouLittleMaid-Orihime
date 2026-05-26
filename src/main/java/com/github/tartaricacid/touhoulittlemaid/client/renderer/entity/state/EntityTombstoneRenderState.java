package com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class EntityTombstoneRenderState extends EntityRenderState {
    public Component maidName = Component.empty();
    public ResourceKey<Level> dimension;
}
