package cn.sh1rocu.touhoulittlemaid.mixin.accessor;

import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.gen.Accessor;

@org.spongepowered.asm.mixin.Mixin(net.minecraft.client.particle.Particle.class)
public interface ParticleAccessor {
    @Accessor("level")
    ClientLevel tlm$getLevel();
}
