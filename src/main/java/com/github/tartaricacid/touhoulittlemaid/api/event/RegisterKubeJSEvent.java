package com.github.tartaricacid.touhoulittlemaid.api.event;

// import dev.latvian.mods.kubejs.event.EventGroup;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

// fabric1.21.1暂无kjs移植

/**
 * 方便其他附属模将自己的事件注册到 KubeJS 的 MaidEvents 名下
 */
public class RegisterKubeJSEvent {
    // private final EventGroup group;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.onRegisterKubeJS(event);
        }
    });

    public interface Callback {
        void onRegisterKubeJS(RegisterKubeJSEvent event);
    }

//    public RegisterKubeJSEvent(EventGroup group) {
//        this.group = group;
//    }

//    public EventGroup getGroup() {
//        return group;
//    }
}