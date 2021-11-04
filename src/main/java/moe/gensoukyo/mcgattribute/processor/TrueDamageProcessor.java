package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class TrueDamageProcessor implements IAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> inputValues) {
        inputValues.put("true", AttributeCache.getAttributeValue(source, CustomAttributes.真实伤害));
        return false;
    }

}
