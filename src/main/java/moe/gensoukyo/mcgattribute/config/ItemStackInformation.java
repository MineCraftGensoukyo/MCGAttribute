package moe.gensoukyo.mcgattribute.config;

import com.google.common.collect.Multiset;

import java.util.ArrayList;

public class ItemStackInformation {

    private final TemplateInformation template;
    private final ArrayList<TemplateInformation> gems;

    public ItemStackInformation(TemplateInformation templateIn, Multiset s, ArrayList<TemplateInformation> gemsIn) {
        this.gems = gemsIn;
        this.template = templateIn;
    }

}
