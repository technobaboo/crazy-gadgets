package technobaboo.crazygadgets.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static technobaboo.crazygadgets.item.CrazyGadgetsItems.MAGNETIC_DEFLECTOR;

import java.util.Optional;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileCollisionMixin extends Entity {
	public ProjectileCollisionMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V"), method = "onCollision", cancellable = true)
	private void onCollisionProjectileDeflection(HitResult hitResult, CallbackInfo ci) {
		EntityHitResult entityHitResult = (EntityHitResult) hitResult;
		Entity hitEntity = entityHitResult.getEntity();
		if (hitEntity instanceof PlayerEntity) {
			LivingEntity hitLivingEntity = (LivingEntity) hitEntity;
			Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(hitLivingEntity);
			if (trinketComponent.isPresent()) {
				if (trinketComponent.get().isEquipped(MAGNETIC_DEFLECTOR)) {
					double speed = this.getVelocity().length();
					// double speed = 1f/100f;
					Vec3d entityCenterPos = hitLivingEntity.getPos().add(0, hitLivingEntity.getHeight() / 2, 0);
					Vec3d direction = this.getPos().subtract(entityCenterPos).normalize();
					this.setVelocity(direction.multiply(speed));
					// this.setVelocity(this.getVelocity().negate());
					ci.cancel();
				}
			}
		}
	}
}
