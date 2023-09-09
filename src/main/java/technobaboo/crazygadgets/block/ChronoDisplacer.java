package technobaboo.crazygadgets.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ChronoDisplacer extends FacingBlock implements Waterloggable {
	public static final BooleanProperty WATERLOGGED;

	public ChronoDisplacer(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(((BlockState) this.stateManager.getDefaultState()).with(Properties.FACING, Direction.UP)
				.with(Properties.WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.FACING, Properties.WATERLOGGED);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		return (BlockState) this.getDefaultState().with(FACING, ctx.getSide()).with(WATERLOGGED,
				fluidState.getFluid() == Fluids.WATER);
	}

	// @Override
	// public BlockState getStateForNeighborUpdate(BlockState state, Direction
	// direction, BlockState neighborState,
	// WorldAccess world, BlockPos pos, BlockPos neighborPos) {
	// if ((Boolean) state.get(WATERLOGGED)) {
	// // world.getFluidTickScheduler().schedule(pos, Fluids.WATER,
	// // Fluids.WATER.getTickRate(world));
	// }

	// return super.getstate
	// return super.getStateForNeighborUpdate(state, direction, neighborState,
	// world, pos, neighborPos);
	// }

	// @Override
	// public FluidState getFluidState(BlockState state) {
	// return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) :
	// super.g(state);
	// }

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(Properties.FACING);
		switch (dir) {
			case NORTH:
			case SOUTH:
				return VoxelShapes.cuboid(0.125f, 0.125f, 0f, 0.875f, 0.875f, 1.0f);
			case EAST:
			case WEST:
				return VoxelShapes.cuboid(0f, 0.125f, 0.125f, 1.0f, 0.875f, 0.875f);
			case UP:
			case DOWN:
				return VoxelShapes.cuboid(0.125f, 0f, 0.125f, 0.875f, 1.0f, 0.875f);
			default:
				return VoxelShapes.fullCube();
		}
	}

	static {
		WATERLOGGED = Properties.WATERLOGGED;
	}
}
