package technobaboo.crazygadgets.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import technobaboo.crazygadgets.CrazyGadgets;

public class CrazyGadgetsEntities {
	public static final EntityType<ChronoPearlEntity> CHRONO_PEARL = register("chrono_pearl", FabricEntityTypeBuilder
			.<ChronoPearlEntity>create(SpawnGroup.MISC, ChronoPearlEntity::new)
			.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
			.disableSummon()
			.trackRangeBlocks(4).trackedUpdateRate(10)
			.build());
	public static final EntityType<CaptureBallEntity> CAPTURE_BALL = register("capture_ball", FabricEntityTypeBuilder
			.<CaptureBallEntity>create(SpawnGroup.MISC, CaptureBallEntity::new)
			.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
			.disableSummon()
			.trackRangeBlocks(4).trackedUpdateRate(10)
			.build());

	static <T extends Entity> EntityType<T> register(String id, EntityType<T> entity) {
		return Registry.register(Registries.ENTITY_TYPE, new Identifier(CrazyGadgets.MOD_ID, id), entity);
	}
}
