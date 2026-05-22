package com.github.tartaricacid.touhoulittlemaid.api.event.client;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class AddClothConfigEvent {
    private final ConfigBuilder root;
    private final ConfigEntryBuilder entryBuilder;

    public AddClothConfigEvent(ConfigBuilder root, ConfigEntryBuilder entryBuilder) {
        this.root = root;
        this.entryBuilder = entryBuilder;
    }

    public ConfigBuilder getRoot() {
        return root;
    }

    public ConfigEntryBuilder getEntryBuilder() {
        return entryBuilder;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> (event) -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public interface Callback {
        void post(AddClothConfigEvent e);
    }
}
