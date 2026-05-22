package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.ILootTable;
import cn.sh1rocu.touhoulittlemaid.api.extension.ILootTableBuilder;
import cn.sh1rocu.touhoulittlemaid.api.mixin.LootCollector;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;
import java.util.function.Consumer;

// Porting_Lib
@Mixin(value = LootTable.class, priority = 50_000)
// high priority to make sure the WrapMethod below catches most injections
public class LootTableMixin implements ILootTable {
    @Unique
    private Identifier tlm$lootTableId;

    @Override
    public void tlm$setLootTableId(final Identifier id) {
        if (this.tlm$lootTableId != null)
            throw new IllegalStateException("Attempted to rename loot table from '" + this.tlm$lootTableId + "' to '" + id + "': this is not supported");
        this.tlm$lootTableId = Objects.requireNonNull(id);
    }

    @Override
    public Identifier tlm$getLootTableId() {
        return this.tlm$lootTableId;
    }

    @WrapMethod(method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V")
    private void finishCollectingLoot(LootContext lootContext, Consumer<ItemStack> consumer, Operation<Void> original) {
        LootCollector lootCollector = new LootCollector(consumer);
        original.call(lootContext, lootCollector);
        lootCollector.finish(this.tlm$getLootTableId(), lootContext);
    }

    @Mixin(LootTable.Builder.class)
    public static class BuilderMixin implements ILootTableBuilder {
        @Unique
        private Identifier tlm$id;

        @Override
        public void tlm$setId(Identifier id) {
            this.tlm$id = id;
        }

        @ModifyReturnValue(method = "build", at = @At("RETURN"))
        private LootTable tlm$addId(LootTable table) {
            if (this.tlm$id != null)
                ((ILootTable) table).tlm$setLootTableId(this.tlm$id);
            return table;
        }
    }
}