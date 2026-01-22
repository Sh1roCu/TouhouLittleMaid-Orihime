package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.item.ItemBoardState;
import com.github.tartaricacid.touhoulittlemaid.item.ItemFoxScroll.TrackInfo;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.CustomData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings({"deprecation"})
public class InitDataComponent {
    public static void init() {

    }

    public static final String ENTITY_ID_TAG_NAME = "id";
    public static final String OWNER_UUID_TAG_NAME = "owner_uuid";

    public static final DataComponentType<String> RECIPES_ID_TAG =
            register("recipe_id", DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                    .build());

    public static final DataComponentType<CustomData> MAID_INFO =
            register("maid_info", DataComponentType.<CustomData>builder()
                    .persistent(CustomData.CODEC)
                    .networkSynchronized(CustomData.STREAM_CODEC).
                    build());

    public static final DataComponentType<TrackInfo> TRACK_INFO =
            register("track_info", DataComponentType.<TrackInfo>builder()
                    .persistent(TrackInfo.CODEC)
                    .networkSynchronized(TrackInfo.STREAM_CODEC)
                    .build());

    public static final String MODEL_ID_TAG_NAME = "model_id";
    public static final DataComponentType<String> MODEL_ID_TAG =
            register(MODEL_ID_TAG_NAME, DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                    .build());

    public static final String MOUNTED_HEIGHT_TAG_NAME = "mounted_height";
    public static final DataComponentType<Float> MOUNTED_HEIGHT_TAG =
            register(MOUNTED_HEIGHT_TAG_NAME, DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .networkSynchronized(ByteBufCodecs.FLOAT)
                    .build());

    public static final String TAMEABLE_CAN_RIDE_TAG_NAME = "tameable_can_ride";
    public static final DataComponentType<Boolean> TAMEABLE_CAN_RIDE_TAG =
            register(TAMEABLE_CAN_RIDE_TAG_NAME, DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());

    public static final DataComponentType<Boolean> IS_NO_GRAVITY_TAG =
            register("is_no_gravity", DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());

    public static final String KAPPA_COMPASS_ACTIVITY_POS_NAME = "kappa_compass_activity_pos";
    public static final DataComponentType<Map<String, BlockPos>> KAPPA_COMPASS_ACTIVITY_POS =
            register(KAPPA_COMPASS_ACTIVITY_POS_NAME, DataComponentType.<Map<String, BlockPos>>builder()
                    .persistent(Codec.unboundedMap(Codec.STRING, BlockPos.CODEC))
                    .networkSynchronized(ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, BlockPos.STREAM_CODEC))
                    .build());

    public static final String KAPPA_COMPASS_DIMENSION_NAME = "kappa_compass_dimension";
    public static final DataComponentType<String> KAPPA_COMPASS_DIMENSION =
            register(KAPPA_COMPASS_DIMENSION_NAME, DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                    .build());

    public static final String FILTER_MODE_NAME = "item_filter_mode";
    public static final DataComponentType<Boolean> FILTER_MODE =
            register(FILTER_MODE_NAME, DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());

    public static final String IO_MODE_NAME = "item_io_mode";
    public static final DataComponentType<Boolean> IO_MODE =
            register(IO_MODE_NAME, DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());

    public static final String FILTER_LIST_TAG_NAME = "item_filter_list";
    public static final DataComponentType<CompoundTag> FILTER_LIST_TAG =
            register(FILTER_LIST_TAG_NAME, DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
                    .build());

    public static final String BINDING_POS_NAME = "binding_pos";
    public static final DataComponentType<BlockPos> BINDING_POS =
            register(BINDING_POS_NAME, DataComponentType.<BlockPos>builder()
                    .persistent(BlockPos.CODEC)
                    .networkSynchronized(BlockPos.STREAM_CODEC)
                    .build());

    public static final String SLOT_CONFIG_TAG_NAME = "slot_config_data";
    public static final DataComponentType<List<Boolean>> SLOT_CONFIG_TAG =
            register(SLOT_CONFIG_TAG_NAME, DataComponentType.<List<Boolean>>builder()
                    .persistent(Codec.BOOL.listOf())
                    .networkSynchronized(ByteBufCodecs.BOOL.apply(ByteBufCodecs.list()))
                    .build());

    public static final String STORAGE_DATA_TAG_NAME = "storage_data";
    public static final DataComponentType<CompoundTag> STORAGE_DATA_TAG =
            register(STORAGE_DATA_TAG_NAME, DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
                    .build());

    public static final String TANK_BACKPACK_TAG_NAME = "tanks";
    public static final DataComponentType<CompoundTag> TANK_BACKPACK_TAG =
            register(TANK_BACKPACK_TAG_NAME, DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
                    .build());

    public static final String SAKUYA_BELL_UUID_TAG_NAME = "sakuya_bell_uuid";
    public static final DataComponentType<UUID> SAKUYA_BELL_UUID_TAG =
            register(SAKUYA_BELL_UUID_TAG_NAME, DataComponentType.<UUID>builder()
                    .persistent(UUIDUtil.CODEC)
                    .networkSynchronized(UUIDUtil.STREAM_CODEC)
                    .build());

    public static final String SAKUYA_BELL_TIP_TAG_NAME = "sakuya_bell_tip";
    public static final DataComponentType<String> SAKUYA_BELL_TIP_TAG =
            register(SAKUYA_BELL_TIP_TAG_NAME, DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                    .build());

    public static final String SAKUYA_BELL_SHOW_TAG_NAME = "sakuya_bell_show";
    public static final DataComponentType<TrackInfo> SAKUYA_BELL_SHOW_TAG =
            register(SAKUYA_BELL_SHOW_TAG_NAME, DataComponentType.<TrackInfo>builder()
                    .persistent(TrackInfo.CODEC)
                    .networkSynchronized(TrackInfo.STREAM_CODEC)
                    .build());

    private static final String BED_COLOR_TAG_NAME = "bed_color";
    public static final DataComponentType<DyeColor> BED_COLOR_TAG =
            register(BED_COLOR_TAG_NAME, DataComponentType.<DyeColor>builder()
                    .persistent(DyeColor.CODEC)
                    .networkSynchronized(DyeColor.STREAM_CODEC)
                    .build());

    private static final String BOARD_STATE_TAG_NAME = "board_state";
    public static final DataComponentType<ItemBoardState.BoardStateInfo> BOARD_STATE_TAG =
            register(BOARD_STATE_TAG_NAME, DataComponentType.<ItemBoardState.BoardStateInfo>builder()
                    .persistent(ItemBoardState.BoardStateInfo.CODEC)
                    .networkSynchronized(ItemBoardState.BoardStateInfo.STREAM_CODEC)
                    .build());

    /**
     * 有初始主人锁定标记时，会进行 UUID 判断，避免其他玩家释放他人的初始女仆。
     * <p>
     * 默认为 Util.NIL_UUID。
     */
    private static final String INIT_MAID_OWNER = "init_maid_owner";
    public static final DataComponentType<UUID> INIT_MAID_OWNER_TAG =
            register(INIT_MAID_OWNER, DataComponentType.<UUID>builder()
                    .persistent(UUIDUtil.CODEC)
                    .networkSynchronized(UUIDUtil.STREAM_CODEC)
                    .build());

    private static <T extends DataComponentType<?>> T register(String id, T type) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), type);
    }
}
