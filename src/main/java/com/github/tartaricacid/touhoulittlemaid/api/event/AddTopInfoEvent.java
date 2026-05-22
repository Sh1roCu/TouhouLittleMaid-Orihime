package com.github.tartaricacid.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.*;

/**
 * Fabric暂无TOP
 */
public class AddTopInfoEvent {
//    private final EntityMaid maid;
//    private final ProbeMode probeMode;
//    private final IProbeInfo probeInfo;
//    private final IProbeHitEntityData hitEntityData;
//
//    public AddTopInfoEvent(EntityMaid maid, ProbeMode probeMode, IProbeInfo probeInfo, IProbeHitEntityData hitEntityData) {
//        this.maid = maid;
//        this.probeMode = probeMode;
//        this.probeInfo = probeInfo;
//        this.hitEntityData = hitEntityData;
//    }
//
//    public EntityMaid getMaid() {
//        return maid;
//    }
//
//    public ProbeMode getProbeMode() {
//        return probeMode;
//    }
//
//    public IProbeInfo getProbeInfo() {
//        return probeInfo;
//    }
//
//    public IProbeHitEntityData getHitEntityData() {
//        return hitEntityData;
//    }

    public static final Event<Callback> CALLBACK = EventFactory.createWithPhases(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    }, HIGHEST, HIGH, Event.DEFAULT_PHASE, LOW, LOWEST);

    public interface Callback {
        void post(AddTopInfoEvent event);
    }
}
