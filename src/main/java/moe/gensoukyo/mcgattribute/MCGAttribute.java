package moe.gensoukyo.mcgattribute;

import moe.gensoukyo.mcgattribute.chain.ProcessorChains;
import moe.gensoukyo.mcgattribute.processor.IAttributeProcessor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

@Mod(
        modid = MCGAttribute.MOD_ID,
        name = MCGAttribute.MOD_NAME,
        version = MCGAttribute.VERSION
)
@Mod.EventBusSubscriber
public class MCGAttribute {

    public static final String MOD_ID = "mcgattribute";
    public static final String MOD_NAME = "MCGAttribute";
    public static final String VERSION = "1.0-SNAPSHOT";

    @Mod.Instance(MOD_ID)
    public static MCGAttribute INSTANCE;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase source = (EntityLivingBase) event.getSource().getTrueSource();
            EntityLivingBase target = event.getEntityLiving();
            HashMap<String, Float> templateValues = new HashMap<>();
            templateValues.put("amount", event.getAmount());
            for (IAttributeProcessor iAttributeProcessor : ProcessorChains.DAMAGE) {
                if (iAttributeProcessor.run(source, target, templateValues)){
                    return;
                }
            }
            event.setAmount(templateValues.getOrDefault("amount", event.getAmount()));
        }
    }

}
