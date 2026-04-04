package com.github.tartaricacid.touhoulittlemaid.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends FabricDynamicRegistryProvider {
    public RegistryDataGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        entries.addAll(provider.lookupOrThrow(Registries.DAMAGE_TYPE));
    }

    @Override
    public String getName() {
        return "TouhouLittleMaid-Fabric Registries";
    }
}
