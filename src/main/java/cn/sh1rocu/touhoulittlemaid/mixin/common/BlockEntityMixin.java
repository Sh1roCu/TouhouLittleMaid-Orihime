package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.IBlockEntityPersistentData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements IBlockEntityPersistentData {
    @Unique
    private CompoundTag tlm$persistentData = null;

    @Inject(method = "loadAdditional", at = @At("RETURN"))
    private void tlm$loadAdditional(ValueInput input, CallbackInfo ci) {
        input.read(PERSISTENT_DATA, CompoundTag.CODEC).ifPresent(neoData -> tlm$persistentData = neoData);
    }

    @Inject(method = "saveAdditional", at = @At("HEAD"))
    private void tlm$saveAdditional(ValueOutput output, CallbackInfo ci) {
        if (tlm$persistentData != null) {
            output.store(PERSISTENT_DATA, CompoundTag.CODEC, tlm$persistentData.copy());
        }
    }

    @Override
    public CompoundTag tlm$getPersistentData() {
        if (tlm$persistentData == null) {
            tlm$persistentData = new CompoundTag();
        }
        return tlm$persistentData;
    }
}