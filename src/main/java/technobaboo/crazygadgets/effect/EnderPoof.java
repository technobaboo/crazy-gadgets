package technobaboo.crazygadgets.effect;

import java.util.Random;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnderPoof {
	public static void Poof(World world, Vec3d position) {
		world.playSound(null, position.getX(), position.getY(), position.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT,
				SoundCategory.AMBIENT, 1F, 1F);
		Random rand = new Random((long) position.lengthSquared());
		if (world.isClient()) {
			for (int i = 0; i < 500; ++i) {
				double x = (rand.nextDouble() - 0.5) * 2;
				double y = (rand.nextDouble() - 0.5) * 2;
				double z = (rand.nextDouble() - 0.5) * 2;
				Vec3d vec = new Vec3d(x, y, z);
				Vec3d particlePos = position.add(vec);
				Vec3d particleVel = vec.multiply(-0.25);
				world.addParticle(ParticleTypes.PORTAL, false, particlePos.x, particlePos.y, particlePos.z,
						particleVel.x, particleVel.y, particleVel.z);
				// world.addParticle(ParticleTypes.PORTAL, false, entity.getX(), entity.getY(),
				// entity.getZ(), 0, 0, 0);
			}
		}
	}
}
