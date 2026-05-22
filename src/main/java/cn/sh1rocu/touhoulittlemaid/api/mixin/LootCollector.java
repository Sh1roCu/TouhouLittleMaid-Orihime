package cn.sh1rocu.touhoulittlemaid.api.mixin;

import cn.sh1rocu.touhoulittlemaid.api.extension.ILootContext;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.function.Consumer;

public class LootCollector implements Consumer<ItemStack> {
    private final Consumer<ItemStack> wrapped;
    private final ObjectArrayList<ItemStack> stacks = new ObjectArrayList<>();

    public LootCollector(Consumer<ItemStack> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void accept(ItemStack stack) {
        this.stacks.add(stack);
    }

    public void finish(Identifier tableId, LootContext ctx) {
        setId(tableId, stacks, ctx).forEach(wrapped);
        stacks.clear();
    }

    private static ObjectArrayList<ItemStack> setId(Identifier lootTableId, ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ((ILootContext) context).tlm$setQueriedLootTableId(lootTableId);
        return generatedLoot;
    }
}