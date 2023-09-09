package technobaboo.crazygadgets;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import technobaboo.crazygadgets.entity.CrazyGadgetsEntities;
import technobaboo.crazygadgets.renderers.ChronoPearlEntityRenderer;
import technobaboo.crazygadgets.util.ClientModKeyBindings;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;

public class CrazyGadgetsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerItemEntityRenders(
				CrazyGadgetsEntities.CAPTURE_BALL);
		EntityRendererRegistry.register(CrazyGadgetsEntities.CHRONO_PEARL, ChronoPearlEntityRenderer::new);
		ClientTickEvents.START_CLIENT_TICK.register(ClientModKeyBindings::onStartTick);
	}

	@SafeVarargs
	private static void registerItemEntityRenders(EntityType<? extends FlyingItemEntity>... entityTypes) {
		for (EntityType<? extends FlyingItemEntity> entityType : entityTypes) {
			registerItemEntityRender(entityType);
		}
	}

	private static <T extends Entity & FlyingItemEntity> void registerItemEntityRender(EntityType<T> entityType) {
		EntityRendererRegistry.register(entityType, ctx -> new FlyingItemEntityRenderer<>(ctx));
	}
}
