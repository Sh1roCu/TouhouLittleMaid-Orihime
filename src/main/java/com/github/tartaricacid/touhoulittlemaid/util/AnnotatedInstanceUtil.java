package com.github.tartaricacid.touhoulittlemaid.util;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import net.fabricmc.loader.api.EntrypointException;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Copy from https://github.com/mezz/JustEnoughItems/blob/1.21.1/Fabric/src/main/java/mezz/jei/fabric/startup/FabricPluginFinder.java
 */
public final class AnnotatedInstanceUtil {
    public static List<ILittleMaid> getModExtensions() {
        return getInstances("little_maid_extension", ILittleMaid.class);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T> List<T> getInstances(String entrypointContainerKey, Class<T> instanceClass) {
        FabricLoader fabricLoader = FabricLoader.getInstance();
        List<EntrypointContainer<T>> pluginContainers = fabricLoader.getEntrypointContainers(entrypointContainerKey, instanceClass);
        return pluginContainers.stream()
                .<T>mapMulti((entrypointContainer, consumer) -> {
                    try {
                        T entrypoint = entrypointContainer.getEntrypoint();
                        consumer.accept(entrypoint);
                    } catch (EntrypointException e) {
                        String modName;
                        try {
                            ModContainer provider = entrypointContainer.getProvider();
                            ModMetadata metadata = provider.getMetadata();
                            modName = metadata.getName();
                        } catch (RuntimeException ignored) {
                            modName = "unknown";
                        }
                        TouhouLittleMaid.LOGGER.error("{} specified an invalid entrypoint for its LittleMaid extension", modName, e);
                    }
                })
                .collect(Collectors.toList());
    }
}
