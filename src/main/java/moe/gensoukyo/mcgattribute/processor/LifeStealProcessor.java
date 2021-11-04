package moe.gensoukyo.mcgattribute.processor;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.CustomAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;
import java.util.Random;

public class LifeStealProcessor implements IAttributeProcessor {

    private static final Random random = new Random();

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        // 处理吸血
        float p吸血几率 = AttributeCache.getAttributeValue(source, CustomAttributes.吸血几率);
        float p吸血抵抗 = AttributeCache.getAttributeValue(source, CustomAttributes.吸血抵抗);
        if (random.nextInt(100) < p吸血几率 - p吸血抵抗) {
            float p吸血倍率 = AttributeCache.getAttributeValue(source, CustomAttributes.吸血倍率);
            float total = vars.getOrDefault("final", 0.0F);
            float 吸血量 = total * p吸血倍率;
            source.heal(吸血量);
        }
        return false;
    }

}