package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class InitSounds {
    public static void init() {

    }

    public static final SoundEvent MAID_IDLE = registerSound("maid.mode.idle");
    public static final SoundEvent MAID_ATTACK = registerSound("maid.mode.attack");
    public static final SoundEvent MAID_RANGE_ATTACK = registerSound("maid.mode.range_attack");
    public static final SoundEvent MAID_DANMAKU_ATTACK = registerSound("maid.mode.danmaku_attack");
    public static final SoundEvent MAID_FARM = registerSound("maid.mode.farm");
    public static final SoundEvent MAID_FEED = registerSound("maid.mode.feed");
    public static final SoundEvent MAID_SHEARS = registerSound("maid.mode.shears");
    public static final SoundEvent MAID_MILK = registerSound("maid.mode.milk");
    public static final SoundEvent MAID_REMOVE_SNOW = registerSound("maid.mode.snow");
    public static final SoundEvent MAID_TORCH = registerSound("maid.mode.torch");
    public static final SoundEvent MAID_FEED_ANIMAL = registerSound("maid.mode.feed_animal");
    public static final SoundEvent MAID_EXTINGUISHING = registerSound("maid.mode.extinguishing");
    public static final SoundEvent MAID_BREAK = registerSound("maid.mode.break");
    public static final SoundEvent MAID_FURNACE = registerSound("maid.mode.furnace");
    public static final SoundEvent MAID_BREWING = registerSound("maid.mode.brewing");
    public static final SoundEvent MAID_FIND_TARGET = registerSound("maid.ai.find_target");
    public static final SoundEvent MAID_HURT = registerSound("maid.ai.hurt");
    public static final SoundEvent MAID_HURT_FIRE = registerSound("maid.ai.hurt_fire");
    public static final SoundEvent MAID_PLAYER = registerSound("maid.ai.hurt_player");
    public static final SoundEvent MAID_TAMED = registerSound("maid.ai.tamed");
    public static final SoundEvent MAID_ITEM_GET = registerSound("maid.ai.item_get");
    public static final SoundEvent MAID_DEATH = registerSound("maid.ai.death");
    public static final SoundEvent GAME_WIN = registerSound("maid.ai.game_win");
    public static final SoundEvent GAME_LOST = registerSound("maid.ai.game_lost");
    public static final SoundEvent MAID_HOT = registerSound("maid.environment.hot");
    public static final SoundEvent MAID_COLD = registerSound("maid.environment.cold");
    public static final SoundEvent MAID_RAIN = registerSound("maid.environment.rain");
    public static final SoundEvent MAID_SNOW = registerSound("maid.environment.snow");
    public static final SoundEvent MAID_MORNING = registerSound("maid.environment.morning");
    public static final SoundEvent MAID_NIGHT = registerSound("maid.environment.night");
    public static final SoundEvent MAID_CREDIT = registerSound("maid.credit");
    public static final SoundEvent MAID_AI_CHAT = registerSound("maid.ai_chat");
    public static final SoundEvent CAMERA_USE = registerSound("item.camera_use");
    public static final SoundEvent ALTAR_CRAFT = registerSound("block.altar_craft");
    public static final SoundEvent GOMOKU = registerSound("block.gomoku");
    public static final SoundEvent GOMOKU_RESET = registerSound("block.gomoku_reset");
    public static final SoundEvent BOX_OPEN = registerSound("entity.box");
    public static final SoundEvent COMPASS_POINT = registerSound("item.compass");
    public static final SoundEvent FAIRY_AMBIENT = registerSound("entity.fairy.ambient");
    public static final SoundEvent FAIRY_DEATH = registerSound("entity.fairy.death");
    public static final SoundEvent FAIRY_HURT = registerSound("entity.fairy.hurt");
    public static final SoundEvent RECORDING_START = registerSound("ui.recording_start");
    public static final SoundEvent RECORDING_END = registerSound("ui.recording_end");

    private static SoundEvent registerSound(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createFixedRangeEvent(id, 16.0F));
    }
}
