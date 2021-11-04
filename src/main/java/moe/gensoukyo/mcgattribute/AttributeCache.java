package moe.gensoukyo.mcgattribute;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import moe.gensoukyo.mcgattribute.config.ItemInformation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = MCGAttribute.MOD_ID)
public class AttributeCache {

    // 缓存了所有实体的属性
    private static final WeakHashMap<EntityLivingBase, AttributeMap> attributeMapCache = new WeakHashMap<>();

    // 当实体的装备变动时，移除移除的物品的属性，增加增加的物品的属性
    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        AttributeMap map = getOrCreate(event.getEntityLiving());
        if (!event.getFrom().isEmpty()) {
            map.removeAttributeModifiers(getAttributeModifiers(event.getFrom(), event.getSlot()));
        }
        if (!event.getTo().isEmpty()) {
            map.applyAttributeModifiers(getAttributeModifiers(event.getTo(), event.getSlot()));
        }
    }

    // 从实体得到属性，如果缓存里没有就新建一个
    @Nonnull
    public static AttributeMap getOrCreate(EntityLivingBase entity) {
        AttributeMap map = attributeMapCache.get(entity);
        if (map == null) {
            map = new AttributeMap();
            for (IAttribute attribute : CustomAttributes.attributeList) {
                map.registerAttribute(attribute);
            }
            attributeMapCache.put(entity, map);
        }
        return map;
    }

    // 通过字符串获取具体的属性值，需要一个默认值
    public static float getAttributeValue(EntityLivingBase entity, String attribute, float defaultValue) {
        AttributeMap map = AttributeCache.getOrCreate(entity);
        IAttributeInstance instance = map.getAttributeInstanceByName(attribute);
        if (instance == null) {
            return defaultValue;
        } else {
            return (float)instance.getAttributeValue();
        }
    }

    // 从属性类型获取具体的属性值，因为属性本身定义了默认值，所以不需要传入默认值
    public static float getAttributeValue(EntityLivingBase entity, IAttribute attribute) {
        AttributeMap map = AttributeCache.getOrCreate(entity);
        IAttributeInstance instance = map.getAttributeInstance(attribute);
        return (float)instance.getAttributeValue();
    }

    // 获取ItemStack的属性修饰符
    private static Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack itemStack, EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (itemStack.hasTagCompound()) {
            // 实在是拿不准这里会不会崩还是 try catch 一下吧
            try {
                assert itemStack.getTagCompound() != null;
                if (!itemStack.getTagCompound().hasKey("CustomAttribute", 10)) { // 10=NBTTagCompound
                    // TODO 转换lore
                } else {
                    NBTTagCompound customAttributeCompound = itemStack.getTagCompound().getCompoundTag("CustomAttribute");
                    if (customAttributeCompound.hasKey("Type", 8)) { // 8=NBTTagString
                        String name = customAttributeCompound.getString("Type");
                        ItemInformation information = ItemInformation.get(name); // 从配置中读取物品信息
                        if (information != null) {
                            if (information.getSlot() == equipmentSlot.getSlotIndex()) {

                                multimap.putAll(information.getAttributeModifiers());

                                if (customAttributeCompound.hasKey("Unique", 9)) { // 9=NBTTagList
                                    NBTTagList modifiers = customAttributeCompound.getTagList("Unique", 10); // 10=NBTTagCompound
                                    for (int i = 0; i < modifiers.tagCount(); ++i) {
                                        NBTTagCompound modifier = modifiers.getCompoundTagAt(i);
                                        AttributeModifier attributemodifier = readAttributeModifierFromNBT(modifier);

                                        if (attributemodifier != null)
                                        {
                                            multimap.put(modifier.getString("AttributeName"), attributemodifier);
                                        }
                                    }
                                }

                                if (customAttributeCompound.hasKey("Gems", 9)) {
                                    NBTTagList gemsList = customAttributeCompound.getTagList("Gems", 8);
                                    for (int i = 0; i < gemsList.tagCount(); ++i) {
                                        String gemName = gemsList.getStringTagAt(i);
                                        ItemInformation gemInformation = ItemInformation.get(gemName); // 从配置中读取物品信息
                                        if (gemInformation != null) {
                                            multimap.putAll(gemInformation.getAttributeModifiers());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                MCGAttribute.LOGGER.warn("Unable to load itemstack attributes: {}", e.getMessage());
            }
        }
        return multimap;
    }

    @Nullable
    public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound compound)
    {
        //UUID uuid = compound.getUniqueId("UUID"); 不需要UUID
        try
        {
            return new AttributeModifier(compound.getString("Name"), compound.getDouble("Amount"), compound.getInteger("Operation"));
        }
        catch (Exception exception)
        {
            MCGAttribute.LOGGER.warn("Unable to create attribute: {}", exception.getMessage());
            return null;
        }
    }

}
