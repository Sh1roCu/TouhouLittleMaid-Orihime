package cn.sh1rocu.touhoulittlemaid.util.enchant;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentUtil {
    public static boolean canEnchant(ItemStack stack, ResourceKey<Enchantment> key, RegistryAccess registryAccess) {
        Registry<Enchantment> registry = registryAccess.registryOrThrow(Registries.ENCHANTMENT);
        Enchantment enchantment = registry.get(key);
        return (enchantment != null && enchantment.canEnchant(stack)) ||
                stack.canBeEnchantedWith(registry.getHolder(key).orElseThrow(), EnchantingContext.ACCEPTABLE);
    }
}
