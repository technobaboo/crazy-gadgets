package technobaboo.crazygadgets.renderers;

import org.joml.Vector3f;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import technobaboo.crazygadgets.item.CrazyGadgetsItems;

public class IonEngineTrinketRenderer implements TrinketRenderer {
    public static void addToRegistry() {
        TrinketRendererRegistry.registerRenderer(CrazyGadgetsItems.ION_ENGINE, new IonEngineTrinketRenderer());
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
            float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
            float headPitch) {

        if (!(entity instanceof AbstractClientPlayerEntity)) {
            return;
        }
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) entity;
        PlayerEntityModel<AbstractClientPlayerEntity> model = (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel;

        matrices.push();
        TrinketRenderer.translateToLeftLeg(matrices, model, player);

        Vector3f worldPosition = new Vector3f(0.0f).mulPosition(matrices.peek().getPositionMatrix());
        entity.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, worldPosition.x, worldPosition.y,
                worldPosition.z, 0.0, 0.0, 0.0);

        matrices.pop();
    }

}
