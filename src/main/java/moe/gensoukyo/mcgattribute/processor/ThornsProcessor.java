package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;
import java.util.Random;

public class ThornsProcessor implements IAttributeProcessor {

    public static final Random random = new Random();

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        // 处理反弹
        float 反弹量 = 0;
        float p反弹几率 = AttributeCache.getAttributeValue(target, CustomAttributes.反弹几率);
        if (random.nextInt(100) < p反弹几率) {
            float p反弹倍率 = AttributeCache.getAttributeValue(target, CustomAttributes.反弹倍率);
            float total = vars.getOrDefault("amount", 0.0F);
            反弹量 = total * p反弹倍率;
            source.setHealth(source.getHealth() - 反弹量); // TODO
        }
        return false;
    }
}
