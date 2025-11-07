package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.util.forge.CraftingHelper;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {
    @Inject(method = "itemStackFromJson", at = @At("HEAD"), cancellable = true)
    private static void tlm$$itemStackFromJson(JsonObject json, CallbackInfoReturnable<ItemStack> cir) {
        if (json.has("nbt")) {
            cir.setReturnValue(CraftingHelper.getItemStack(json, true, true));
        }
    }
}