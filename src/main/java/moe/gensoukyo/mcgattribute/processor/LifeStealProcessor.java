package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.attribute.BuiltInAttributeNames;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class LifeStealProcessor extends AbstractProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        // 处理吸血
        float p吸血几率 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.LIFE_STEAL_CHANCE);
        float p吸血抵抗 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.LIFE_STEAL_DEFENCE);
        if (random.nextInt(100) < p吸血几率 - p吸血抵抗) {
            float p吸血倍率 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.LIFE_STEAL_MULTIPLIER);
            float total = vars.getOrDefault("final", 0.0F);
            float 吸血量 = total * p吸血倍率;
            source.heal(吸血量);
        }
        return true;
    }

}