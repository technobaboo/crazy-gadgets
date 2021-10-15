package com.technobaboo.crazygadgets.block;

import com.technobaboo.crazygadgets.CrazyGadgetsMod;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CrazyGadgetsBlocks {
	public static final Block CHRONO_DISPLACER = new ChronoDisplacer(FabricBlockSettings.of(Material.METAL, MapColor.BRIGHT_RED).requiresTool().strength(4.0F, 6.0F).sounds(BlockSoundGroup.METAL));
	public static final Block COPPER_WRAPPED_IRON_BLOCK = new Block(FabricBlockSettings.of(Material.METAL, MapColor.DULL_RED).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
	public static final Block MAGNETIC_IRON_BLOCK = new MagneticIronBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.NETHERITE));

	public static void init() {
		register(new Identifier(CrazyGadgetsMod.MODID, "chrono_displacer"),          CHRONO_DISPLACER);
		register(new Identifier(CrazyGadgetsMod.MODID, "copper_wrapped_iron_block"), COPPER_WRAPPED_IRON_BLOCK);
		register(new Identifier(CrazyGadgetsMod.MODID, "magnetic_iron_block"),       MAGNETIC_IRON_BLOCK);
	}

	protected static void register(Identifier id, Block block) {
		Registry.register(Registry.BLOCK, id, block);
		Registry.register(Registry.ITEM, id, new BlockItem(block, new FabricItemSettings()));
	}
}
