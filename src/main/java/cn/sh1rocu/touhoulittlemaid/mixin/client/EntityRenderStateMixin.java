package cn.sh1rocu.touhoulittlemaid.mixin.client;

import cn.sh1rocu.touhoulittlemaid.api.mixin.IEntityRenderStatePartialTick;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements IEntityRenderStatePartialTick {
    @Unique
    private float tlm$partialTick;

    @Override
    public float tlm$partialTick() {
        return this.tlm$partialTick;
    }

    @Override
    public void tlm$setPartialTick(float partialTicks) {
        this.tlm$partialTick = partialTicks;
    }
}
