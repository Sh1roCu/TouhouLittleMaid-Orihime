package cn.sh1rocu.touhoulittlemaid.api.event;

public class CancellableEvent {
    boolean isCanceled = false;

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }

    public boolean isCanceled() {
        return this.isCanceled;
    }
}