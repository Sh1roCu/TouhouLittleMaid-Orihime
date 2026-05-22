package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.resources.Identifier;

public interface ILootContext {
    Identifier tlm$getQueriedLootTableId();

    void tlm$setQueriedLootTableId(Identifier queriedLootTableId);
}
