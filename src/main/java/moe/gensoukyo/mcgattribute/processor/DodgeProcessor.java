package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;
import java.util.Random;

public class DodgeProcessor implements IAttributeProcessor {

    public static Random random = new Random();

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> inputValues) {
        float p闪避几率 = AttributeCache.getAttributeValue(target, CustomAttributes.闪避几率);
        float p命中几率 = AttributeCache.getAttributeValue(source, CustomAttributes.命中几率);
        if (random.nextInt(100) < p闪避几率 - p命中几率) {
            inputValues.put("amount", 0.0F);
            return true;
        } else {
            return false;
        }
    }

}
