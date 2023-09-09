package technobaboo.crazygadgets.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import technobaboo.crazygadgets.block.CrazyGadgetsBlocks;
import technobaboo.crazygadgets.block.MagneticIronBlock;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin {
	@Inject(at = @At("TAIL"), method = "setPowered")
	public void setPowered(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
		if (state.get(FacingBlock.FACING) == Direction.UP) {
			for (BlockPos checkBlock = pos.add(0, -1, 0); world.getBlockState(checkBlock)
					.isOf(CrazyGadgetsBlocks.COPPER_WRAPPED_IRON_BLOCK); checkBlock = checkBlock.add(0, -1, 0)) {
				world.syncWorldEvent(null, WorldEvents.BLOCK_BROKEN, checkBlock,
						Block.getRawIdFromState(Blocks.COPPER_BLOCK.getDefaultState()));
				world.setBlockState(checkBlock, CrazyGadgetsBlocks.MAGNETIC_IRON_BLOCK.getDefaultState()
						.with(MagneticIronBlock.SCORCHED, true));
			}
		}
	}
}
