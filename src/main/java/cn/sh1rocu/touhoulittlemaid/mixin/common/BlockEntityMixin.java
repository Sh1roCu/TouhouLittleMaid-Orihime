package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.IBlockEntityPersistentData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
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
    private void tlm$loadAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (tag.contains(PERSISTENT_DATA, Tag.TAG_COMPOUND)) {
            tlm$persistentData = tag.getCompound(PERSISTENT_DATA);
        }
    }

    @Inject(method = "saveAdditional", at = @At("RETURN"))
    private void tlm$saveAdditional(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo ci) {
        if (tlm$persistentData != null) {
            tag.put(PERSISTENT_DATA, tlm$persistentData);
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