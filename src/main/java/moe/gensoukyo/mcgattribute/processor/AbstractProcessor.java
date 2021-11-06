package moe.gensoukyo.mcgattribute.processor;

import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;
import java.util.Random;

public abstract class AbstractProcessor {

    public static Random random = new Random();

    /**
     * @param source 伤害来源
     * @param target 伤害目标
     * @param vars   输入值
     * @return 是否继续
     */
    public abstract boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> vars);

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

}