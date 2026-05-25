package com.github.tartaricacid.touhoulittlemaid.compat.sbackpack;

import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;

// TODO: wait 26.1
public class BackpackRightClickMaidEvent {
    public static void onClickMaid(InteractMaidEvent event) {
//        Player player = event.getPlayer();
//        EntityMaid maid = event.getMaid();
//        ItemStack stack = event.getStack();
//        if (!player.isShiftKeyDown()) {
//            return;
//        }
//        if (!(stack.getItem() instanceof BackpackItem)) {
//            return;
//        }
//        int maidXp = maid.getExperience();
//        if (maidXp <= 0) {
//            return;
//        }
//        IBackpackWrapper backpack = BackpackWrapper.fromStack(stack);
//        UpgradeHandler handler = backpack.getUpgradeHandler();
//        if (!handler.hasUpgrade(XpPumpUpgradeItem.TYPE) || !handler.hasUpgrade(TankUpgradeItem.TYPE)) {
//            return;
//        }
//        backpack.getFluidHandler().ifPresent(fluid -> {
//            int count = XpHelper.experienceToLiquid(maidXp);
//            try (Transaction tx = Transaction.openOuter()) {
//                long filled = fluid.insert(ModFluids.EXPERIENCE_TAG, count, ModFluids.XP_STILL.get(), tx, true);
//                if (filled > 0) {
//                    maid.setExperience(maidXp - (int) XpHelper.liquidToExperience(filled));
//                }
//                event.setCanceled(true);
//                tx.commit();
//            }
//        });
    }
}
