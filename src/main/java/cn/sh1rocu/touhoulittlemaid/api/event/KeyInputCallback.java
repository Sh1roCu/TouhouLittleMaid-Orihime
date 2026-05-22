package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public interface KeyInputCallback {
    Event<KeyInputCallback> EVENT = EventFactory.createArrayBacked(KeyInputCallback.class, callbacks -> (key, scanCode, action, mods) -> {
        for (KeyInputCallback callback : callbacks) {
            callback.onKeyInput(key, scanCode, action, mods);
        }
    });

    void onKeyInput(int key, int scanCode, int action, int mods);
}