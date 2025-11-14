package com.github.tartaricacid.touhoulittlemaid.item;

import cn.sh1rocu.touhoulittlemaid.api.extension.IItemRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.tileentity.TileEntityItemStackGarageKitRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.resource.CustomPackLoader;
import com.github.tartaricacid.touhoulittlemaid.client.resource.pojo.MaidModelInfo;
import com.github.tartaricacid.touhoulittlemaid.compat.ysm.YsmCompat;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitBlocks;
import com.github.tartaricacid.touhoulittlemaid.inventory.tooltip.YsmMaidInfo;
import com.github.tartaricacid.touhoulittlemaid.util.ParseI18n;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ItemGarageKit extends BlockItem implements IItemRenderer {
    private static final String ENTITY_INFO = "EntityInfo";
    private static final String DEFAULT_ENTITY_ID = "touhou_little_maid:maid";
    private static final String DEFAULT_MODEL_ID = "touhou_little_maid:hakurei_reimu";
    private static final String ENTITY_ID_TAG_NAME = "id";
    private static final CompoundTag DEFAULT_DATA = getDefaultData();

    public ItemGarageKit() {
        super(InitBlocks.GARAGE_KIT, (new Item.Properties()).stacksTo(1));
    }

    private static boolean hasMaidData(ItemStack stack) {
        return stack.hasTag() && !Objects.requireNonNull(stack.getTag()).getCompound(ENTITY_INFO).isEmpty();
    }

    public static CompoundTag getMaidData(ItemStack stack) {
        if (hasMaidData(stack)) {
            return Objects.requireNonNull(stack.getTag()).getCompound(ENTITY_INFO);
        }
        return DEFAULT_DATA;
    }

    private static CompoundTag getDefaultData() {
        CompoundTag data = new CompoundTag();
        data.putString(ENTITY_ID_TAG_NAME, DEFAULT_ENTITY_ID);
        data.putString(EntityMaid.MODEL_ID_TAG, DEFAULT_MODEL_ID);
        // 默认数据需要强制指定 YSM 渲染为空
        data.putBoolean(EntityMaid.IS_YSM_MODEL_TAG, false);
        return data;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Component getName(ItemStack stack) {
        // 仅在客户端添加这个名称
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            // 手办名字前缀
            MutableComponent prefix = Component.translatable("block.touhou_little_maid.garage_kit.prefix");
            CompoundTag data = getMaidData(stack);

            String entityId = data.getString(ENTITY_ID_TAG_NAME);
            // 如果是其他实体，那么不需要显示 model id
            if (!entityId.equals(DEFAULT_ENTITY_ID)) {
                EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(entityId));
                return prefix.append(entityType.getDescription());
            }

            // 优先使用 YSM 模型名称
            if (YsmCompat.isInstalled()) {
                YsmMaidInfo ysmMaidInfo = YsmCompat.getYsmMaidInfo(data);
                if (ysmMaidInfo.isYsmModel()) {
                    MutableComponent name = ysmMaidInfo.name();
                    if (name == null || name.equals(Component.empty())) {
                        return prefix.append(ysmMaidInfo.modelId());
                    }
                    return prefix.append(name);
                }
            }

            // 然后才是默认模型名
            String modelId = data.getString(EntityMaid.MODEL_ID_TAG);
            MaidModelInfo info = CustomPackLoader.MAID_MODELS.getInfo(modelId).orElse(null);
            if (info != null) {
                return prefix.append(ParseI18n.parse(info.getName()));
            }
            return super.getName(stack);
        }
        return super.getName(stack);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return TileEntityItemStackGarageKitRenderer.INSTANCE.get();
    }
}