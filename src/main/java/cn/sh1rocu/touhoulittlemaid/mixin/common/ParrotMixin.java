package cn.sh1rocu.touhoulittlemaid.mixin.common;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.parrot.Parrot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Parrot.class)
public class ParrotMixin {
    @Shadow
    @Final
    private static Map<EntityType<?>, SoundEvent> MOB_SOUND_MAP;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void tlm$addSound(CallbackInfo ci) {
        MOB_SOUND_MAP.put(EntityMaid.TYPE, InitSounds.MAID_IDLE);
    }
}
