package moe.gensoukyo.mcgattribute.config;

import com.google.common.collect.Multimap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class ItemInformation {

    private static final HashMap<String, ItemInformation> mapOfItemInformation = new HashMap<>();

    private final int slot;
    private final Multimap<String, Float> attributeModifiers;

    @Nullable
    public static ItemInformation get(String name) {
        return mapOfItemInformation.get(name);
    }

    public ItemInformation(String itemId, int slotIn, @Nonnull Multimap<String, Float> attributeModifiersIn) {
        this.slot = slotIn;
        this.attributeModifiers = attributeModifiersIn;
        mapOfItemInformation.put(itemId, this);
    }

    // 给宝石使用的，无需输入槽位，也不会检查槽位
    public ItemInformation(String itemId, @Nonnull Multimap<String, Float> attributeModifiersIn) {
        this.slot = -1;
        this.attributeModifiers = attributeModifiersIn;
        mapOfItemInformation.put(itemId, this);
    }

    public int getSlot() {
        return slot;
    }

    public Multimap<String, Float> getAttributeModifiers() {
        return attributeModifiers;
    }
}
