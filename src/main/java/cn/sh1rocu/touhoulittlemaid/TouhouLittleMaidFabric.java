package cn.sh1rocu.touhoulittlemaid;

import cn.sh1rocu.touhoulittlemaid.api.event.*;
import cn.sh1rocu.touhoulittlemaid.api.extension.IBedBlock;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.event.*;
import com.github.tartaricacid.touhoulittlemaid.config.GeneralConfig;
import com.github.tartaricacid.touhoulittlemaid.config.ServerConfig;
import com.github.tartaricacid.touhoulittlemaid.debug.event.DebugStickClickEvent;
import com.github.tartaricacid.touhoulittlemaid.debug.target.SendMaidDebugDataEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.RandomEmoji;
import com.github.tartaricacid.touhoulittlemaid.event.*;
import com.github.tartaricacid.touhoulittlemaid.event.food.ConvertFoodEatenEvent;
import com.github.tartaricacid.touhoulittlemaid.event.food.RemainFoodEatenEvent;
import com.github.tartaricacid.touhoulittlemaid.event.maid.*;
import com.github.tartaricacid.touhoulittlemaid.init.registry.CommonRegistry;
import com.github.tartaricacid.touhoulittlemaid.init.registry.CompatRegistry;
import com.github.tartaricacid.touhoulittlemaid.init.registry.DatapackRegistry;
import com.github.tartaricacid.touhoulittlemaid.init.registry.MobSpawnInfoRegistry;
import com.github.tartaricacid.touhoulittlemaid.item.ItemSubstituteJizo;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.neoforged.fml.config.ModConfig;

public class TouhouLittleMaidFabric implements ModInitializer {
    public static final ResourceLocation HIGHEST = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_highest_priority");
    public static final ResourceLocation HIGH = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_high_priority");
    // NORMAL用Fabric的DEFAULT
    // public static final ResourceLocation NORMAL = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_normal_priority");
    public static final ResourceLocation LOW = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_low_priority");
    public static final ResourceLocation LOWEST = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_lowest_priority");

    @Override
    public void onInitialize() {
        // AI模块初始化较快，需要最优先加载config，否则ConfigProxySelector的config字段可能为null
        registerConfiguration();
        CommonRegistry.onSetupEvent();
        TouhouLittleMaid.commonSetup();
        CompatRegistry.onEnqueue();
        DatapackRegistry.onAddReloadListenerEvent();

        subscribeEvents();
        subscribeDebugEvents();
    }

    private static void registerConfiguration() {
        NeoForgeConfigRegistry.INSTANCE.register(TouhouLittleMaid.MOD_ID, ModConfig.Type.COMMON, GeneralConfig.getConfigSpec());
        NeoForgeConfigRegistry.INSTANCE.register(TouhouLittleMaid.MOD_ID, ModConfig.Type.SERVER, ServerConfig.init());
    }

    private void subscribeEvents() {
        EntitySleepEvents.SET_BED_OCCUPATION_STATE.register((entity, sleepingPos, bedState, occupied) -> {
            if (bedState.getBlock() instanceof IBedBlock bedBlock && bedBlock.tlm$isBed(bedState, entity.level(), sleepingPos, entity)) {
                entity.level().setBlock(sleepingPos, bedState.setValue(BedBlock.OCCUPIED, true), 3);
                return true;
            }
            return false;
        });
        EntitySleepEvents.MODIFY_SLEEPING_DIRECTION.register((entity, sleepingPos, direction) -> {
            var bedState = entity.level().getBlockState(sleepingPos);
            if (bedState.getBlock() instanceof IBedBlock bedBlock && bedBlock.tlm$isBed(bedState, entity.level(), sleepingPos, entity)) {
                return bedState.getValue(HorizontalDirectionalBlock.FACING);
            }
            return direction;
        });
        EntitySleepEvents.ALLOW_BED.register((entity, sleepingPos, bedState, vanillaResult) -> {
            if (bedState.getBlock() instanceof IBedBlock bedBlock && bedBlock.tlm$isBed(bedState, entity.level(), sleepingPos, entity)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
        EntityDeathEvent.onEntityDeath();
        EntityDeathEvent.onPlayerCloned();
        PotentialSpawnsEvent.CALLBACK.register(MobSpawnInfoRegistry::addMobSpawnInfo);
        UseItemCallback.EVENT.register(CancelSaddleMaidEvent::onItemRightClick);
        UseEntityCallback.EVENT.register(CopyEntityIdEvent::copyEntityId);
        UseEntityCallback.EVENT.register(InstallChairEvent::onPlayerEntityInteract);
        PlayerLoggedInEvent.CALLBACK.register(EnterServerEvent::onAttachCapabilityEvent);
        ProjectileImpactEvent.CALLBACK.register(EntityHurtEvent::onArrowImpact);
        EntityJoinLevelEvent.CALLBACK.register(EntityJoinWorldEvent::onCreeperJoinWorld);
        EntityJoinLevelEvent.CALLBACK.register(EntityJoinWorldEvent::onAnimalJoinWorld);
        EntityJoinLevelEvent.CALLBACK.register(EntityJoinWorldEvent::onPlayerJoinWorld);
        NeoForgeModConfigEvents.loading(TouhouLittleMaid.MOD_ID).register(MaidMealRegConfigEvent::onEvent);
        EntityTrackingEvents.START_TRACKING.register(MaidTrackEvent::onTrackingPlayer);
        MaidAfterEatEvent.CALLBACK.register(ConvertFoodEatenEvent::onAfterMaidEat);
        MaidAfterEatEvent.CALLBACK.register(RemainFoodEatenEvent::onAfterMaidEat);
        InteractMaidEvent.CALLBACK.register(ApplyGoldenAppleEvent::onInteractMaid);
        InteractMaidEvent.CALLBACK.register(ApplyPotionEffectEvent::onInteractMaid);
        InteractMaidEvent.CALLBACK.register(LOW, DismountMaidEvent::onInteract);
        InteractMaidEvent.CALLBACK.register(GetExpBottleEvent::onInteract);
        InteractMaidEvent.CALLBACK.register(HandleBackpackEvent::onInteractMaid);
        InteractMaidEvent.CALLBACK.register(MaidAreaClickEvent::onInteract);
        MaidDeathEvent.CALLBACK.register(MaidDeathFavorability::onDeath);
        FarmlandTrampleEvent.CALLBACK.register(MaidFarmlandTrample::onFarmlandTrample);
        EntityMountEvent.CALLBACK.register(MaidMountEvent::onMaidMount);
        LivingEntityUseItemFinishEvent.CALLBACK.register(PotionItemUse::onMaidPotionItemUse);
        InteractMaidEvent.CALLBACK.register(SaddleMaidEvent::onInteract);
        InteractMaidEvent.CALLBACK.register(SlabClickEvent::onInteract);
        InteractMaidEvent.CALLBACK.register(LOWEST, SwitchSittingEvent::onInteractMaid);
        InteractMaidEvent.CALLBACK.register(UseFavorabilityToolEvent::onInteract);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            InteractMaidEvent.CALLBACK.register(UseNameTagEvent::onInteractServer);
        }
        InteractMaidEvent.CALLBACK.register(ItemSubstituteJizo::onEntityInteract);

        MaidDamageEvent.CALLBACK.register(LOWEST, RandomEmoji::addHurtChatText);

        MaidFavorabilityLevelChangeEvent.CALLBACK.register(MaidDropBaubleEvent::onFavorabilityLevelChange);
    }

    private static void subscribeDebugEvents() {
        InteractMaidEvent.CALLBACK.register(DebugStickClickEvent::onInteract);
        PlayerTickEvent.START.register(SendMaidDebugDataEvent::onPlayerTick);
    }
}
