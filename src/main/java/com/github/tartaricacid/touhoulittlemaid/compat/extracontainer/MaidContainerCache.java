package com.github.tartaricacid.touhoulittlemaid.compat.extracontainer;

import com.github.tartaricacid.touhoulittlemaid.compat.accessories.AccessoriesCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.extracontainer.accessories.AccessoriesSlotRef;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.google.common.collect.Lists;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.WeakHashMap;

/**
 * 女仆容器缓存，缓存女仆的容器列表（物品栏 + Accessories 栏上的背包）
 * 容器优先级：物品栏（永远为首个元素）> back 槽位背包 > accessories 槽位背包 > 其他槽位
 * 同槽位类型按 slotIndex 排序
 */
public class MaidContainerCache {
    private static final WeakHashMap<EntityMaid, List<ContainerRef>> CACHE = new WeakHashMap<>();

    public static List<ContainerRef> getContainers(EntityMaid maid) {
        List<ContainerRef> containers = CACHE.get(maid);
        if (containers == null) {
            containers = buildContainerRefs(maid);
            CACHE.put(maid, containers);
        }
        return containers;
    }

    public static void onEquipped(EntityMaid maid, ItemStack stack, String slotType, int slotIndex) {
        List<ContainerRef> containers = getContainers(maid);
        ContainerRef newRef = ExtraContainerManager.tryCreateSlotRef(stack, slotType, slotIndex);
        if (newRef == null || !(newRef instanceof AccessoriesSlotRef newSlotRef)) {
            return;
        }

        for (int i = 1; i < containers.size(); i++) {
            ContainerRef ref = containers.get(i);
            if (!(ref instanceof AccessoriesSlotRef slotRef)) {
                continue;
            }
            if (slotRef.slotType.equals(slotType) && slotRef.slotIndex == slotIndex) {
                return;
            }
        }

        int insertIndex = containers.size();
        for (int i = 1; i < containers.size(); i++) {
            ContainerRef ref = containers.get(i);
            if (!(ref instanceof AccessoriesSlotRef slotRef)) {
                continue;
            }
            if (newSlotRef.compareTo(slotRef) < 0) {
                insertIndex = i;
                break;
            }
        }
        containers.add(insertIndex, newSlotRef);
    }

    public static void onUnequipped(EntityMaid maid, String slotType, int slotIndex) {
        List<ContainerRef> containers = getContainers(maid);
        containers.removeIf(ref -> {
            if (ref instanceof AccessoriesSlotRef slotRef) {
                return slotRef.slotType.equals(slotType) && slotRef.slotIndex == slotIndex;
            }
            return false;
        });
    }

    public static void invalidate(EntityMaid maid) {
        CACHE.remove(maid);
    }

    public static int getCacheSize() {
        return CACHE.size();
    }

    private static List<ContainerRef> buildContainerRefs(EntityMaid maid) {
        List<ContainerRef> containers = Lists.newArrayList();

        containers.add(new MaidInventoryRef());
        if (!AccessoriesCompat.isLoadedOrEnable()) {
            return containers;
        }

        List<AccessoriesSlotRef> slotRefs = Lists.newArrayList();
        AccessoriesCapability.getOptionally(maid).ifPresent(handler -> {
            for (var entry : handler.getContainers().entrySet()) {
                String slotType = entry.getKey();
                AccessoriesContainer stacksHandler = entry.getValue();
                var stacks = stacksHandler.getAccessories();

                for (int i = 0; i < stacks.getContainerSize(); i++) {
                    ItemStack stack = stacks.getItem(i);
                    ContainerRef ref = ExtraContainerManager.tryCreateSlotRef(stack, slotType, i);
                    if (ref instanceof AccessoriesSlotRef curiosRef) {
                        slotRefs.add(curiosRef);
                    }
                }
            }
        });

        for (AccessoriesSlotRef newRef : slotRefs) {
            int insertIndex = containers.size();
            for (int i = 1; i < containers.size(); i++) {
                ContainerRef ref = containers.get(i);
                if (!(ref instanceof AccessoriesSlotRef slotRef)) {
                    continue;
                }
                if (newRef.compareTo(slotRef) < 0) {
                    insertIndex = i;
                    break;
                }
            }
            containers.add(insertIndex, newRef);
        }

        return containers;
    }
}