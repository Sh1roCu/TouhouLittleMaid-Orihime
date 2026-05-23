package cn.sh1rocu.touhoulittlemaid.util.transfer;

import cn.sh1rocu.touhoulittlemaid.mixin.accessor.SnapshotParticipantAccessor;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;

public abstract class SnapshotJournal<T> extends SnapshotParticipant<T> {
    private T originalState;

    @Override
    protected void onFinalCommit() {
        T originalState = this.originalState;
        this.originalState = null;
        onRootCommit(originalState);
        releaseSnapshot(originalState);
    }

    protected void onRootCommit(T originalState) {
    }

    @Override
    public void onClose(TransactionContext transaction, TransactionContext.Result result) {
        int currentDepth = transaction.nestingDepth();

        var snapshots = ((SnapshotParticipantAccessor<T>) this).tlm$snapshots();

        // Get and remove the relevant snapshot.
        T snapshot = snapshots.remove(currentDepth);

        if (result.wasAborted()) {
            // If the transaction was aborted, we just revert to the state of the snapshot.
            readSnapshot(snapshot);
            releaseSnapshot(snapshot);
        } else if (currentDepth <= 0) {
            // The transaction is the root.
            if (originalState == null) {
                originalState = snapshot;
                transaction.addOuterCloseCallback(this);
            } else {
                // If originalState was not null, it means that an onRootCommit callback is already scheduled.
                // This means that this journal got modified in a transaction opened from some onRootCommit callback.
                // In this case we just wait for the already-registered callback to run.
                releaseSnapshot(snapshot);
            }
        } else if (snapshots.get(currentDepth - 1) == null) {
            // No snapshot yet, so move the snapshot one depth up.
            snapshots.set(currentDepth - 1, snapshot);
            // This is the first snapshot at this level: we need to add the closing journal to the previous depth.
            transaction.getOpenTransaction(currentDepth - 1).addCloseCallback(this);
        } else {
            // There is already an older snapshot at the depth above, just release the newer one.
            releaseSnapshot(snapshot);
        }
    }
}
