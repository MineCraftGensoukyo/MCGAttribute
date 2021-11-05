package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.attribute.BuiltInAttributeNames;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class LastHitProcessor extends AbstractAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        float p斩杀几率 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.LAST_HIT_CHANCE);
        float 补刀血线 = 0.1F;
        if ((target.getHealth() / target.getMaxHealth()) <= 补刀血线 && random.nextInt(100) < p斩杀几率) {
            vars.put("final", target.getHealth());
            return true;
        } else {
            return false;
        }
    }
}
