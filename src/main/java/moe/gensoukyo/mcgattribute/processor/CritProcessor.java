package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.attribute.BuiltInAttributeNames;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class CritProcessor extends AbstractProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        // 处理暴击
        float p暴击几率 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.CRIT_CHANCE);
        float p暴击抵抗 = AttributeCache.getAttributeValue(target, BuiltInAttributeNames.CRIT_DEFENCE);
        if (random.nextInt(100) < p暴击几率 - p暴击抵抗) {
            float 暴击倍率 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.CRIT_MULTIPLIER);
            float total = vars.getOrDefault("final", 0.0F);
            total = total * (1 + 暴击倍率);
            vars.put("final", total);
        }
        return true;
    }
}