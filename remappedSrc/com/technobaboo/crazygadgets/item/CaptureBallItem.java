package com.technobaboo.crazygadgets.item;

import java.util.List;
import java.util.Optional;

import com.technobaboo.crazygadgets.entity.CaptureBallEntity;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CaptureBallItem extends Item {
	private boolean used = false;

	public CaptureBallItem(Settings settings) {
		super(settings);
	}

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if(entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
			if(!stack.hasNbt()) {
				if(!user.world.isClient()) {
					ItemStack newOrb = new ItemStack(CrazyGadgetsItems.CAPTURE_BALL, 1);
					NbtCompound itemNbt = newOrb.getOrCreateNbt();
					NbtCompound entityNbt = newOrb.getOrCreateSubNbt("CapturedEntity");
					entity.saveNbt(entityNbt);
					if(entity instanceof Tameable)
						itemNbt.putString("CapturedEntityType", "tameable");
					else if(entity instanceof Angerable)
						itemNbt.putString("CapturedEntityType", "neutral");
					else if(entity instanceof PassiveEntity)
						itemNbt.putString("CapturedEntityType", "passive");
					else if(entity instanceof HostileEntity)
						itemNbt.putString("CapturedEntityType", "hostile");
					entity.saveNbt(entityNbt);
					entity.discard();
					int count = stack.getCount();
					if(!user.isCreative())
						user.setStackInHand(hand, new ItemStack(CrazyGadgetsItems.CAPTURE_BALL, count-1));
					user.giveItemStack(newOrb);
					user.incrementStat(Stats.USED.getOrCreateStat(this));
				}
				used = true;
				return ActionResult.PASS;
			}
		}

		return ActionResult.FAIL;
	}
	public ActionResult useOnBlock(ItemUsageContext context) {
		ItemStack stack = context.getStack();
		if(stack.hasNbt()) {
			World world = context.getWorld();
			if(!world.isClient()) {
				// BlockPos blockPos = context.getBlockPos();
				Vec3d pos = context.getHitPos();
				Entity entity = EntityType.loadEntityWithPassengers(stack.getSubNbt("CapturedEntity"), world, (entityx) -> { 
					entityx.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), entityx.getYaw(), entityx.getPitch());
					return entityx;
				});
				world.spawnEntity(entity);
				context.getPlayer().setStackInHand(context.getHand(), ItemStack.EMPTY);
				if(!context.getPlayer().isCreative())
					context.getPlayer().giveItemStack(new ItemStack(CrazyGadgetsItems.CAPTURE_BALL, 1));
				context.getPlayer().incrementStat(Stats.USED.getOrCreateStat(this));
			}
		} else {
			throwProjectile(context.getWorld(), context.getPlayer(), stack);
		}
		used = true;
		return ActionResult.PASS;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stackInHand = user.getStackInHand(hand);
		if(!stackInHand.hasNbt() && !used) {
			throwProjectile(world, user, stackInHand);
			return TypedActionResult.success(stackInHand, true);
		} else {
			used = false;
			return TypedActionResult.fail(stackInHand);
		}
	}

	protected void throwProjectile(World world, PlayerEntity user, ItemStack stack) {
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		user.getItemCooldownManager().set(this, 1);

		if (!world.isClient) {
			CaptureBallEntity captureBallEntity = new CaptureBallEntity(world, user);
			captureBallEntity.setItem(stack);
//			captureBallEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
			world.spawnEntity(captureBallEntity);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) {
			stack.decrement(1);
		}
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
		if(itemStack.hasNbt()) {
			String entityIDString = itemStack.getSubNbt("CapturedEntity").getString("id");
			Identifier entityID = new Identifier(entityIDString);
			Optional<EntityType<?>> entityType = EntityType.get(entityIDString);
			if(!entityType.isEmpty()) {
				Formatting formatting = Formatting.RESET;
				String entityTypeString = itemStack.getNbt().getString("CapturedEntityType");

				if(entityTypeString == "hostile")
					formatting = Formatting.RED;
				else if(entityTypeString == "passive")
					formatting = Formatting.GREEN;
				else if(entityTypeString == "neutral")
					formatting = Formatting.YELLOW;
				else if(entityTypeString == "tameable")
					formatting = Formatting.AQUA;

				tooltip.add( new TranslatableText("entity."+entityID.getNamespace()+"."+entityID.getPath()).formatted(formatting) );
			}
		}
	}
}
