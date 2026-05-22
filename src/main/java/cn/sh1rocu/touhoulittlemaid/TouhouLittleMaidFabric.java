package cn.sh1rocu.touhoulittlemaid;

import cn.sh1rocu.touhoulittlemaid.api.event.*;
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
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.config.ModConfig;

public class TouhouLittleMaidFabric implements ModInitializer {
    public static final Identifier HIGHEST = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_highest_priority");
    public static final Identifier HIGH = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_high_priority");
    // NORMAL用Fabric的DEFAULT
    // public static final Identifier NORMAL = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_normal_priority");
    public static final Identifier LOW = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_low_priority");
    public static final Identifier LOWEST = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_lowest_priority");

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
        ConfigRegistry.INSTANCE.register(TouhouLittleMaid.MOD_ID, ModConfig.Type.COMMON, GeneralConfig.getConfigSpec());
        ConfigRegistry.INSTANCE.register(TouhouLittleMaid.MOD_ID, ModConfig.Type.SERVER, ServerConfig.init());
    }

    private void subscribeEvents() {
        EntityDeathEvent.onEntityDeath();
        EntityDeathEvent.onPlayerCloned();
        PotentialSpawnsEvent.CALLBACK.register(MobSpawnInfoRegistry::addMobSpawnInfo);
        UseItemCallback.EVENT.register(CancelSaddleMaidEvent::onItemRightClick);
        UseEntityCallback.EVENT.register(CopyEntityIdEvent::copyEntityId);
        UseEntityCallback.EVENT.register(InstallChairEvent::onPlayerEntityInteract);
        ServerPlayerEvents.JOIN.register(EnterServerEvent::onAttachCapabilityEvent);
        ProjectileImpactEvent.CALLBACK.register(EntityHurtEvent::onArrowImpact);
        ServerEntityEvents.ENTITY_LOAD.register(EntityJoinWorldEvent::onCreeperJoinWorld);
        ServerEntityEvents.ENTITY_LOAD.register(EntityJoinWorldEvent::onAnimalJoinWorld);
        ServerEntityEvents.ENTITY_LOAD.register(EntityJoinWorldEvent::onPlayerJoinWorld);
        ModConfigEvents.loading(TouhouLittleMaid.MOD_ID).register(MaidMealRegConfigEvent::onEvent);
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
