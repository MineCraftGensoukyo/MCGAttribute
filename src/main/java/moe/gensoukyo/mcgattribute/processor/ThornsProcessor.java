package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.attribute.BuiltInAttributeNames;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class ThornsProcessor extends AbstractAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        // 处理反弹
        float 反弹量;
        float p反弹几率 = AttributeCache.getAttributeValue(target, BuiltInAttributeNames.THORNS_CHANCE);
        if (random.nextInt(100) < p反弹几率) {
            float p反弹倍率 = AttributeCache.getAttributeValue(target, BuiltInAttributeNames.THORNS_MULTIPLIER);
            float total = vars.getOrDefault("amount", 0.0F);
            反弹量 = total * p反弹倍率;
            source.setHealth(source.getHealth() - 反弹量); // TODO
        }
        return false;
    }
}
