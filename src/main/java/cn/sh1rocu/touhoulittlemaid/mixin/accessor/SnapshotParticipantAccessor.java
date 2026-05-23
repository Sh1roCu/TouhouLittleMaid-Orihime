package cn.sh1rocu.touhoulittlemaid.mixin.accessor;

import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(SnapshotParticipant.class)
public interface SnapshotParticipantAccessor<T> {
    @Accessor("snapshots")
    List<T> tlm$snapshots();
}
