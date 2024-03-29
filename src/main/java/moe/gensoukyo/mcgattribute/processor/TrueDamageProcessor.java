package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.attribute.BuiltInAttributeNames;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class TrueDamageProcessor extends AbstractProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        vars.put("true", vars.getOrDefault("true", 0.0F) + AttributeCache.getAttributeValue(source, BuiltInAttributeNames.TRUE_DAMAGE));
        return true;
    }

}
