package com.github.tartaricacid.touhoulittlemaid.compat.kubejs;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.event.*;
import com.github.tartaricacid.touhoulittlemaid.client.overlay.MaidTipsOverlay;
import com.github.tartaricacid.touhoulittlemaid.compat.kubejs.event.CommonEventsPostJS;
import com.github.tartaricacid.touhoulittlemaid.compat.kubejs.event.MaidEventsJS;
import com.github.tartaricacid.touhoulittlemaid.compat.kubejs.event.compat.JadeEventsPostJS;
import com.github.tartaricacid.touhoulittlemaid.compat.kubejs.event.compat.TopEventsPostJS;
import com.github.tartaricacid.touhoulittlemaid.compat.kubejs.recipe.AltarOutputJS;
import com.github.tartaricacid.touhoulittlemaid.compat.kubejs.recipe.AltarRecipeSchema;
import com.github.tartaricacid.touhoulittlemaid.compat.kubejs.register.MaidRegisterJS;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
import com.github.tartaricacid.touhoulittlemaid.init.InitRecipes;
import com.github.tartaricacid.touhoulittlemaid.item.bauble.BaubleManager;
import com.github.tartaricacid.touhoulittlemaid.util.ItemsUtil;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.LOW;

public class ModKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void init() {
        ModKubeJSCompat.ENABLE = true;
        CommonEventsPostJS commonEventsPostJS = new CommonEventsPostJS();

        InteractMaidEvent.CALLBACK.register(LOW, commonEventsPostJS::interactMaid);

        MaidAfterEatEvent.CALLBACK.register(LOW, commonEventsPostJS::maidAfterEat);

        MaidAttackEvent.CALLBACK.register(LOW, commonEventsPostJS::maidAttack);

        MaidDamageEvent.CALLBACK.register(LOW, commonEventsPostJS::maidDamage);

        MaidDeathEvent.CALLBACK.register(LOW, commonEventsPostJS::maidDeath);

        MaidEquipEvent.CALLBACK.register(LOW, commonEventsPostJS::maidEquip);

        MaidFishedEvent.CALLBACK.register(LOW, commonEventsPostJS::maidFish);

        MaidHurtEvent.CALLBACK.register(LOW, commonEventsPostJS::maidHurt);

        MaidPickupEvent.ITEM_RESULT_PRE.register(LOW, commonEventsPostJS::maidPickupItemResultPre);

        MaidPickupEvent.ITEM_RESULT_POST.register(LOW, commonEventsPostJS::maidPickupItemResultPost);

        MaidPickupEvent.EXPERIENCE_RESULT.register(LOW, commonEventsPostJS::maidPickupExperienceResult);

        MaidPickupEvent.ARROW_RESULT.register(LOW, commonEventsPostJS::maidPickupArrowResult);

        MaidPickupEvent.POWERPOINT_RESULT.register(LOW, commonEventsPostJS::maidPickupPowerPointResult);

        MaidPlaySoundEvent.CALLBACK.register(LOW, commonEventsPostJS::maidPlaySound);

        MaidTickEvent.CALLBACK.register(LOW, commonEventsPostJS::maidTick);

        MaidTaskEnableEvent.CALLBACK.register(LOW, commonEventsPostJS::maidTaskEnable);

        MaidTamedEvent.CALLBACK.register(LOW, commonEventsPostJS::maidTamed);

        if (FabricLoader.getInstance().isModLoaded("jade")) {
            JadeEventsPostJS jadeEventsPostJS = new JadeEventsPostJS();
            AddJadeInfoEvent.CALLBACK.register(jadeEventsPostJS::addJadeInfo);
        }
        if (FabricLoader.getInstance().isModLoaded("theoneprobe")) {
            TopEventsPostJS topEventsPostJS = new TopEventsPostJS();
            // TODO fabric暂无TOP
            AddTopInfoEvent.CALLBACK.register(topEventsPostJS::addTopInfo);
        }
    }

    @Override
    public void onServerReload() {
        // 客户端部分内容重载，哎，都怪 KJS 没给客户端热重载的方法
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            MaidTipsOverlay.init();
        }
        BaubleManager.init();
        TaskManager.init();
    }

    @Override
    public void registerEvents() {
        EventGroup group = MaidEventsJS.GROUP;
        RegisterKubeJSEvent.CALLBACK.invoker().onRegisterKubeJS(new RegisterKubeJSEvent(group));
        group.register();
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.namespace(TouhouLittleMaid.MOD_ID)
                .register(BuiltInRegistries.RECIPE_SERIALIZER.getKey(InitRecipes.ALTAR_RECIPE_SERIALIZER).getPath(), AltarRecipeSchema.SCHEMA);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("MaidAltarOutput", AltarOutputJS.class);
        event.add("MaidRegister", MaidRegisterJS.class);
        event.add("MaidItemsUtil", ItemsUtil.class);
    }
}