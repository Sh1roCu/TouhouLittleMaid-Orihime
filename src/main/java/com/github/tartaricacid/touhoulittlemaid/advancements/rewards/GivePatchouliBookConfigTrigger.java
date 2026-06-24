package com.github.tartaricacid.touhoulittlemaid.advancements.rewards;

import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MiscConfig;
import com.github.tartaricacid.touhoulittlemaid.init.InitTrigger;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class GivePatchouliBookConfigTrigger extends SimpleCriterionTrigger<GivePatchouliBookConfigTrigger.Instance> {
    public static Criterion<GivePatchouliBookConfigTrigger.Instance> create() {
        Instance instance = new Instance(Optional.empty());
        return InitTrigger.GIVE_PATCHOULI_BOOK_CONFIG.createCriterion(instance);
    }

    public void trigger(ServerPlayer serverPlayer) {
        super.trigger(serverPlayer, _ -> MiscConfig.GIVE_PATCHOULI_BOOK.get());
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<GivePatchouliBookConfigTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(GivePatchouliBookConfigTrigger.Instance::player))
                .apply(instance, GivePatchouliBookConfigTrigger.Instance::new));
    }
}
