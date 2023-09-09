package technobaboo.crazygadgets.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import technobaboo.crazygadgets.CrazyGadgets;
import technobaboo.crazygadgets.item.CrazyGadgetsItems;

public class CrazyGadgetsBlocks {
	// public static final Block CHRONO_DISPLACER = new
	// ChronoDisplacer(FabricBlockSettings.of(Material.METAL,
	// MapColor.BRIGHT_RED).requiresTool().strength(4.0F,
	// 6.0F).sounds(BlockSoundGroup.METAL));
	public static final Block COPPER_WRAPPED_IRON_BLOCK = register("copper_wrapped_iron_block",
			new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
	public static final Block MAGNETIC_IRON_BLOCK = register("magnetic_iron_block",
			new MagneticIronBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
					.sounds(BlockSoundGroup.NETHERITE)));
	public static final Block NEGATIVE_MASS_BLOCK = register("negative_mass",
			new MagneticIronBlock(FabricBlockSettings.copyOf(Blocks.BEDROCK)
					.sounds(BlockSoundGroup.COPPER).luminance(12).solid().resistance(100000000.0f)));

	protected static Block register(String id, Block block) {
		Registry.register(Registries.BLOCK, new Identifier(CrazyGadgets.MOD_ID, id), block);
		CrazyGadgetsItems.registerItem(id, new BlockItem(block, new FabricItemSettings()));
		return block;
	}
}
