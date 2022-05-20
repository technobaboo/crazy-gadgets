package com.technobaboo.crazygadgets.entity;

import com.technobaboo.crazygadgets.CrazyGadgets;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CrazyGadgetsEntities {
	public static final EntityType<ChronoPearlEntity> CHRONO_PEARL = FabricEntityTypeBuilder
			.<ChronoPearlEntity>create(SpawnGroup.MISC, ChronoPearlEntity::new)
			.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
			.disableSummon()
			.trackRangeBlocks(4).trackedUpdateRate(10)
			.build();
	public static final EntityType<CaptureBallEntity> CAPTURE_BALL = FabricEntityTypeBuilder
			.<CaptureBallEntity>create(SpawnGroup.MISC, CaptureBallEntity::new)
			.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
			.disableSummon()
			.trackRangeBlocks(4).trackedUpdateRate(10)
			.build();

	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(CrazyGadgets.MODID, "chrono_pearl"), CHRONO_PEARL);
		Registry.register(Registry.ENTITY_TYPE, new Identifier(CrazyGadgets.MODID, "capture_ball"), CAPTURE_BALL);
	}
}
