package moe.gensoukyo.mcgattribute.config;

import com.google.gson.Gson;
import moe.gensoukyo.mcgattribute.MCGAttribute;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ModConfig {

    private static HashMap<String, ItemInfo> mapOfItemInfo = new HashMap<>();

    @Nullable
    public static ItemInfo getItemInfo(String name) {
        return mapOfItemInfo.get(name);
    }

    @Nullable
    public static ItemInfo putItemInfo(String name, ItemInfo info) {
        return mapOfItemInfo.put(name, info);
    }

    public static void reloadAll() {
        reloadItemInfo();
    }

    public static void reloadItemInfo() {
        Gson gson = new Gson();
        HashMap<String, Float> hashMap = new HashMap<>();
        hashMap.put("属性a", 996F);
        hashMap.put("属性b", 993F);
        hashMap.put("属性c", 991F);
        new ItemInfo("阿巴阿巴阿巴", hashMap);
        HashMap<String, Float> hashMap1hMap = new HashMap<>();
        hashMap1hMap.put("awce", 99F);
        hashMap1hMap.put("ac", 9F);
        hashMap1hMap.put("aasdf", 999F);
        new ItemInfo("阿巴啊巴", hashMap1hMap);
        MCGAttribute.LOGGER.info("守矢神社" + gson.toJson(mapOfItemInfo));
        mapOfItemInfo = gson.fromJson("{\"阿巴啊巴\":{\"slot\":-1,\"attributeModifiers\":{\"aasdf\":999.0,\"ac\":9.0,\"awce\":99.0}},\"阿巴阿巴阿巴\":{\"slot\":-1,\"attributeModifiers\":{\"属性c\":991.0,\"属性a\":996.0,\"属性b\":993.0}}}",
                HashMap.class);
    }


}
