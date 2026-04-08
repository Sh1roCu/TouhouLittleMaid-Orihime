package com.github.tartaricacid.touhoulittlemaid.client.sound;

import com.github.tartaricacid.touhoulittlemaid.client.resource.CustomPackLoader;
import com.github.tartaricacid.touhoulittlemaid.client.resource.models.DefaultPackConstant;
import com.github.tartaricacid.touhoulittlemaid.client.sound.data.SoundCache;
import com.github.tartaricacid.touhoulittlemaid.client.sound.data.SoundData;
import com.github.tartaricacid.touhoulittlemaid.client.sound.pojo.SoundPackInfo;
import com.github.tartaricacid.touhoulittlemaid.init.InitSounds;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid.LOGGER;

public class CustomSoundLoader {
    public static final Map<String, SoundCache> CACHE = Maps.newLinkedHashMap();
    private static final Pattern FILENAME_REG = Pattern.compile("^\\d*\\.ogg$");
    private static final Marker MARKER = MarkerManager.getMarker("CustomSoundLoader");
    private static final String JSON_FILE_NAME = "maid_sound.json";

    public static void clear() {
        CACHE.clear();
    }

    public static SoundCache getSoundCache(String id) {
        return CACHE.get(id);
    }

    public static void sortSoundPack() {
        Map<String, SoundCache> sortPacks = Maps.newLinkedHashMap();

        // 先把默认模型查到，按顺序放进去
        for (String id : DefaultPackConstant.SOUND_SORT) {
            if (CACHE.containsKey(id)) {
                sortPacks.put(id, CACHE.get(id));
            }
        }
        // 剩余模型放进另一个里，进行字典排序
        CACHE.keySet().stream()
                .filter(id -> !DefaultPackConstant.SOUND_SORT.contains(id))
                .sorted(String::compareTo)
                .forEach(key -> sortPacks.put(key, CACHE.get(key)));

        // 最后顺次放入
        CACHE.clear();
        CACHE.putAll(sortPacks);
    }

    public static void loadSoundPack(Path rootPath, String id) {
        File file = rootPath.resolve("assets").resolve(id).resolve(JSON_FILE_NAME).toFile();
        if (!file.isFile()) {
            return;
        }
        LOGGER.debug(MARKER, "Loading {} sound pack: ", id);
        try (InputStream stream = Files.newInputStream(file.toPath())) {
            SoundPackInfo info = CustomPackLoader.GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), new TypeToken<SoundPackInfo>() {
            }.getType());
            info.decorate();
            // 加载图标贴图
            if (info.getIcon() != null) {
                CustomPackLoader.registerFilePackTexture(rootPath, info.getIcon());
            }
            Path soundsFolder = rootPath.resolve("assets").resolve(id).resolve("sounds").resolve("maid");
            SoundCache soundCache = new SoundCache(info, loadSoundEvent(soundsFolder));
            CACHE.put(id, soundCache);
        } catch (IOException e) {
            LOGGER.warn(MARKER, "Failed to load sound pack {}", id, e);
        } catch (JsonSyntaxException e) {
            LOGGER.warn(MARKER, "Fail to parse sound pack in id {}", id);
        }
        LOGGER.debug(MARKER, "Loaded {} sound pack.", id);
    }

    private static Map<ResourceLocation, List<SoundData>> loadSoundEvent(Path rootPath) {
        Map<ResourceLocation, List<SoundData>> buffers = Maps.newLinkedHashMap();

        buffers.put(InitSounds.MAID_IDLE.getLocation(), loadSounds(rootPath.resolve("mode"), "idle"));
        buffers.put(InitSounds.MAID_ATTACK.getLocation(), loadSounds(rootPath.resolve("mode"), "attack"));
        buffers.put(InitSounds.MAID_RANGE_ATTACK.getLocation(), loadSounds(rootPath.resolve("mode"), "range_attack"));
        buffers.put(InitSounds.MAID_DANMAKU_ATTACK.getLocation(), loadSounds(rootPath.resolve("mode"), "danmaku_attack"));
        buffers.put(InitSounds.MAID_FARM.getLocation(), loadSounds(rootPath.resolve("mode"), "farm"));
        buffers.put(InitSounds.MAID_FEED.getLocation(), loadSounds(rootPath.resolve("mode"), "feed"));
        buffers.put(InitSounds.MAID_SHEARS.getLocation(), loadSounds(rootPath.resolve("mode"), "shears"));
        buffers.put(InitSounds.MAID_MILK.getLocation(), loadSounds(rootPath.resolve("mode"), "milk"));
        buffers.put(InitSounds.MAID_TORCH.getLocation(), loadSounds(rootPath.resolve("mode"), "torch"));
        buffers.put(InitSounds.MAID_FEED_ANIMAL.getLocation(), loadSounds(rootPath.resolve("mode"), "feed_animal"));
        buffers.put(InitSounds.MAID_EXTINGUISHING.getLocation(), loadSounds(rootPath.resolve("mode"), "extinguishing"));
        buffers.put(InitSounds.MAID_REMOVE_SNOW.getLocation(), loadSounds(rootPath.resolve("mode"), "snow"));
        buffers.put(InitSounds.MAID_BREAK.getLocation(), loadSounds(rootPath.resolve("mode"), "break"));
        buffers.put(InitSounds.MAID_FURNACE.getLocation(), loadSounds(rootPath.resolve("mode"), "furnace"));
        buffers.put(InitSounds.MAID_BREWING.getLocation(), loadSounds(rootPath.resolve("mode"), "brewing"));

        buffers.put(InitSounds.MAID_FIND_TARGET.getLocation(), loadSounds(rootPath.resolve("ai"), "find_target"));
        buffers.put(InitSounds.MAID_HURT.getLocation(), loadSounds(rootPath.resolve("ai"), "hurt"));
        buffers.put(InitSounds.MAID_HURT_FIRE.getLocation(), loadSounds(rootPath.resolve("ai"), "hurt_fire"));
        buffers.put(InitSounds.MAID_PLAYER.getLocation(), loadSounds(rootPath.resolve("ai"), "hurt_player"));
        buffers.put(InitSounds.MAID_TAMED.getLocation(), loadSounds(rootPath.resolve("ai"), "tamed"));
        buffers.put(InitSounds.MAID_ITEM_GET.getLocation(), loadSounds(rootPath.resolve("ai"), "item_get"));
        buffers.put(InitSounds.MAID_DEATH.getLocation(), loadSounds(rootPath.resolve("ai"), "death"));
        buffers.put(InitSounds.GAME_WIN.getLocation(), loadSounds(rootPath.resolve("ai"), "game_win"));
        buffers.put(InitSounds.GAME_LOST.getLocation(), loadSounds(rootPath.resolve("ai"), "game_lost"));

        buffers.put(InitSounds.MAID_COLD.getLocation(), loadSounds(rootPath.resolve("environment"), "cold"));
        buffers.put(InitSounds.MAID_HOT.getLocation(), loadSounds(rootPath.resolve("environment"), "hot"));
        buffers.put(InitSounds.MAID_RAIN.getLocation(), loadSounds(rootPath.resolve("environment"), "rain"));
        buffers.put(InitSounds.MAID_SNOW.getLocation(), loadSounds(rootPath.resolve("environment"), "snow"));
        buffers.put(InitSounds.MAID_MORNING.getLocation(), loadSounds(rootPath.resolve("environment"), "morning"));
        buffers.put(InitSounds.MAID_NIGHT.getLocation(), loadSounds(rootPath.resolve("environment"), "night"));

        buffers.put(InitSounds.MAID_CREDIT.getLocation(), loadSounds(rootPath.resolve("other"), "credit"));

        reuseSounds(buffers, InitSounds.MAID_ATTACK, InitSounds.MAID_RANGE_ATTACK);
        reuseSounds(buffers, InitSounds.MAID_ATTACK, InitSounds.MAID_DANMAKU_ATTACK);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_FARM);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_FEED);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_SHEARS);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_MILK);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_TORCH);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_FEED_ANIMAL);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_EXTINGUISHING);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_REMOVE_SNOW);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_BREAK);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_FURNACE);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_BREWING);

        reuseSounds(buffers, InitSounds.MAID_HURT, InitSounds.MAID_HURT_FIRE);

        return buffers;
    }

    private static List<SoundData> loadSounds(Path rootPath, String fileName) {
        List<SoundData> sounds = Lists.newArrayList();
        File[] files = rootPath.toFile().listFiles((dir, name) -> checkFileName(fileName, name));
        if (files == null) {
            return sounds;
        }
        for (File file : files) {
            if (file.isFile()) {
                OggReader.readSoundDataFromFile(file, sounds, MARKER);
            }
        }
        return sounds;
    }

    private static void reuseSounds(Map<ResourceLocation, List<SoundData>> buffers, SoundEvent from, SoundEvent to) {
        List<SoundData> fromBuffers = buffers.get(from.getLocation());
        buffers.get(to.getLocation()).addAll(fromBuffers);
    }

    private static boolean checkFileName(String patterString, String rawString) {
        if (rawString.startsWith(patterString)) {
            String substring = rawString.substring(patterString.length());
            Matcher matcher = FILENAME_REG.matcher(substring);
            return matcher.matches();
        }
        return false;
    }

    public static void loadSoundPack(ZipFile zipFile, String id) {
        ZipEntry entry = zipFile.getEntry(String.format("assets/%s/%s", id, JSON_FILE_NAME));
        if (entry == null) {
            return;
        }
        LOGGER.debug(MARKER, "Loading {} sound pack: ", id);
        try (InputStream stream = zipFile.getInputStream(entry)) {
            SoundPackInfo info = CustomPackLoader.GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), new TypeToken<SoundPackInfo>() {
            }.getType());
            info.decorate();
            // 加载图标贴图
            if (info.getIcon() != null) {
                CustomPackLoader.registerZipPackTexture(zipFile.getName(), info.getIcon());
            }
            SoundCache soundCache = new SoundCache(info, loadSoundEvent(zipFile, id));
            CACHE.put(id, soundCache);
        } catch (IOException e) {
            LOGGER.warn(MARKER, "Failed to load sound pack {}", id, e);
        } catch (JsonSyntaxException e) {
            LOGGER.warn(MARKER, "Fail to parse sound pack in id {}", id);
        }
        LOGGER.debug(MARKER, "Loaded {} sound pack.", id);
    }

    @NotNull
    private static Map<ResourceLocation, List<SoundData>> loadSoundEvent(ZipFile zipFile, String id) {
        Map<ResourceLocation, List<SoundData>> buffers = Maps.newLinkedHashMap();
        Pattern pattern = Pattern.compile(String.format("assets/%s/sounds/maid/(.*?)/(.*?\\.ogg)", id));
        zipFile.stream().forEach(zipEntry -> {
            if (!zipEntry.isDirectory()) {
                String path = zipEntry.getName();
                Matcher matcher = pattern.matcher(path);
                if (matcher.find()) {
                    String subDir = matcher.group(1);
                    String fileName = matcher.group(2);
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_IDLE, "mode", "idle");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_ATTACK, "mode", "attack");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_RANGE_ATTACK, "mode", "range_attack");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_DANMAKU_ATTACK, "mode", "danmaku_attack");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_FARM, "mode", "farm");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_FEED, "mode", "feed");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_SHEARS, "mode", "shears");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_MILK, "mode", "milk");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_TORCH, "mode", "torch");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_FEED_ANIMAL, "mode", "feed_animal");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_EXTINGUISHING, "mode", "extinguishing");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_REMOVE_SNOW, "mode", "snow");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_BREAK, "mode", "break");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_FURNACE, "mode", "furnace");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_BREWING, "mode", "brewing");

                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_FIND_TARGET, "ai", "find_target");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_HURT, "ai", "hurt");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_HURT_FIRE, "ai", "hurt_fire");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_PLAYER, "ai", "hurt_player");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_TAMED, "ai", "tamed");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_ITEM_GET, "ai", "item_get");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_DEATH, "ai", "death");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.GAME_WIN, "ai", "game_win");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.GAME_LOST, "ai", "game_lost");

                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_COLD, "environment", "cold");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_HOT, "environment", "hot");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_RAIN, "environment", "rain");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_SNOW, "environment", "snow");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_MORNING, "environment", "morning");
                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_NIGHT, "environment", "night");

                    loadSounds(zipFile, buffers, zipEntry, subDir, fileName, InitSounds.MAID_CREDIT, "other", "credit");
                }
            }
        });

        reuseSounds(buffers, InitSounds.MAID_ATTACK, InitSounds.MAID_RANGE_ATTACK);
        reuseSounds(buffers, InitSounds.MAID_ATTACK, InitSounds.MAID_DANMAKU_ATTACK);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_FARM);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_FEED);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_SHEARS);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_MILK);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_TORCH);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_FEED_ANIMAL);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_EXTINGUISHING);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_REMOVE_SNOW);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_BREAK);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_FURNACE);
        reuseSounds(buffers, InitSounds.MAID_IDLE, InitSounds.MAID_BREWING);

        reuseSounds(buffers, InitSounds.MAID_HURT, InitSounds.MAID_HURT_FIRE);

        return buffers;
    }

    private static void loadSounds(ZipFile zipFile, Map<ResourceLocation, List<SoundData>> buffers, ZipEntry zipEntry, String subDir, String fileName, SoundEvent soundEvent, String checkSubDir, String checkFileName) {
        List<SoundData> sounds = buffers.computeIfAbsent(soundEvent.getLocation(), res -> Lists.newArrayList());
        if (checkSubDir.equals(subDir) && checkFileName(checkFileName, fileName)) {
            OggReader.readSoundDataFromZip(zipFile, zipEntry, fileName, sounds, MARKER);
        }
    }
}
