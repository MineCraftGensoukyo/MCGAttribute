package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;
import java.util.Random;

public class LastHitProcessor implements IAttributeProcessor {

    private static final Random random = new Random();

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> inputValues) {
        float p斩杀几率 = AttributeCache.getAttributeValue(source, CustomAttributes.斩杀几率);
        float 补刀血线 = 0.1F;
        if ((target.getHealth() / target.getMaxHealth()) <= 补刀血线 && random.nextInt(100) < p斩杀几率) {
            inputValues.put("amount", target.getHealth());
            return true;
        } else {
            return false;
        }
    }
}
