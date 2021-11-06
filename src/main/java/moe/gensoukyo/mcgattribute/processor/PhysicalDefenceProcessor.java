package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.attribute.BuiltInAttributeNames;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class PhysicalDefenceProcessor extends AbstractProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        float p破甲几率 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.IGNORE_PHYSICAL_DEFENCE_CHANCE);
        float physics_amount = vars.getOrDefault("physical", 0.0F);
        if (random.nextInt(100) > p破甲几率) {
            // 未能破甲时，计算护甲值
            float p护甲穿透 = AttributeCache.getAttributeValue(source, BuiltInAttributeNames.PHYSICAL_DEFENCE_MINUS);
            float p物理护甲 = AttributeCache.getAttributeValue(target, BuiltInAttributeNames.PHYSICAL_DEFENCE);
            physics_amount *= 1 - Math.max(p物理护甲 - p护甲穿透, 0) / 100; // 不能穿到负数
            // 处理物理防御，PVP防御和PVE防御都共用这个属性
            float p物理防御 = AttributeCache.getAttributeValue(target, BuiltInAttributeNames.PHYSICAL_MINUS);
            physics_amount -= p物理防御;
            if (physics_amount < 0) physics_amount = 0; // 防止护甲或者防御过高反而加血
        }
        vars.put("physical", physics_amount);
        return true;
    }

}