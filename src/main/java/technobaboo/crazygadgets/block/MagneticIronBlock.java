package technobaboo.crazygadgets.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

// Magnetic iron colors are
// #3232f6 and #ff3333

public class MagneticIronBlock extends Block {
	public static final BooleanProperty SCORCHED = BooleanProperty.of("scorched");

	public MagneticIronBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(((BlockState) this.stateManager.getDefaultState()).with(SCORCHED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SCORCHED);
	}
}
