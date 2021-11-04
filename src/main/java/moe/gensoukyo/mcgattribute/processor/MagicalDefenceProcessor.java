package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class MagicalDefenceProcessor implements IAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        float magic_amount = vars.getOrDefault("magical", 0.0F);
        // 处理魔抗和魔防
        float p魔法抗性 = AttributeCache.getAttributeValue(target, CustomAttributes.魔法抗性);
        magic_amount *= 1 - p魔法抗性 / 100;
        float p魔法防御 = AttributeCache.getAttributeValue(target, CustomAttributes.魔法防御);
        magic_amount -= p魔法防御;
        if (magic_amount < 0) magic_amount = 0; // 防止防御过高反而加血
        vars.put("magical", magic_amount);
        return false;
    }

}
