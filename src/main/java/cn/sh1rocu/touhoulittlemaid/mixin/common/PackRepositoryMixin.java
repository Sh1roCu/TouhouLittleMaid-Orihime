package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.mixin.PackRepositoryExtension;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.LinkedHashSet;
import java.util.Set;

@Mixin(PackRepository.class)
public class PackRepositoryMixin implements PackRepositoryExtension {
    @Mutable
    @Shadow
    @Final
    private Set<RepositorySource> sources;

    @Override
    public synchronized void tlm$addPackFinder(RepositorySource packFinder) {
        if (!(this.sources instanceof LinkedHashSet)) {
            this.sources = new LinkedHashSet<>(this.sources);
        }
        this.sources.add(packFinder);
    }
}