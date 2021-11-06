package moe.gensoukyo.mcgattribute.attribute;

import moe.gensoukyo.mcgattribute.processor.AbstractProcessor;
import moe.gensoukyo.mcgattribute.processor.NothingProcessor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AttributeInfo {

    private static final HashMap<String, AttributeInfo> map = new HashMap<>();

    private String name;
    private EnumAttributeType type;
    private ArrayList<AbstractProcessor> processors;

    public AttributeInfo(String nameIn, EnumAttributeType typeIn, AbstractProcessor... processorsIn) {
        this.name = nameIn;
        this.type = typeIn;
        this.processors = new ArrayList<>();
        processors.addAll(Arrays.asList(processorsIn));
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

    public ArrayList<AbstractProcessor> getProcessors() {
        return processors;
    }

    public String getName() {
        return name;
    }
}
