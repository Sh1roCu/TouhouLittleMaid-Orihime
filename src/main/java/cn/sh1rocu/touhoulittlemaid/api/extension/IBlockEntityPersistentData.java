package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.nbt.CompoundTag;

public interface IBlockEntityPersistentData {
    // 直接适配，懒得用别的名了（，如果要移植到1.20记得改成ForgeData
    String PERSISTENT_DATA = "NeoForgeData";

    default CompoundTag tlm$getPersistentData() {
        throw new RuntimeException();
    }
}