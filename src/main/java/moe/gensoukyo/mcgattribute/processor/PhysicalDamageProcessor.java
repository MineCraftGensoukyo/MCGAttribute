package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class PhysicalDamageProcessor implements IAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> inputValues) {
        // 物理伤害
        float physics_amount = 0.0F;
        // 物理、PVP、PVE伤害啥的都是它的modifier
        float p物理伤害 = AttributeCache.getAttributeValue(source, CustomAttributes.物理伤害);
        // 伤害加成
        float p物理增幅 = AttributeCache.getAttributeValue(source, CustomAttributes.物理增幅);
        inputValues.put("physical", (physics_amount + p物理伤害) * (1 + p物理增幅));
        return false;
    }

}
