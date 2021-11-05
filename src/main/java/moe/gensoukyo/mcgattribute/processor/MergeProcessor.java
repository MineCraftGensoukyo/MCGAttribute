package moe.gensoukyo.mcgattribute.processor;

import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class MergeProcessor extends AbstractAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        float total = 0;
        for (float value : vars.values()) {
            total += value;
        }
        vars.put("final", total);
        return false;
    }

}