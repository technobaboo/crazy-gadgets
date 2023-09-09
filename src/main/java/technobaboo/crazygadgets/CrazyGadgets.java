package technobaboo.crazygadgets;

import net.fabricmc.api.ModInitializer;
import technobaboo.crazygadgets.block.CrazyGadgetsBlocks;
import technobaboo.crazygadgets.item.CrazyGadgetsItems;
import technobaboo.crazygadgets.packet.KeybindPacket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrazyGadgets implements ModInitializer {
	public static final String MOD_ID = "crazy_gadgets";
	public static final Logger LOGGER = LogManager.getLogger("Crazy Gadgets");

	@Override
	public void onInitialize() {
		CrazyGadgetsItems.init();
		CrazyGadgetsBlocks.init();
		KeybindPacket.registerServerHandler();

		LOGGER.info("Crazy Gadgets loaded!");
	}
}
