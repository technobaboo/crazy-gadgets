package technobaboo.crazygadgets.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import static technobaboo.crazygadgets.item.CrazyGadgetsItems.ION_ENGINE;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    public void render(AbstractClientPlayerEntity player, float f, float g, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        PlayerEntityRenderer renderer = (PlayerEntityRenderer) (Object) this;
        spawnParticles(player.getWorld(), player, renderer.getModel());
    }

    private static void spawnParticles(World world, AbstractClientPlayerEntity entity,
            PlayerEntityModel<AbstractClientPlayerEntity> model) {
        TrinketComponent trinkets = TrinketsApi.getTrinketComponent(entity).get();
        for (var placement : trinkets.getEquipped(ION_ENGINE)) {
            switch (placement.getLeft().index()) {
                case 0: {
                    spawnParticles(world, entity, model.rightArm.pivotX + 0.05, entity.isFallFlying() ? 0.0 : 0.8,
                            -0.45);
                }
                    break;
                case 1: {
                    spawnParticles(world, entity, model.leftArm.pivotX + 0.05, entity.isFallFlying() ? 0.0 : 0.8, 0.45);
                }
                    break;
                case 2: {
                    spawnParticles(world, entity, model.rightLeg.pivotX + 0.05, entity.isFallFlying() ? 0.1 : 0.0,
                            -0.1);
                }
                    break;
                case 3: {
                    spawnParticles(world, entity, model.leftLeg.pivotX + 0.05, entity.isFallFlying() ? 0.1 : 0.0, 0.1);
                }
                    break;
            }
        }

    }

    // Spawns particles at the limbs of the player
    private static void spawnParticles(World world, AbstractClientPlayerEntity entity, double pitch, double yOffset,
            double zOffset) {
        double yaw = entity.bodyYaw;
        double xRotator = Math.cos(yaw * Math.PI / 180.0) * zOffset;
        double zRotator = Math.sin(yaw * Math.PI / 180.0) * zOffset;
        double xRotator1 = Math.cos((yaw - 90) * Math.PI / 180.0) * pitch;
        double zRotator1 = Math.sin((yaw - 90) * Math.PI / 180.0) * pitch;

        world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, entity.getX() + xRotator + xRotator1,
                entity.getY() + yOffset, entity.getZ() + zRotator1 + zRotator, 0.0, 0.0, 0.0);
    }
}
