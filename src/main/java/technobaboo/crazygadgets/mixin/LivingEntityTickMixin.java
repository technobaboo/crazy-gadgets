package technobaboo.crazygadgets.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import technobaboo.crazygadgets.behavior.Deflection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityTickMixin extends Entity {

    public LivingEntityTickMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void projectileTick(CallbackInfo ci) {
        LivingEntity thisObject = (LivingEntity) (Object) this;
        Deflection.handleDeflectProjectile(thisObject);
    }

}
