package cn.sh1rocu.touhoulittlemaid.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PotentialSpawnsEvent extends CancellableEvent {
    private final LevelAccessor level;
    private final MobCategory mobcategory;
    private final BlockPos pos;
    private @Nullable Builder<MobSpawnSettings.SpawnerData> list;
    private List<Weighted<MobSpawnSettings.SpawnerData>> view;

    public PotentialSpawnsEvent(LevelAccessor level, MobCategory category, BlockPos pos, WeightedList<MobSpawnSettings.SpawnerData> oldList) {
        this.level = level;
        this.pos = pos;
        this.mobcategory = category;
        this.list = null;
        this.view = oldList.unwrap();
    }

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public LevelAccessor getLevel() {
        return level;
    }

    public MobCategory getMobCategory() {
        return mobcategory;
    }

    public BlockPos getPos() {
        return pos;
    }

    public List<Weighted<MobSpawnSettings.SpawnerData>> getSpawnerDataList() {
        return view;
    }

    private void makeList() {
        if (list == null) {
            list = new Builder<>();
            list.addAll(view);
            view = list.getList();
        }
    }

    public void addSpawnerData(Weighted<MobSpawnSettings.SpawnerData> data) {
        makeList();
        list.add(data);
    }

    public void removeSpawnerData(Weighted<MobSpawnSettings.SpawnerData> data, int weight) {
        makeList();
        list.remove(data);
    }

    public interface Callback {
        void post(PotentialSpawnsEvent event);
    }

    @SuppressWarnings("all")
    private static class Builder<E> {
        private final List<Weighted<E>> result = new ArrayList<>();

        public Builder<E> add(E item) {
            return this.add(item, 1);
        }

        public Builder<E> add(E item, int weight) {
            this.result.add(new Weighted<>(item, weight));
            return this;
        }

        public Builder<E> add(Weighted<E> value) {
            this.result.add(value);
            return this;
        }

        public Builder<E> addAll(WeightedList<E> values) {
            return this.addAll(values.unwrap());
        }

        public Builder<E> addAll(Collection<Weighted<E>> values) {
            this.result.addAll(values);
            return this;
        }

        public Builder<E> remove(Weighted<E> value) {
            this.result.remove(value);
            return this;
        }

        public Builder<E> remove(E value) {
            this.result.removeIf(weighted -> weighted.value().equals(value));
            return this;
        }

        public Builder<E> removeIf(java.util.function.Predicate<Weighted<E>> filter) {
            this.result.removeIf(filter);
            return this;
        }

        public List<Weighted<E>> getList() {
            return java.util.Collections.unmodifiableList(this.result);
        }

        public WeightedList<E> build() {
            return WeightedList.of(this.result);
        }
    }
}
