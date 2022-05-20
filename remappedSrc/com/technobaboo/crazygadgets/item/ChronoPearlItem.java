package com.technobaboo.crazygadgets.item;

import com.technobaboo.crazygadgets.entity.ChronoPearlEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ChronoPearlItem extends Item {
	public ChronoPearlItem(Settings settings) {
		super(settings);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stackInHand = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		user.getItemCooldownManager().set(this, 5);

		if (!world.isClient) {
			ChronoPearlEntity chronoPearlEntity = new ChronoPearlEntity(world, user);
			chronoPearlEntity.setItem(stackInHand);
//			chronoPearlEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
			world.spawnEntity(chronoPearlEntity);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) {
			stackInHand.decrement(1);
		}

		return TypedActionResult.success(stackInHand, world.isClient());
	}
}
