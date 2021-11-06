package moe.gensoukyo.mcgattribute;

import moe.gensoukyo.mcgattribute.chain.ProcessorChains;
import moe.gensoukyo.mcgattribute.config.ModConfig;
import moe.gensoukyo.mcgattribute.processor.AbstractAttributeProcessor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    static {
        ModConfig.reloadAll();
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase source = (EntityLivingBase) event.getSource().getTrueSource();
            EntityLivingBase target = event.getEntityLiving();
            HashMap<String, Float> vars = new HashMap<>();
            vars.put("input", event.getAmount());
            vars.put("final", 0.0F);
            for (AbstractAttributeProcessor iAttributeProcessor : ProcessorChains.DAMAGE) {
                if (iAttributeProcessor.run(source, target, vars)){
                    return;
                }
            }
            event.setAmount(vars.getOrDefault("final", event.getAmount()));
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {

    }
}
