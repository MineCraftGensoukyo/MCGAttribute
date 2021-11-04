package moe.gensoukyo.mcgattribute;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import java.util.ArrayList;

public class CustomAttributes {

    public static final IAttribute 物理伤害 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 魔法伤害 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 物理增幅 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 魔法增幅 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 真实伤害 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 暴击几率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 暴击倍率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 吸血几率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 吸血倍率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 命中几率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 斩杀几率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 破甲几率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 护甲穿透 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 物理护甲 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 魔法抗性 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 物理防御 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 魔法防御 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 暴击抵抗 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 吸血抵抗 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 闪避几率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 反弹几率 = registerAttribute("mcgproject.attribute.damage_physics");
    public static final IAttribute 反弹倍率 = registerAttribute("mcgproject.attribute.damage_physics");

    public static ArrayList<IAttribute> attributeList = new ArrayList<>();

    private static IAttribute registerAttribute(String nameIn) {
        IAttribute attribute = new RangedAttribute(
                null, nameIn, 0.0, Short.MIN_VALUE, Short.MAX_VALUE);
        attributeList.add(attribute);
        return attribute;
    }

}
