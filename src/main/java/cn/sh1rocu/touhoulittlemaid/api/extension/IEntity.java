package cn.sh1rocu.touhoulittlemaid.api.extension;

public interface IEntity {
    boolean isAddedToLevel();

    void onAddedToLevel();

    void onRemovedFromLevel();
}
