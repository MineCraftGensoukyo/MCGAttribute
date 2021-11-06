package moe.gensoukyo.mcgattribute.attribute;

import moe.gensoukyo.mcgattribute.processor.*;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class AttributeInfo {

    private static final HashMap<String, AttributeInfo> map = new HashMap<>();

    static {
        new AttributeInfo(BuiltInAttributeNames.PHYSICAL_DAMAGE, EnumAttributeType.ATTACK, new PhysicalDamageProcessor());
        new AttributeInfo(BuiltInAttributeNames.MAGICAL_DAMAGE, EnumAttributeType.ATTACK, new MagicalDamageProcessor());
        //new AttributeInfo(BuiltInAttributeNames.PHYSICAL_MULTIPLIER, EnumAttributeType.ATTACK, new PhysicalDamageProcessor());
        //new AttributeInfo(BuiltInAttributeNames.MAGICAL_MULTIPLIER, EnumAttributeType.ATTACK, new MagicalDamageProcessor());
        new AttributeInfo(BuiltInAttributeNames.TRUE_DAMAGE, EnumAttributeType.ATTACK, new TrueDamageProcessor());
        new AttributeInfo(BuiltInAttributeNames.CRIT_CHANCE, EnumAttributeType.ATTACK, new CritProcessor());
        //new AttributeInfo(BuiltInAttributeNames.CRIT_MULTIPLIER, EnumAttributeType.ATTACK, new CritProcessor());
        new AttributeInfo(BuiltInAttributeNames.LIFE_STEAL_CHANCE, EnumAttributeType.ATTACK, new LifeStealProcessor());
        //new AttributeInfo(BuiltInAttributeNames.LIFE_STEAL_MULTIPLIER, EnumAttributeType.ATTACK, new LifeStealProcessor());
        //new AttributeInfo(BuiltInAttributeNames.HIT_CHANCE, EnumAttributeType.ATTACK, new DodgeProcessor());
        new AttributeInfo(BuiltInAttributeNames.LAST_HIT_CHANCE, EnumAttributeType.ATTACK, new LastHitProcessor());
        new AttributeInfo(BuiltInAttributeNames.IGNORE_PHYSICAL_DEFENCE_CHANCE, EnumAttributeType.ATTACK, new PhysicalDefenceProcessor());
        //new AttributeInfo(BuiltInAttributeNames.PHYSICAL_DEFENCE_MINUS, EnumAttributeType.ATTACK, new PhysicalDefenceProcessor());

        new AttributeInfo(BuiltInAttributeNames.PHYSICAL_DEFENCE, EnumAttributeType.DEFENCE, new PhysicalDefenceProcessor());
        new AttributeInfo(BuiltInAttributeNames.MAGICAL_DEFENCE, EnumAttributeType.DEFENCE, new MagicalDefenceProcessor());
        new AttributeInfo(BuiltInAttributeNames.PHYSICAL_MINUS, EnumAttributeType.DEFENCE, new PhysicalDefenceProcessor());
        new AttributeInfo(BuiltInAttributeNames.MAGICAL_MINUS, EnumAttributeType.DEFENCE, new MagicalDefenceProcessor());
        //new AttributeInfo(BuiltInAttributeNames.CRIT_DEFENCE, EnumAttributeType.DEFENCE, new CritProcessor());
        //new AttributeInfo(BuiltInAttributeNames.LIFE_STEAL_DEFENCE, EnumAttributeType.DEFENCE, new LifeStealProcessor());
        new AttributeInfo(BuiltInAttributeNames.DODGE_CHANCE, EnumAttributeType.DEFENCE, new DodgeProcessor());
        new AttributeInfo(BuiltInAttributeNames.THORNS_CHANCE, EnumAttributeType.DEFENCE, new ThornsProcessor());
        //new AttributeInfo(BuiltInAttributeNames.THORNS_MULTIPLIER, EnumAttributeType.DEFENCE, new ThornsProcessor());
    }

    private String name;
    private EnumAttributeType type;
    private AbstractProcessor processors;

    public AttributeInfo(String nameIn, EnumAttributeType typeIn, AbstractProcessor processorIn) {
        this.name = nameIn;
        this.type = typeIn;
        this.processors = processorIn;
        map.put(this.name, this);
    }

    @Nonnull
    public static AttributeInfo get(String name) {
        if (!map.containsKey(name)) {
            map.put(name, new AttributeInfo(name, EnumAttributeType.NOTHING, new NothingProcessor()));
        }
        return map.get(name);
    }

    public EnumAttributeType getType() {
        return type;
    }

    public AbstractProcessor getProcessor() {
        return processors;
    }

    public String getName() {
        return name;
    }
}
