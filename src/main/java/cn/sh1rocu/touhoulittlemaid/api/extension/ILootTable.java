package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.resources.Identifier;

public interface ILootTable {
    Identifier tlm$getLootTableId();

    void tlm$setLootTableId(Identifier id);
}