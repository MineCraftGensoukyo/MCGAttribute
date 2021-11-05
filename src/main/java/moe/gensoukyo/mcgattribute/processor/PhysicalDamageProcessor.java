package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.attribute.BuiltInAttributeNames;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class PhysicalDamageProcessor extends AbstractAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        // 物理伤害
        float physics_amount = 0.0F;
        // 物理、PVP、PVE伤害啥的都是它的modifier
        float p物理伤害 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.PHYSICAL_DAMAGE);
        // 伤害加成
        float p物理增幅 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.PHYSICAL_MULTIPLIER);
        vars.put("physical", (physics_amount + p物理伤害) * (1 + p物理增幅));
        return false;
    }

}