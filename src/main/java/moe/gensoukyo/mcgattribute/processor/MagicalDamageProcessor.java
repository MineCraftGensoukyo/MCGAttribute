package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class MagicalDamageProcessor implements IAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        // 魔法伤害
        float magic_amount = 0.0F;
        float p魔法伤害 = AttributeCache.getAttributeValue(source, CustomAttributes.魔法伤害);
        // 伤害加成
        float p魔法增幅 = AttributeCache.getAttributeValue(source, CustomAttributes.魔法增幅);
        vars.put("magical", (magic_amount + p魔法伤害) * (1 + p魔法增幅));
        return false;
    }

}
