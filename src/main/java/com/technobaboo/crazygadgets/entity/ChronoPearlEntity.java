package com.technobaboo.crazygadgets.entity;

import com.technobaboo.crazygadgets.effect.EnderPoof;
import com.technobaboo.crazygadgets.item.CrazyGadgetsItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ChronoPearlEntity extends ThrownItemEntity {
	public static final TrackedData<NbtCompound> ENTITY_NBT;
	private static final TrackedData<Integer> TIMEOUT;

	public ChronoPearlEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public ChronoPearlEntity(World world) {
		super(CrazyGadgetsEntities.CHRONO_PEARL, 0, 0, 0, world);
	}

	public ChronoPearlEntity(World world, LivingEntity owner) {
		super(CrazyGadgetsEntities.CHRONO_PEARL, owner, world);
	}

	public ChronoPearlEntity(World world, double x, double y, double z) {
		super(CrazyGadgetsEntities.CHRONO_PEARL, x, y, z, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ENTITY_NBT, new NbtCompound());
		this.dataTracker.startTracking(TIMEOUT, 200);
	}

	@Override
	protected Item getDefaultItem() {
		return CrazyGadgetsItems.CHRONO_PEARL;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
			NbtCompound heldEntityNBT = new NbtCompound();
			entity.saveNbt(heldEntityNBT);
			this.dataTracker.set(ENTITY_NBT, heldEntityNBT);
			// this.setPosition(entity.getX(), entity.getY(), entity.getZ());
			// this.setRotation(entity.getYaw(), entity.getPitch());
			this.setNoGravity(true);
			this.setVelocity(0, 0, 0);
			this.noClip = true;
			EnderPoof.Poof(world, entity.getPos());
			entity.discard();

			// CrazyGadgetsMod.LOGGER.info("Chrono Pearl hit entity");
		} else {
			super.onEntityHit(entityHitResult);
			this.discard();
		}
	}

	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient && hitResult.getType() != HitResult.Type.ENTITY) {
			this.world.sendEntityStatus(this, (byte) 3); // particle?
			this.discard(); // kills the projectile
		}
	}

	@Override
	public void tick() {
		if (!this.dataTracker.get(ENTITY_NBT).isEmpty()) {
			int timeout = this.dataTracker.get(TIMEOUT);
			if (timeout == 0) {
				Entity entity = EntityType.loadEntityWithPassengers(this.dataTracker.get(ENTITY_NBT), world,
						(entityx) -> {
							entityx.refreshPositionAndAngles(entityx.getX(), entityx.getY(), entityx.getZ(),
									entityx.getYaw(), entityx.getPitch());
							return entityx;
						});
				world.spawnEntity(entity);
				EnderPoof.Poof(world, entity.getPos());
				this.discard();
				return;
			}
			this.dataTracker.set(TIMEOUT, --timeout);
		} else {
			super.tick();
		}
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.put("DisplacedEntity", this.dataTracker.get(ENTITY_NBT));
		nbt.putInt("Timeout", this.dataTracker.get(TIMEOUT));
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("DisplacedEntity"))
			this.dataTracker.set(ENTITY_NBT, nbt.getCompound("DisplacedEntity"));
		if (nbt.contains("Timeout"))
			this.dataTracker.set(TIMEOUT, nbt.getInt("Timeout"));
	}

	static {
		ENTITY_NBT = DataTracker.registerData(ChronoPearlEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
		TIMEOUT = DataTracker.registerData(ChronoPearlEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}
