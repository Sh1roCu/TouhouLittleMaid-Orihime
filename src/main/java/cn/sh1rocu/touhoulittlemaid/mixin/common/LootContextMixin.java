package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.ILootContext;
import com.github.tartaricacid.touhoulittlemaid.init.InitLootModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootContext.class)
public abstract class LootContextMixin implements ILootContext {
    @Unique
    private ResourceLocation tlm$queriedLootTableId;

    @Override
    public void tlm$setQueriedLootTableId(ResourceLocation queriedLootTableId) {
        if (this.tlm$queriedLootTableId == null && queriedLootTableId != null)
            this.tlm$queriedLootTableId = queriedLootTableId;
    }

    @Override
    public ResourceLocation tlm$getQueriedLootTableId() {
        return this.tlm$queriedLootTableId == null ? InitLootModifier.UNKNOWN_LOOT_TABLE : this.tlm$queriedLootTableId;
    }
}