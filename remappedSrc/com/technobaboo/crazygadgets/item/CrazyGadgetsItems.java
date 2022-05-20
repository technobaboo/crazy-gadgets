package com.technobaboo.crazygadgets.item;

import com.technobaboo.crazygadgets.CrazyGadgetsMod;
import com.technobaboo.crazygadgets.block.CrazyGadgetsBlocks;
import com.technobaboo.crazygadgets.entity.CaptureBallEntity;
import com.technobaboo.crazygadgets.entity.ChronoPearlEntity;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class CrazyGadgetsItems {
	public static final ChronoPearlItem        CHRONO_PEARL                  = new ChronoPearlItem(new FabricItemSettings().maxCount(16).group(ItemGroup.SEARCH));
	// public static final Item                   FIBERGLASS                    = new Item(new FabricItemSettings());
	// public static final Item                   REDSTONE_INFUSED_COPPER_INGOT = new Item(new FabricItemSettings());
	// public static final Item                   PCB                           = new Item(new FabricItemSettings());
	public static final CaptureBallItem        CAPTURE_BALL                  = new CaptureBallItem(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON).group(ItemGroup.SEARCH));
	public static final Item                   COPPER_PLATE                  = new Item(new FabricItemSettings().group(ItemGroup.SEARCH));
	public static final Item                   MAGNETIC_IRON_INGOT           = new Item(new FabricItemSettings().group(ItemGroup.SEARCH));
	public static final Item                   MAGNETIC_IRON_BALL            = new Item(new FabricItemSettings().group(ItemGroup.SEARCH));
	public static final Item                   COPPER_SHEET                  = new Item(new FabricItemSettings().group(ItemGroup.SEARCH));
	public static final Item                   COPPER_WIRE                   = new Item(new FabricItemSettings().group(ItemGroup.SEARCH));
	public static final MagneticDeflectorItem  MAGNETIC_DEFLECTOR            = new MagneticDeflectorItem(new FabricItemSettings().group(ItemGroup.SEARCH));
	public static final IonEngineItem          ION_ENGINE                    = new IonEngineItem(new FabricItemSettings().group(ItemGroup.SEARCH).maxCount(1));
//	public static final Item                   POWER_CORE                    = new Item(new FabricItemSettings().group(ItemGroup.SEARCH).rarity(Rarity.EPIC).maxCount(1).maxDamage(10000));

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(
		new Identifier(CrazyGadgetsMod.MODID, "general"))
		.icon(() -> new ItemStack(MAGNETIC_IRON_INGOT))
		.appendItems(stacks -> {
			// stacks.add(new ItemStack(FIBERGLASS));
			// stacks.add(new ItemStack(REDSTONE_INFUSED_COPPER_INGOT));
			// stacks.add(new ItemStack(PCB));
			stacks.add(new ItemStack(COPPER_PLATE));
			stacks.add(new ItemStack(COPPER_SHEET));
			stacks.add(new ItemStack(COPPER_WIRE));
			stacks.add(new ItemStack(CrazyGadgetsBlocks.COPPER_WRAPPED_IRON_BLOCK));
			stacks.add(new ItemStack(CrazyGadgetsBlocks.MAGNETIC_IRON_BLOCK));
			stacks.add(new ItemStack(MAGNETIC_IRON_INGOT));
			stacks.add(new ItemStack(MAGNETIC_IRON_BALL));
			stacks.add(new ItemStack(MAGNETIC_DEFLECTOR));
			stacks.add(new ItemStack(ION_ENGINE));
//			stacks.add(new ItemStack(POWER_CORE));
			 stacks.add(new ItemStack(CHRONO_PEARL));
			 stacks.add(new ItemStack(CAPTURE_BALL));
			// stacks.add(new ItemStack(CrazyGadgetsBlocks.CHRONO_DISPLACER));
			
		})
		.build();

	public static void init() {
		// Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "fiberglass"), FIBERGLASS);
		// Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "redstone_infused_copper_ingot"), REDSTONE_INFUSED_COPPER_INGOT);
		// Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "pcb"), PCB);
		 Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "capture_ball"), CAPTURE_BALL);
		 Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "chrono_pearl"), CHRONO_PEARL);
		Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "copper_plate"), COPPER_PLATE);
		Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "copper_sheet"), COPPER_SHEET);
		Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "copper_wire"), COPPER_WIRE);
		Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "magnetic_iron_ingot"), MAGNETIC_IRON_INGOT);
		Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "magnetic_iron_ball"), MAGNETIC_IRON_BALL);
		Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "magnetic_deflector"), MAGNETIC_DEFLECTOR);
		Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "ion_engine"), ION_ENGINE);
//		Registry.register(Registry.ITEM, new Identifier(CrazyGadgetsMod.MODID, "power_core"), POWER_CORE);

		DispenserBlock.registerBehavior(CrazyGadgetsItems.CAPTURE_BALL, new ProjectileDispenserBehavior() {
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				CaptureBallEntity captureBallEntity = new CaptureBallEntity(world, position.getX(), position.getY(), position.getZ());
				return captureBallEntity;
			}
		});
		DispenserBlock.registerBehavior(CrazyGadgetsItems.CHRONO_PEARL, new ProjectileDispenserBehavior() {
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				ChronoPearlEntity chronoPearlEntity = new ChronoPearlEntity(world, position.getX(), position.getY(), position.getZ());
				return chronoPearlEntity;
			}
		});
	}
}
