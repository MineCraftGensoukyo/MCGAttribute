package moe.gensoukyo.mcgattribute.attribute;

import moe.gensoukyo.mcgattribute.processor.AbstractProcessor;
import net.minecraft.inventory.EntityEquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class AttributeMap {

    private final HashMap<EntityEquipmentSlot, HashMap<String, Float>> slotAttributeMaps;
    private final HashMap<String, Float> totalAttributeMap;
    private final HashMap<EnumAttributeType, ArrayList<AbstractProcessor>> processors;

    private boolean shouldRecalculate;

    public AttributeMap() {
        slotAttributeMaps = new HashMap<>();
        totalAttributeMap = new HashMap<>();
        processors = new HashMap<>();
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            slotAttributeMaps.put(slot, new HashMap<>());
        }
        for (EnumAttributeType value : EnumAttributeType.values()) {
            processors.put(value, new ArrayList<>());
        }
    }

    public float getAttributeValue(String attributeName) {
        if (shouldRecalculate) {
            recalculateAttributes();
            shouldRecalculate = false;
        }
        return totalAttributeMap.getOrDefault(attributeName, 0.0F);
    }

    public float getAttributeValue(String attributeName, float defaultValue) {
        if (shouldRecalculate) {
            recalculateAttributes();
            shouldRecalculate = false;
        }
        return totalAttributeMap.getOrDefault(attributeName, defaultValue);
    }

    public void applySlotAttributes(EntityEquipmentSlot slot, HashMap<String, Float> modifiers) {
        slotAttributeMaps.put(slot, modifiers);
        shouldRecalculate = true;
    }

    private void recalculateAttributes() {
        totalAttributeMap.clear();
        for (HashMap<String, Float> map : slotAttributeMaps.values()) {
            for (Map.Entry<String, Float> entry : map.entrySet()) {
                String attributeName = entry.getKey();
                totalAttributeMap.put(attributeName, totalAttributeMap.getOrDefault(attributeName, 0.0F) + entry.getValue());
            }
        }
        for (String s : totalAttributeMap.keySet()) {
            AttributeInfo info = AttributeInfo.get(s);
            ArrayList<AbstractProcessor> originalProcessors = processors.get(info.getType());
            if (!originalProcessors.contains(info.getProcessor())) {
                originalProcessors.add(info.getProcessor());
            }

        }
    }

    public ArrayList<AbstractProcessor> getProcessors(EnumAttributeType type) {
        return processors.get(type);
    }
}
