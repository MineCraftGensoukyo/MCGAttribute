package moe.gensoukyo.mcgattribute.client.event;

import moe.gensoukyo.mcgattribute.MCGAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber(modid = MCGAttribute.MOD_ID, value = Side.CLIENT)
@SideOnly(Side.CLIENT)
public class RenderEventHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            assert compound != null;
            if (compound.hasKey("CustomAttribute", 10)) {
                List<String> tooltips = event.getToolTip();
                NBTTagCompound compound1 = compound.getCompoundTag("CustomAttribute");
                if (compound1.hasKey("Cached")) {
                    NBTTagCompound compound2 = compound1.getCompoundTag("Cached");
                }
            }
        }
    }
}
