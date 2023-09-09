package technobaboo.crazygadgets.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import technobaboo.crazygadgets.CrazyGadgets;
import technobaboo.crazygadgets.entity.CaptureBallEntity;
import technobaboo.crazygadgets.entity.ChronoPearlEntity;

public class CrazyGadgetsItems {
	public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
			new Identifier(CrazyGadgets.MODID, "general"),
			FabricItemGroup.builder().displayName(Text.translatable("itemGroup.crazy_gadgets.general"))
					.icon(() -> new ItemStack(CrazyGadgetsItems.MAGNETIC_IRON_INGOT))
					.build());

	public static final ChronoPearlItem CHRONO_PEARL = registerItem("chrono_pearl", new ChronoPearlItem(
			new FabricItemSettings().maxCount(16)));
	// public static final Item REDSTONE_INFUSED_COPPER_INGOT =
	// registerItem("redstone_infused_copper_ingot",
	// new Item(new FabricItemSettings()));
	public static final Item PCB = registerItem("pcb", new Item(new FabricItemSettings()));
	public static final CaptureBallItem CAPTURE_BALL = registerItem("capture_ball",
			new CaptureBallItem(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON)));

	public static final Item COPPER_PLATE = registerItem("copper_plate", new Item(new FabricItemSettings()));
	public static final Item MAGNETIC_IRON_INGOT = registerItem("magnetic_iron_ingot", new Item(
			new FabricItemSettings()));
	public static final Item MAGNETIC_IRON_BALL = registerItem("magnetic_iron_ball", new Item(
			new FabricItemSettings()));
	public static final Item COPPER_SHEET = registerItem("copper_sheet", new Item(new FabricItemSettings()));
	public static final Item COPPER_WIRE = registerItem("copper_wire", new Item(new FabricItemSettings()));
	public static final MagneticDeflectorItem MAGNETIC_DEFLECTOR = registerItem("magnetic_deflector",
			new MagneticDeflectorItem(
					new FabricItemSettings()));
	public static final IonEngineItem ION_ENGINE = registerItem("ion_engine", new IonEngineItem(
			new FabricItemSettings().maxCount(1)));
	// public static final Item POWER_CORE = registerItem("power_core", new Item(new
	// FabricItemSettings().rarity(Rarity.EPIC).maxCount(1).maxDamage(10000));

	public static void init() {
		DispenserBlock.registerBehavior(CrazyGadgetsItems.CAPTURE_BALL, new ProjectileDispenserBehavior() {
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				CaptureBallEntity captureBallEntity = new CaptureBallEntity(world, position.getX(), position.getY(),
						position.getZ());
				return captureBallEntity;
			}
		});
		DispenserBlock.registerBehavior(CrazyGadgetsItems.CHRONO_PEARL, new ProjectileDispenserBehavior() {
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				ChronoPearlEntity chronoPearlEntity = new ChronoPearlEntity(world, position.getX(), position.getY(),
						position.getZ());
				return chronoPearlEntity;
			}
		});
	}

	public static <T extends Item> T registerItem(String id, T item) {
		Registry.register(Registries.ITEM, new Identifier(CrazyGadgets.MODID, id), item);
		ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getEntry(ITEM_GROUP).getKey().get())
				.register(content -> content.add(item));
		return item;
	}
}
