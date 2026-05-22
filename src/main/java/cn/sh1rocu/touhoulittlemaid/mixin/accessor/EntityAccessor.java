package cn.sh1rocu.touhoulittlemaid.mixin.accessor;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Invoker("setRot")
    void tlm$setRot(float yRot, float xRot);

    @Accessor("firstTick")
    boolean tlm$firstTick();
}
