package moe.gensoukyo.mcgattribute.config;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;

public class ItemInformation {

    private static final HashMap<String, ItemInformation> mapOfItemInformation = new HashMap<>();

    private final int slot;
    private final Multimap<String, AttributeModifier> attributeModifiers;

    public static HashMap<String, ItemInformation> getMapOfItemInformation() {
        return mapOfItemInformation;
    }

    public ItemInformation(String itemId, int slotIn, Multimap<String, AttributeModifier> attributeModifiersIn) {
        this.slot = slotIn;
        this.attributeModifiers = attributeModifiersIn;
        mapOfItemInformation.put(itemId, this);
    }

    public int getSlot() {
        return slot;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers() {
        return attributeModifiers;
    }
}
