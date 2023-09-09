package technobaboo.crazygadgets;

import net.fabricmc.api.ModInitializer;
import technobaboo.crazygadgets.item.CrazyGadgetsItems;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrazyGadgets implements ModInitializer {
	public static final String MODID = "crazy_gadgets";
	public static final Logger LOGGER = LogManager.getLogger("Crazy Gadgets");

	@Override
	public void onInitialize() {
		CrazyGadgetsItems.init();

		LOGGER.info("Crazy Gadgets loaded!");
	}
}
