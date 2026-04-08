package com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.backpack;

import com.github.tartaricacid.touhoulittlemaid.client.gui.widget.button.BaubleButton;
import com.github.tartaricacid.touhoulittlemaid.compat.accessories.client.CuriosButton;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.TabIndex;
import com.github.tartaricacid.touhoulittlemaid.network.message.OpenMaidGuiMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public interface IBackpackContainerScreen {
    default BaubleButton getBaubleButton(EntityMaid maid, int leftPos, int topPos) {
        return new BaubleButton(leftPos, topPos, false, btn ->
                ClientPlayNetworking.send(OpenMaidGuiMessage.ID, OpenMaidGuiMessage.encode(maid.getId(), TabIndex.BAUBLE)));
    }


    default CuriosButton getCuriosButton(EntityMaid maid, int leftPos, int topPos) {
        return new CuriosButton(leftPos, topPos, false, btn -> {
            ClientPlayNetworking.send(OpenMaidGuiMessage.ID, OpenMaidGuiMessage.encode(maid.getId(), TabIndex.CURIOS));
        });
    }
}
