package com.technobaboo.crazygadgets.behavior;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Optional;

import static com.technobaboo.crazygadgets.item.CrazyGadgetsItems.MAGNETIC_DEFLECTOR;

public class Deflection {
    private static final double radius = 4;
    private static final double radiusSqr = Math.pow(radius, 2);
    private static final Vec3d radiusVector = new Vec3d(radius, radius, radius);

    private static Vec3d deflectingEntityCenter;
    protected static boolean isDeflectable(Entity entity) {
        final boolean inRadius = entity.getPos().subtract(deflectingEntityCenter).lengthSquared() < radiusSqr;
        final boolean isProjectile = entity instanceof ProjectileEntity;
        return isProjectile && inRadius;
    }

    public static void handleDeflectProjectile(LivingEntity thisObject) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(thisObject);
        if(component.isPresent() && component.get().isEquipped(MAGNETIC_DEFLECTOR)) {
            Vec3d centerPos = thisObject.getPos().add(0, thisObject.getHeight() / 2, 0);
            deflectingEntityCenter = centerPos;
            Box deflectionBox = new Box(centerPos.add(radiusVector), centerPos.subtract(radiusVector));
            List<Entity> deflectableEntities = thisObject.world.getOtherEntities(thisObject, deflectionBox, Deflection::isDeflectable);
            for (Entity entity : deflectableEntities) {
                Vec3d direction = centerPos.subtract(entity.getPos()).normalize();
                double angle = Math.acos(direction.dotProduct(entity.getVelocity().normalize()));
                System.out.println(Math.toDegrees(angle));
                if(angle < Math.toRadians(60)) {
                    double speed = entity.getVelocity().length();
                    ProjectileEntity projectile = (ProjectileEntity) entity;
                    if(projectile.getOwner() != null && projectile.getOwner().getUuid() == thisObject.getUuid()) {
                        continue;
                    }
//                    entity.setVelocity(direction.multiply(-speed));
                    projectile.setVelocity(direction.x, direction.y, direction.z, (float) -speed, 0);
//                    projectile.damage(DamageSource.magic(projectile, projectile), 0);
//                    projectile.setOwner(thisObject);
                    if(projectile instanceof ExplosiveProjectileEntity) {
                        ExplosiveProjectileEntity explosiveProjectile = (ExplosiveProjectileEntity) projectile;
                        explosiveProjectile.powerX = -direction.x * 0.1D;
                        explosiveProjectile.powerY = -direction.y * 0.1D;
                        explosiveProjectile.powerZ = -direction.z * 0.1D;
                    }
                }
            }
        }
    }
}
