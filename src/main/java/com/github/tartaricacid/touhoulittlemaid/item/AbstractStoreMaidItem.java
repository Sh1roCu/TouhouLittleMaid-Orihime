package com.github.tartaricacid.touhoulittlemaid.item;

import cn.sh1rocu.touhoulittlemaid.api.extension.IItemEntity;
import com.github.tartaricacid.touhoulittlemaid.compat.ysm.YsmCompat;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.ItemMaidTooltip;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.YsmMaidInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractStoreMaidItem extends Item implements IItemEntity {
    static final String MAID_INFO = "MaidInfo";
    static final String CUSTOM_NAME = "CustomName";

    public AbstractStoreMaidItem(Properties properties) {
        super(properties);
    }

    public static boolean hasMaidData(ItemStack stack) {
        return stack.hasTag() && !Objects.requireNonNull(stack.getTag()).getCompound(MAID_INFO).isEmpty();
    }

    public static CompoundTag getMaidData(ItemStack stack) {
        if (hasMaidData(stack)) {
            return Objects.requireNonNull(stack.getTag()).getCompound(MAID_INFO);
        }
        return new CompoundTag();
    }

    @Override
    public boolean tlm$onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.isCurrentlyGlowing()) {
            entity.setGlowingTag(true);
        }
        if (!entity.isInvulnerable()) {
            entity.setInvulnerable(true);
        }
        Vec3 position = entity.position();
        int minY = entity.level.getMinBuildHeight();
        if (position.y < minY) {
            entity.setNoGravity(true);
            entity.setDeltaMovement(Vec3.ZERO);
            entity.setPos(position.x, minY, position.z);
        }
        return IItemEntity.super.tlm$onEntityItemUpdate(stack, entity);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        CompoundTag maidData = getMaidData(stack);
        if (maidData.contains(EntityMaid.MODEL_ID_TAG, Tag.TAG_STRING)) {
            String modelId = maidData.getString(EntityMaid.MODEL_ID_TAG);
            String customName = "";
            if (maidData.contains(CUSTOM_NAME, Tag.TAG_STRING)) {
                customName = maidData.getString(CUSTOM_NAME);
            }
            // YSM 渲染相关数据
            YsmMaidInfo ysmMaidInfo = YsmCompat.getYsmMaidInfo(maidData);
            return Optional.of(new ItemMaidTooltip(modelId, customName, ysmMaidInfo));
        }
        return Optional.empty();
    }
}