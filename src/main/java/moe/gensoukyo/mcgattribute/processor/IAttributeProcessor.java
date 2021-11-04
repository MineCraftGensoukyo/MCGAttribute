package moe.gensoukyo.mcgattribute.processor;

import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public interface IAttributeProcessor {

    /**
     * @param source 伤害来源
     * @param target 伤害目标
     * @param inputValues 输入值
     * @return 是否结束
     */
    boolean run(EntityLivingBase source, EntityLivingBase target, HashMap<String, Float> inputValues);

}