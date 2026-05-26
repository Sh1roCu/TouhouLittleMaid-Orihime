package cn.sh1rocu.touhoulittlemaid.util.compat.tacz;

public class ItemHandlerUtil {
//    public static int findAndExtractInventoryAmmo(IItemHandler itemHandler, ItemStack gunItem, int needAmmoCount) {
//        int cnt = needAmmoCount;
//        // 背包检查
//        for (int i = 0; i < itemHandler.getSlots(); i++) {
//            ItemStack checkAmmoStack = itemHandler.getStackInSlot(i);
//            if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(gunItem, checkAmmoStack)) {
//                ItemStack extractItem = itemHandler.extractItem(i, cnt, false);
//                cnt = cnt - extractItem.getCount();
//                if (cnt <= 0) {
//                    break;
//                }
//            }
//            if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(gunItem, checkAmmoStack)) {
//                int boxAmmoCount = iAmmoBox.getAmmoCount(checkAmmoStack);
//                int extractCount = Math.min(boxAmmoCount, cnt);
//                int remainCount = boxAmmoCount - extractCount;
//                iAmmoBox.setAmmoCount(checkAmmoStack, remainCount);
//                if (remainCount <= 0) {
//                    iAmmoBox.setAmmoId(checkAmmoStack, DefaultAssets.EMPTY_AMMO_ID);
//                }
//                cnt = cnt - extractCount;
//                if (cnt <= 0) {
//                    break;
//                }
//            }
//        }
//        return needAmmoCount - cnt;
//    }
}
