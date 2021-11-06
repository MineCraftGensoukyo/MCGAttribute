package moe.gensoukyo.mcgattribute;

import moe.gensoukyo.mcgattribute.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = MCGAttribute.MOD_ID,
        name = MCGAttribute.MOD_NAME,
        version = MCGAttribute.VERSION
)
public class MCGAttribute {

    public static final String MOD_ID = "mcgattribute";
    public static final String MOD_NAME = "MCGAttribute";
    public static final String VERSION = "1.0-SNAPSHOT";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    static {
        ModConfig.reloadAll();
    }

}
