package cn.sh1rocu.touhoulittlemaid.util.neoforge;

import com.mojang.serialization.MapCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;

import java.util.Set;

public class ValueInputUtil {
    public static Set<String> keySet(ValueInput input) {
        //noinspection deprecation
        return input.read(MapCodec.assumeMapUnsafe(CompoundTag.CODEC)).orElseThrow().keySet();
    }

    public static void readChild(ValueInput input, String key, ValueIOSerializable object) {
        input.child(key).ifPresent(object::deserialize);
    }

    public static ValueInput rawChildOrEmpty(ValueInput input, String key) {
        return input.childOrEmpty(key);
    }
}
