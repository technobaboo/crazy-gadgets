package com.technobaboo.crazygadgets.packet;

import java.util.UUID;

import com.technobaboo.crazygadgets.CrazyGadgetsMod;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public class CrazyGadgetsPackets {
	public static final Identifier ENTITY_SPAWN_ID = new Identifier(CrazyGadgetsMod.MODID, "spawn_packet");

	public static void initClient() {
		ClientPlayNetworking.registerGlobalReceiver(ENTITY_SPAWN_ID, (client, handler, buf, responseSender) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.get(buf.readVarInt());
			UUID uuid = buf.readUuid();
			int entityId = buf.readVarInt();
			Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(buf);
			float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
			float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
			client.execute(() -> {
				if (client.world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(client.world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos);
				e.setPos(pos.x, pos.y, pos.z);
				e.setPitch(pitch);
				e.setYaw(yaw);
				e.setId(entityId);
				e.setUuid(uuid);
				client.world.addEntity(entityId, e);
			});
		});
	}
}
