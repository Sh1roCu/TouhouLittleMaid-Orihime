package com.github.tartaricacid.touhoulittlemaid.api.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

/**
 * 用于修改女仆名称的事件。
 */
public class MaidTypeNameEvent {
    private final EntityMaid maid;
    private @Nullable Component typeName = null;

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.onMaidTypeName(event);
        }
    });

    public interface Callback {
        void onMaidTypeName(MaidTypeNameEvent event);
    }

    public MaidTypeNameEvent(EntityMaid maid) {
        this.maid = maid;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    /**
     * 当女仆类型名称为 null 时，表示回退使用默认名称。
     */
    @Nullable
    public Component getTypeName() {
        return typeName;
    }

    public void setTypeName(@Nullable Component typeName) {
        this.typeName = typeName;
    }
}