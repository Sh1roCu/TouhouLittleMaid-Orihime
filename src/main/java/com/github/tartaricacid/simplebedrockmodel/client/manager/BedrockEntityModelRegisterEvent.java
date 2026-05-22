package com.github.tartaricacid.simplebedrockmodel.client.manager;

import com.github.tartaricacid.simplebedrockmodel.client.bedrock.AbstractBedrockEntityModel;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

import java.io.InputStream;
import java.util.function.Function;

public class BedrockEntityModelRegisterEvent<T extends AbstractBedrockEntityModel<? extends Entity>> {
    private final BedrockEntityModelSet<T> modelSet;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public interface Callback {
        @SuppressWarnings("rawtypes")
        void post(BedrockEntityModelRegisterEvent event);
    }

    public BedrockEntityModelRegisterEvent(BedrockEntityModelSet<T> modelSet) {
        this.modelSet = modelSet;
    }

    public void register(Identifier location, Function<InputStream, T> function) {
        this.modelSet.addModel(location, function);
    }
}