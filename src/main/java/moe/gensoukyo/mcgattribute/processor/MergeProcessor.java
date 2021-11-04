package moe.gensoukyo.mcgattribute.processor;

import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class MergeProcessor implements IAttributeProcessor {

    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> inputValues) {
        float total = 0;
        for (float value : inputValues.values()) {
            total += value;
        }
        inputValues.put("amount", total);
        return false;
    }

}