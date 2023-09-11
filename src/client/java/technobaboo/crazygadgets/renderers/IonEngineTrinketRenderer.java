package technobaboo.crazygadgets.renderers;

import org.joml.Quaternionf;
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
import net.minecraft.util.math.RotationAxis;
import technobaboo.crazygadgets.behavior.Engine;
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
        if (!Engine.flying(player)) {
            return;
        }
        PlayerEntityModel<AbstractClientPlayerEntity> model = (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel;

        MatrixStack legTransform = new MatrixStack();
        legTransform.translate(entity.getX(), entity.getY(), entity.getZ());
        legTransform.multiply(RotationAxis.POSITIVE_Y.rotation(player.getBodyYaw()));
        matrices.translate(0.125F, 0.75F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(model.leftLeg.roll));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(model.leftLeg.yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(model.leftLeg.pitch));
        matrices.translate(0.125F, 0.75F, 0.0F);

        Vector3f worldPosition = new Vector3f().mulPosition(legTransform.peek().getPositionMatrix());
        entity.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, worldPosition.x, worldPosition.y,
                worldPosition.z, 0.0, 0.0, 0.0);
    }

    // private static void spawnParticles(World world, AbstractClientPlayerEntity
    // entity,
    // PlayerEntityModel<AbstractClientPlayerEntity> model) {
    // TrinketComponent trinkets = TrinketsApi.getTrinketComponent(entity).get();
    // for (var placement : trinkets.getEquipped(ION_ENGINE)) {
    // switch (placement.getLeft().index()) {
    // case 0: {
    // spawnParticles(world, entity, model.rightArm.pivotX + 0.05,
    // entity.isFallFlying() ? 0.0 : 0.8,
    // -0.45);
    // }
    // break;
    // case 1: {
    // spawnParticles(world, entity, model.leftArm.pivotX + 0.05,
    // entity.isFallFlying() ? 0.0 : 0.8, 0.45);
    // }
    // break;
    // case 2: {
    // spawnParticles(world, entity, model.rightLeg.pivotX + 0.05,
    // entity.isFallFlying() ? 0.1 : 0.0,
    // -0.1);
    // }
    // break;
    // case 3: {
    // spawnParticles(world, entity, model.leftLeg.pivotX + 0.05,
    // entity.isFallFlying() ? 0.1 : 0.0, 0.1);
    // }
    // break;
    // }
    // }

    // }

    // // Spawns particles at the limbs of the player
    // private static void spawnParticles(World world, AbstractClientPlayerEntity
    // entity, double pitch, double yOffset,
    // double zOffset) {
    // double yaw = entity.bodyYaw;
    // double xRotator = Math.cos(yaw * Math.PI / 180.0) * zOffset;
    // double zRotator = Math.sin(yaw * Math.PI / 180.0) * zOffset;
    // double xRotator1 = Math.cos((yaw - 90) * Math.PI / 180.0) * pitch;
    // double zRotator1 = Math.sin((yaw - 90) * Math.PI / 180.0) * pitch;

    // world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, entity.getX() +
    // xRotator + xRotator1,
    // entity.getY() + yOffset, entity.getZ() + zRotator1 + zRotator, 0.0, 0.0,
    // 0.0);
    // }

}