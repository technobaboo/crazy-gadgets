package com.technobaboo.crazygadgets;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import com.technobaboo.crazygadgets.item.CrazyGadgetsItems;
import com.technobaboo.crazygadgets.block.CrazyGadgetsBlocks;
import com.technobaboo.crazygadgets.entity.CrazyGadgetsEntities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrazyGadgets implements ModInitializer {
	public static final String MODID = "crazy_gadgets";
	public static final Logger LOGGER = LogManager.getLogger("Crazy Gadgets");

	@Override
	public void onInitialize() {
		CrazyGadgetsBlocks.init();
		CrazyGadgetsItems.init();
		CrazyGadgetsEntities.init();

		LOGGER.info("Crazy Gadgets loaded!");
	}
}
