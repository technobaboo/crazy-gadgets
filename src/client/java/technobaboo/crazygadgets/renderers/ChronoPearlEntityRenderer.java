package technobaboo.crazygadgets.renderers;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import technobaboo.crazygadgets.entity.ChronoPearlEntity;

public class ChronoPearlEntityRenderer extends FlyingItemEntityRenderer<ChronoPearlEntity> {
	public ChronoPearlEntityRenderer(EntityRendererFactory.Context context) {
		super(context, 1.0F, false);
	}

	@Override
	public void render(ChronoPearlEntity entity, float yaw, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light) {
		if (entity.getDataTracker().get(ChronoPearlEntity.ENTITY_NBT).isEmpty())
			super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}
}
