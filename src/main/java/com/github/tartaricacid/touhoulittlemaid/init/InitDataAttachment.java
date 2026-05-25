package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.data.ChatTokensAttachment;
import com.github.tartaricacid.touhoulittlemaid.data.MaidNumAttachment;
import com.github.tartaricacid.touhoulittlemaid.data.PowerAttachment;
import com.github.tartaricacid.touhoulittlemaid.entity.data.ProfileData;
import com.github.tartaricacid.touhoulittlemaid.entity.data.TaskData;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public interface InitDataAttachment {
    static void init() {

    }

    // 玩家相关数据
    AttachmentType<MaidNumAttachment> MAID_NUM = MaidNumAttachment.TYPE;
    AttachmentType<PowerAttachment> POWER_NUM = PowerAttachment.TYPE;
    AttachmentType<ChatTokensAttachment> CHAT_TOKENS = ChatTokensAttachment.TYPE;

    // 女仆相关数据

    // 模型和声音包 ID
    AttachmentType<ProfileData> PROFILE = ProfileData.TYPE;
    // 工作模式相关
    AttachmentType<TaskData> TASK = TaskData.TYPE;

}


