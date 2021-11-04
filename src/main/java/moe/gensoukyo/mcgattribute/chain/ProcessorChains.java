package moe.gensoukyo.mcgattribute.chain;

import moe.gensoukyo.mcgattribute.processor.*;

import java.util.ArrayList;

public class ProcessorChains {

    public static ArrayList<IAttributeProcessor> DAMAGE = new ArrayList<>();

    static {
        DAMAGE.add(new DodgeProcessor()); // 闪避
        DAMAGE.add(new LastHitProcessor()); // 即死

        DAMAGE.add(new PhysicalDamageProcessor()); // 物理伤害
        DAMAGE.add(new PhysicalDefenceProcessor()); // 物理防御
        DAMAGE.add(new MagicalDamageProcessor()); // 魔法伤害
        DAMAGE.add(new MagicalDefenceProcessor()); // 魔法防御
        DAMAGE.add(new TrueDamageProcessor());  // 真实伤害
        DAMAGE.add(new MergeProcessor()); // 合并伤害

        DAMAGE.add(new CritProcessor()); // 暴击

        DAMAGE.add(new LifeStealProcessor()); // 吸血
        DAMAGE.add(new ThornsProcessor()); // 反伤
    }
}
