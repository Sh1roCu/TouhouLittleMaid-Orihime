package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;

import java.util.function.Consumer;

public class AddPackFindersEvent {
    private final PackType packType;
    private final Consumer<RepositorySource> sources;
    private final boolean trusted;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback c : callbacks)
            c.onAddPackFinders(event);
    });

    public interface Callback {
        void onAddPackFinders(AddPackFindersEvent event);
    }

    public AddPackFindersEvent(PackType packType, Consumer<RepositorySource> sources, boolean trusted) {
        this.packType = packType;
        this.sources = sources;
        this.trusted = trusted;
    }

    public void addRepositorySource(RepositorySource source) {
        sources.accept(source);
    }

    public PackType getPackType() {
        return packType;
    }

    public boolean isTrusted() {
        return trusted;
    }
}