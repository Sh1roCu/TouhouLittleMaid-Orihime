package com.github.tartaricacid.touhoulittlemaid.compat.top.provider;

public class ShrineLampProvider /*implements IBlockDisplayOverride */ {
/*    @Override
    public boolean overrideStandardInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level world, BlockState blockState, IProbeHitData data) {
        BlockEntity te = world.getBlockEntity(data.getPos());
        if (te instanceof TileEntityMaidBeacon lamp) {
            DefaultProbeInfoProvider.showStandardBlockInfo(Config.getRealConfig(), mode, probeInfo, blockState, blockState.getBlock(), world, data.getPos(), player, data);

            float storagePower = lamp.getStoragePower();
            float costPower = lamp.getEffectCost() * 900;
            probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                    .text((Component.translatable("tooltips.touhou_little_maid.maid_beacon.desc", ItemMaidBeacon.DECIMAL_FORMAT.format(storagePower))));
            if (lamp.getPotionIndex() == -1) {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .text((Component.translatable("gui.touhou_little_maid.maid_beacon.cost_power", ItemMaidBeacon.DECIMAL_FORMAT.format(0))));
            } else {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .text((Component.translatable("gui.touhou_little_maid.maid_beacon.cost_power", ItemMaidBeacon.DECIMAL_FORMAT.format(costPower))));
            }
            return true;
        }
        return false;
    }*/
}
