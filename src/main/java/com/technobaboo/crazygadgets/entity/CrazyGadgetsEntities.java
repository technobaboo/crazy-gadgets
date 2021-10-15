package com.technobaboo.crazygadgets.entity;

import com.technobaboo.crazygadgets.CrazyGadgetsMod;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CrazyGadgetsEntities {
	public static final EntityType<ChronoPearlEntity> CHRONO_PEARL = 
		FabricEntityTypeBuilder.<ChronoPearlEntity>create(SpawnGroup.MISC,ChronoPearlEntity::new)
				.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
				.disableSummon()
				.trackRangeBlocks(4).trackedUpdateRate(10)
				.build();
	public static final EntityType<CaptureBallEntity> CAPTURE_BALL = 
		FabricEntityTypeBuilder.<CaptureBallEntity>create(SpawnGroup.MISC,CaptureBallEntity::new)
				.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
				.disableSummon()
				.trackRangeBlocks(4).trackedUpdateRate(10)
				.build();

	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, new Identifier(CrazyGadgetsMod.MODID, "chrono_pearl"), CHRONO_PEARL);
		Registry.register(Registry.ENTITY_TYPE, new Identifier(CrazyGadgetsMod.MODID, "capture_ball"), CAPTURE_BALL);
	}

	public static void initClient() {
		EntityRendererRegistry.register(CHRONO_PEARL, (context) -> new ChronoPearlEntityRenderer(context));
		EntityRendererRegistry.register(CAPTURE_BALL, (context) -> new FlyingItemEntityRenderer<CaptureBallEntity>(context));
	}
}
