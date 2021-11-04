package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;
import java.util.Random;

public class CritProcessor implements IAttributeProcessor {

    public static Random random = new Random();

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        // 处理暴击
        float p暴击几率 = AttributeCache.getAttributeValue(source, CustomAttributes.暴击几率);
        float p暴击抵抗 = AttributeCache.getAttributeValue(target, CustomAttributes.暴击抵抗);
        if (random.nextInt(100) < p暴击几率 - p暴击抵抗) {
            float 暴击倍率 = AttributeCache.getAttributeValue(source, CustomAttributes.暴击倍率);
            float total = vars.getOrDefault("final", 0.0F);
            total = total * (1 + 暴击倍率);
            vars.put("final", total);
        }
        return false;
    }
}