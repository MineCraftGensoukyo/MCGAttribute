package moe.gensoukyo.mcgattribute;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import moe.gensoukyo.mcgattribute.attribute.AttributeMap;
import moe.gensoukyo.mcgattribute.config.ItemInfo;
import moe.gensoukyo.mcgattribute.config.ModConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = MCGAttribute.MOD_ID)
public class AttributeCache {

    // 缓存了所有实体的属性
    private static final WeakHashMap<EntityLivingBase, AttributeMap> attributeMapCache = new WeakHashMap<>();

    // 当实体的装备变动时，移除移除的物品的属性，增加增加的物品的属性
    // 理论上装备（自定义）耐久度的变化也会导致此事件，所以基本上就是频繁触发
    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        AttributeMap map = getOrCreate(event.getEntityLiving());

        // 排除非CustomAttribute引起的变化
        boolean attributeChanged = false;
        ItemStack stackFrom = event.getFrom();
        ItemStack stackTo = event.getTo();
        boolean flag1 = stackFrom.hasTagCompound();
        boolean flag2 = stackTo.hasTagCompound();
        if (flag1^flag2) {
            attributeChanged = true; // 一个有一个没有就默认其中一个有CustomAttribute吧
        } else {
            if (flag1) {
                NBTTagCompound compoundFrom = stackFrom.getTagCompound();
                NBTTagCompound compoundTo = stackFrom.getTagCompound();
                assert compoundFrom != null;
                if (compoundFrom.hasKey("CustomAttribute", 10)) {
                    if (compoundTo.hasKey("CustomAttribute", 10)) {
                        NBTTagCompound attrFrom = stackFrom.getTagCompound();
                        NBTTagCompound attrTo = stackFrom.getTagCompound();
                        if (attrFrom.hasKey("Information", 10)) {
                            if (attrTo.hasKey("Information", 10)) {
                                attributeChanged = !attrFrom.getCompoundTag("Information").equals(attrTo.getCompoundTag("Information"));
                            } else {
                                attributeChanged = true;
                            }
                        }
                    } else {
                        attributeChanged = true;
                    }
                }
            }
        }
        if (attributeChanged) {
            map.applySlotAttributes(event.getSlot(), getSlotAttributes(event.getTo(), event.getSlot()));
        }
    }

    // 从实体得到属性，如果缓存里没有就新建一个
    @Nonnull
    public static AttributeMap getOrCreate(EntityLivingBase entity) {
        AttributeMap map = attributeMapCache.get(entity);
        if (map == null) {
            map = new AttributeMap();
            attributeMapCache.put(entity, map);
        }
        return map;
    }

    // 从属性类型获取具体的属性值
    public static float getAttributeValue(EntityLivingBase entity, String attribute, float defaultValue) {
        AttributeMap map = AttributeCache.getOrCreate(entity);
        return map.getAttributeValue(attribute, defaultValue);
    }

    // 从属性类型获取具体的属性值，没写默认值就默认为0
    public static float getAttributeValue(EntityLivingBase entity, String attribute) {
        AttributeMap map = AttributeCache.getOrCreate(entity);
        return map.getAttributeValue(attribute);
    }

    @Nonnull
    private static HashMap<String, Float> getSlotAttributes(ItemStack stack, EntityEquipmentSlot equipmentSlot) {
        HashMap<String, Float> hashMap = new HashMap<>();
        if (stack.hasTagCompound()) {
            assert stack.getTagCompound() != null;
            if (stack.getTagCompound().hasKey("CustomAttribute", 10)) { // 10=NBTTagCompound
                NBTTagCompound topCompound = stack.getTagCompound().getCompoundTag("CustomAttribute");

                if (topCompound.hasKey("Information", 10)) {
                    NBTTagCompound informationCompound = stack.getTagCompound().getCompoundTag("Information");

                    if (informationCompound.hasKey("Type", 8)) { // 8=NBTTagString
                        String name = informationCompound.getString("Type");
                        ItemInfo information = ModConfig.getItemInfo(name); // 从配置中读取物品信息
                        if (information != null) {
                            if (information.getSlot() == equipmentSlot.getSlotIndex()) {
                                Multimap<String, Float> multimap = HashMultimap.create();
                                for (Map.Entry<String, Float> entry : information.getAttributeModifiers().entrySet()) {
                                    multimap.put(entry.getKey(), entry.getValue());
                                }

                                if (informationCompound.hasKey("Unique", 10)) {
                                    NBTTagCompound uniqueCompound = informationCompound.getCompoundTag("Unique");
                                    for (String s : uniqueCompound.getKeySet()) {
                                        if (uniqueCompound.hasKey(s, 5)) {
                                            multimap.put(s, uniqueCompound.getFloat(s));
                                        }
                                    }
                                }

                                if (informationCompound.hasKey("Gems", 9)) { // 9=NBTTagList
                                    NBTTagList gemsList = informationCompound.getTagList("Gems", 8);
                                    for (int i = 0; i < gemsList.tagCount(); i++) {
                                        String gemName = gemsList.getStringTagAt(i);
                                        ItemInfo gemInformation = ModConfig.getItemInfo(gemName); // 从配置中读取物品信息
                                        if (gemInformation != null) {
                                            for (Map.Entry<String, Float> entry : gemInformation.getAttributeModifiers().entrySet()) {
                                                multimap.put(entry.getKey(), entry.getValue());
                                            }
                                        }
                                    }
                                }

                                for (Map.Entry<String, Float> entry : multimap.entries()) {
                                    String attributeName = entry.getKey();
                                    hashMap.put(attributeName, hashMap.getOrDefault(attributeName, 0.0F) + entry.getValue());
                                }
                            }
                        }
                        // 给客户端读取数据用的
                        NBTTagCompound list = new NBTTagCompound();
                        for (Map.Entry<String, Float> entry : hashMap.entrySet()) {
                            list.setFloat(entry.getKey(), entry.getValue());
                        }
                        topCompound.setTag("Cached", list);
                    }
                }

            }
        }
        return hashMap;
    }

}
