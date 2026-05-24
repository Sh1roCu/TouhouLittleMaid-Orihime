package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.input.KeyEvent;

public interface KeyInputCallback {
    Event<KeyInputCallback> EVENT = EventFactory.createArrayBacked(KeyInputCallback.class, callbacks -> (key, event) -> {
        for (KeyInputCallback callback : callbacks) {
            callback.onKeyInput(key, event);
        }
    });

    void onKeyInput(int action, KeyEvent event);
}