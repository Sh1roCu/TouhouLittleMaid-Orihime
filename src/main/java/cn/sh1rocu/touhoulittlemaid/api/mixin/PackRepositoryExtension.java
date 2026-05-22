package cn.sh1rocu.touhoulittlemaid.api.mixin;

import net.minecraft.server.packs.repository.RepositorySource;

public interface PackRepositoryExtension {
    default void tlm$addPackFinder(RepositorySource packFinder) {
        throw new RuntimeException("PackRepository implementation does not support adding sources!");
    }
}