package moe.gensoukyo.mcgattribute;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import moe.gensoukyo.mcgattribute.config.ItemInformation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
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
import java.util.Objects;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = MCGAttribute.MOD_ID)
public class AttributeCache {

    private static final WeakHashMap<EntityLivingBase, AttributeMap> attributeMapCache = new WeakHashMap<>();

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

    public static float getAttributeValue(EntityLivingBase entity, String attribute, float defaultValue) {
        AttributeMap map = AttributeCache.getOrCreate(entity);
        IAttributeInstance instance = map.getAttributeInstanceByName(attribute);
        if (instance == null) {
            return defaultValue;
        } else {
            return (float)instance.getAttributeValue();
        }
    }

    public static float getAttributeValue(EntityLivingBase entity, IAttribute attribute) {
        AttributeMap map = AttributeCache.getOrCreate(entity);
        IAttributeInstance instance = map.getAttributeInstance(attribute);
        return (float)instance.getAttributeValue();
    }

    // itemStack.getAttributeModifiers的魔改版本
    private static Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack itemStack, EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap;
        String registryName = Objects.requireNonNull(itemStack.getItem().getRegistryName()).toString();
        if (ItemInformation.getMapOfItemInformation().containsKey(registryName)) {
            ItemInformation information = ItemInformation.getMapOfItemInformation().get(registryName);
            if (information.getSlot() == equipmentSlot.getSlotIndex()) {
                multimap = information.getAttributeModifiers();
                if (itemStack.hasTagCompound()) {
                    assert itemStack.getTagCompound() != null;
                    // 读取写在NBT里的随机属性
                    if (!itemStack.getTagCompound().hasKey("CustomAttributeModifiers", 9)) { // 9=NBTTagList
                        // 如果没有写在nbt里的随机属性，则根据AttributePlus使用的lore生成nbt，如果未读取到lore，生成空nbt
                        NBTTagList list = new NBTTagList();
                        // TODO
                        itemStack.getTagCompound().setTag("CustomAttributeModifiers", list);
                    } else {
                        NBTTagList nbttaglist = itemStack.getTagCompound().getTagList("CustomAttributeModifiers", 10); // 10=NBTTagCompound

                        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                            AttributeModifier attributemodifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbttagcompound);

                            if (attributemodifier != null && (!nbttagcompound.hasKey("Slot", 8) || nbttagcompound.getString("Slot").equals(equipmentSlot.getName())) && attributemodifier.getID().getLeastSignificantBits() != 0L && attributemodifier.getID().getMostSignificantBits() != 0L) {
                                multimap.put(nbttagcompound.getString("AttributeName"), attributemodifier);
                            }
                        }
                    }
                }
                return multimap;
            }
        }
        multimap = HashMultimap.create();
        return multimap;
    }

}
