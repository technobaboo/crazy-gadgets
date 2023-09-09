package technobaboo.crazygadgets.behavior;

import java.util.Optional;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import technobaboo.crazygadgets.util.ModKeyBindings;

import static technobaboo.crazygadgets.item.CrazyGadgetsItems.ION_ENGINE;

public class Engine {
    public static void fly(PlayerEntity player) {
        // Don't fly in creative
        if (player.isCreative()) {
            return;
        }

        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
        if (!component.isPresent() || !component.get().isEquipped(ION_ENGINE)) {
            player.getAbilities().allowFlying = false;
            return;
        }
        int equippedCount = component.get().getEquipped(ION_ENGINE).size();
        player.getAbilities().allowFlying = !player.isOnGround() && equippedCount == 4;
        player.getAbilities().flying &= player.getAbilities().allowFlying;
        if (player.isOnGround() || player.getAbilities().flying) {
            if (player.isFallFlying()) {
                player.stopFallFlying();
            }
            return;
        }

        float maxThrust = ((float) equippedCount) / 4;
        if (ModKeyBindings.getPlayer(player).isPressingSprint()) {
            if (!player.isFallFlying()) {
                player.startFallFlying();
            }
            fallFly(player, maxThrust);
        } else {
            if (player.isFallFlying()) {
                player.stopFallFlying();
            }
            if (ModKeyBindings.getPlayer(player).isPressingJump()) {
                flyUpward(player, maxThrust);
            }
        }

    }

    public static void flyUpward(PlayerEntity player, float thrustMultiplier) {
        player.fallDistance /= 2;
        // isFallFlying = false;

        double speed = 0.5 * thrustMultiplier;
        player.setVelocity(player.getVelocity().add(0.0, speed, 0.0));
        if (player.getVelocity().y > speed) {
            player.setVelocity(player.getVelocity().x, speed, player.getVelocity().z);
        }
    }

    public static void fallFly(PlayerEntity player, float thrustMultiplier) {
        if (player.isOnGround()) {
            player.fallDistance /= 2;
            return;
        }

        double speed = 0.8 * thrustMultiplier;
        Vec3d rotationVector = player.getRotationVector().multiply(speed);
        Vec3d velocity = player.getVelocity();
        player.setVelocity(velocity.add(rotationVector.x * 0.1 + (rotationVector.x * 1.5 - velocity.x) * 0.5,
                rotationVector.y * 0.1 + (rotationVector.y * 1.5 - velocity.y) * 0.5,
                rotationVector.z * 0.1 + (rotationVector.z * 1.5 - velocity.z) * 0.5));
    }
}
