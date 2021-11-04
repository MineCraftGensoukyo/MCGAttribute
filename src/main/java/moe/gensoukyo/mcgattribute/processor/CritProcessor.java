package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;
import java.util.Random;

public class CritProcessor implements IAttributeProcessor {

    public static Random random = new Random();

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> inputValues) {
        // 处理暴击
        float p暴击几率 = AttributeCache.getAttributeValue(source, CustomAttributes.暴击几率);
        float p暴击抵抗 = AttributeCache.getAttributeValue(target, CustomAttributes.暴击抵抗);
        if (random.nextInt(100) < p暴击几率 - p暴击抵抗) {
            float 暴击倍率 = AttributeCache.getAttributeValue(source, CustomAttributes.暴击倍率);
            float total = inputValues.getOrDefault("amount", 0.0F);
            total = total * (1 + 暴击倍率);
            inputValues.put("amount", total);
        }
        return false;
    }
}