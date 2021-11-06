package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.attribute.BuiltInAttributeNames;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class DodgeProcessor extends AbstractProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        float p闪避几率 = AttributeCache.getAttributeValue(target, BuiltInAttributeNames.DODGE_CHANCE);
        float p命中几率 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.HIT_CHANCE);
        if (random.nextInt(100) < p闪避几率 - p命中几率) {
            vars.put("final", 0.0F);
            return false;
        } else {
            return true;
        }
    }

}
