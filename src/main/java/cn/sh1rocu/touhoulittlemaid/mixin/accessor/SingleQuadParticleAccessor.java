package cn.sh1rocu.touhoulittlemaid.mixin.accessor;

import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SingleQuadParticle.class)
public interface SingleQuadParticleAccessor {
    @Invoker("setSprite")
    void tlm$setSprite(TextureAtlasSprite sprite);
}
