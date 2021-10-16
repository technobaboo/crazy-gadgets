package com.technobaboo.crazygadgets.entity;

import com.technobaboo.crazygadgets.item.CrazyGadgetsItems;
import com.technobaboo.crazygadgets.packet.CrazyGadgetsPackets;
import com.technobaboo.crazygadgets.packet.EntitySpawnPacket;


import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CaptureBallEntity extends ThrownItemEntity {
	private static final TrackedData<NbtCompound> ENTITY_NBT;

	public CaptureBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public CaptureBallEntity(World world) {
        super(CrazyGadgetsEntities.CAPTURE_BALL, 0, 0, 0, world);
    }
    public CaptureBallEntity(World world, LivingEntity owner) {
        super(CrazyGadgetsEntities.CAPTURE_BALL, owner, world);
    }
    public CaptureBallEntity(World world, double x, double y, double z) {
        super(CrazyGadgetsEntities.CAPTURE_BALL, x, y, z, world);
    }

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ENTITY_NBT, new NbtCompound());
	}

	@Override
	protected Item getDefaultItem() {
		return CrazyGadgetsItems.CAPTURE_BALL;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		ItemStack item = new ItemStack(CrazyGadgetsItems.CAPTURE_BALL, 1);
		if(entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
			NbtCompound itemNbt = item.getOrCreateNbt();
			NbtCompound entityNbt = item.getOrCreateSubNbt("CapturedEntity");
			entity.saveNbt(entityNbt);
			if(entity instanceof Tameable)
				itemNbt.putString("CapturedEntityType", "tameable");
			else if(entity instanceof Angerable)
				itemNbt.putString("CapturedEntityType", "neutral");
			else if(entity instanceof PassiveEntity)
				itemNbt.putString("CapturedEntityType", "passive");
			else if(entity instanceof HostileEntity)
				itemNbt.putString("CapturedEntityType", "hostile");
			entity.discard();
		}
		Vec3d hitPos = entityHitResult.getPos();
		ItemEntity itemEntity = new ItemEntity(entityHitResult.getEntity().world, hitPos.x, hitPos.y, hitPos.z, item);
		world.spawnEntity(itemEntity);
		this.discard();
	}

	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient && hitResult.getType() != HitResult.Type.ENTITY) {
			PlayerEntity playerOwner = (PlayerEntity) this.getOwner();
			boolean ownerIsPlayer = this.getOwner() instanceof PlayerEntity;
			if(!ownerIsPlayer || (ownerIsPlayer && !playerOwner.isCreative())) {
				ItemStack item = new ItemStack(CrazyGadgetsItems.CAPTURE_BALL, 1);
				Vec3d hitPos = hitResult.getPos();
				ItemEntity itemEntity = new ItemEntity(world, hitPos.x, hitPos.y, hitPos.z, item);
				world.spawnEntity(itemEntity);
			}

			this.discard();
		}
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.put("DisplacedEntity", this.dataTracker.get(ENTITY_NBT));
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("DisplacedEntity"))
			this.dataTracker.set(ENTITY_NBT, nbt.getCompound("DisplacedEntity"));
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntitySpawnPacket.create(this, CrazyGadgetsPackets.ENTITY_SPAWN_ID);
	}

	static {
		ENTITY_NBT = DataTracker.registerData(ChronoPearlEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
	}
}
