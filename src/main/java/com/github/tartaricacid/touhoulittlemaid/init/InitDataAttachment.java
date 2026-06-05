package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.data.ChatTokensAttachment;
import com.github.tartaricacid.touhoulittlemaid.data.MaidNumAttachment;
import com.github.tartaricacid.touhoulittlemaid.data.PowerAttachment;
import com.github.tartaricacid.touhoulittlemaid.entity.data.*;
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
    // 饥饿值、好感度、经验和雷击状态
    AttachmentType<StatsData> STATS = StatsData.TYPE;
    // 工作模式相关
    AttachmentType<TaskData> TASK = TaskData.TYPE;
    // 动画状态相关
    AttachmentType<AnimationData> ANIMATION = AnimationData.TYPE;
    // 女仆行为配置
    AttachmentType<ConfigData> CONFIG = ConfigData.TYPE;
    // 攻击目标列表
    AttachmentType<AttackListData> ATTACK_LIST = AttackListData.TYPE;
    // 背包类型
    AttachmentType<BackpackData> BACKPACK = BackpackData.TYPE;
    // 对弈记录和当前对弈状态
    AttachmentType<GameData> GAME = GameData.TYPE;
}
