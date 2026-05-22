package com.github.tartaricacid.touhoulittlemaid.compat.aquaculture.entity;

/**
 * 没Fabric端，太好了（
 */
public class AquacultureFishingType {

}

/*public class AquacultureFishingType implements IFishingType {
    @Override
    public boolean isFishingRod(ItemStack itemStack) {
        return itemStack.getItem() instanceof AquaFishingRodItem;
    }

    @Override
    public boolean suitableFishingHook(EntityMaid maid, Level worldIn, ItemStack rod, BlockPos blockPos) {
        FluidState fluidState = worldIn.getFluidState(blockPos);
        if (fluidState.is(FluidTags.LAVA)) {
            Hook hook = AquaFishingRodItem.getHookType(rod);
            return hook.getFluids().contains(FluidTags.LAVA);
        }
        return fluidState.is(FluidTags.WATER);
    }

    @Override
    public MaidFishingHook getFishingHook(EntityMaid maid, Level level, ItemStack rod, Vec3 pos) {
        int lureSpeed = (int) (EnchantmentHelper.getFishingTimeReduction((ServerLevel) level, rod, maid) * 20.0F);
        Tier tier = Tiers.WOOD;
        if (rod.getItem() instanceof AquaFishingRodItem aquaFishingRodItem) {
            tier = aquaFishingRodItem.getTier();
        }
        if (tier == AquacultureAPI.MATS.NEPTUNIUM) {
            lureSpeed += 100;
        }
        ItemStack bait = AquaFishingRodItem.getBait(rod);
        if (!bait.isEmpty()) {
            lureSpeed += ((BaitItem) bait.getItem()).getLureSpeedModifier() * 100;
        }
        lureSpeed = Math.min(500, lureSpeed);
        int luck = EnchantmentHelper.getFishingLuckBonus((ServerLevel) level, rod, maid);
        Hook hook = AquaFishingRodItem.getHookType(rod);
        if (hook != Hooks.EMPTY && hook.getLuckModifier() > 0) {
            luck += hook.getLuckModifier();
        }
        ItemStack fishingLine = AquaFishingRodItem.getFishingLine(rod);
        ItemStack bobber = AquaFishingRodItem.getBobber(rod);
        return new AquacultureFishingHook(maid, level, luck, lureSpeed, pos, hook, fishingLine, bobber, rod);
    }
}*/
