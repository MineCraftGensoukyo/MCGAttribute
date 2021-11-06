package moe.gensoukyo.mcgattribute.config;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class ItemInfo {

    @SerializedName("slot")
    private final int slot;

    @SerializedName("attributes")
    private final HashMap<String, Float> attributeModifiers;

    public ItemInfo(String itemId, int slotIn, @Nonnull HashMap<String, Float> attributeModifiersIn) {
        this.slot = slotIn;
        this.attributeModifiers = attributeModifiersIn;
        ModConfig.putItemInfo(itemId, this);
    }

    // 给宝石使用的，无需输入槽位，也不会检查槽位
    public ItemInfo(String itemId, @Nonnull HashMap<String, Float> attributeModifiersIn) {
        this.slot = -1;
        this.attributeModifiers = attributeModifiersIn;
        ModConfig.putItemInfo(itemId, this);
    }

    public int getSlot() {
        return slot;
    }

    public HashMap<String, Float> getAttributeModifiers() {
        return attributeModifiers;
    }

}
