package moe.gensoukyo.mcgattribute.processor;

import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public class NothingProcessor extends AbstractProcessor {
    @Override
    public boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars) {
        return true;
    }
}
