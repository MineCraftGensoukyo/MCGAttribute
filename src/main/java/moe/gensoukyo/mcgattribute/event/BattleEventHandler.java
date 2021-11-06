package moe.gensoukyo.mcgattribute.event;

import moe.gensoukyo.mcgattribute.AttributeCache;
import moe.gensoukyo.mcgattribute.MCGAttribute;
import moe.gensoukyo.mcgattribute.attribute.EnumAttributeType;
import moe.gensoukyo.mcgattribute.chain.ProcessorChains;
import moe.gensoukyo.mcgattribute.processor.AbstractProcessor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = MCGAttribute.MOD_ID)
public class BattleEventHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase source = (EntityLivingBase) event.getSource().getTrueSource();
            EntityLivingBase target = event.getEntityLiving();
            HashMap<String, Float> vars = new HashMap<>();
            vars.put("input", event.getAmount());
            vars.put("final", 0.0F);
            // 获取此次要用的处理器
            ArrayList<AbstractProcessor> processors = AttributeCache.getOrCreate(source).getProcessors(EnumAttributeType.ATTACK);
            ArrayList<AbstractProcessor> defenceProcessors = AttributeCache.getOrCreate(target).getProcessors(EnumAttributeType.DEFENCE);
            for (AbstractProcessor defenceProcessor : defenceProcessors) {
                if (!processors.contains(defenceProcessor)) {
                    processors.add(defenceProcessor);
                }
            }
            // 奇奇怪怪的排序方法 TODO 改进
            ArrayList<AbstractProcessor> sortedProcessors = new ArrayList<>();
            for (AbstractProcessor abstractProcessor : ProcessorChains.DAMAGE) {
                if (processors.contains(abstractProcessor)) {
                    sortedProcessors.add(abstractProcessor);
                }
            }
            // 依次执行处理器，如果处理器返回false，终止
            for (AbstractProcessor iAttributeProcessor : sortedProcessors) {
                if (!iAttributeProcessor.run(source, target, vars)) {
                    return;
                }
            }
            // 设置最终伤害值
            event.setAmount(vars.getOrDefault("final", event.getAmount()));
        }
    }

}
