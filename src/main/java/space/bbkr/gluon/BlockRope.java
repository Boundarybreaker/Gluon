package space.bbkr.gluon;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockRope extends Block {

	public static final BooleanProperty ANCHOR = BooleanProperty.create("anchor");
	public static final BooleanProperty EXTENDED = BlockStateProperties.EXTENDED;

	public BlockRope(Properties props) {
		super(props);
		this.setDefaultState(this.getStateContainer().getBaseState().with(ANCHOR, false).with(EXTENDED, false));
	}

	@Override
	public VoxelShape getShape(IBlockState p_getShape_1_, IBlockReader p_getShape_2_, BlockPos p_getShape_3_) {
		return Block.makeCuboidShape(6.0,0.0,6.0,10.0,16.0,10.0);
	}

	@Override
	public VoxelShape getCollisionShape(IBlockState p_getCollisionShape_1_, IBlockReader p_getCollisionShape_2_, BlockPos p_getCollisionShape_3_) {
		return Block.makeCuboidShape(6.05, 0.0, 6.05, 9.95, 16.0, 9.95);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public void onEntityCollision(IBlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityItem) return;
		if (entity.collidedHorizontally) {
			entity.motionY = 0.35;
		} else if (entity.isSneaking()) {
			entity.motionY = 0.08; //Stop, but also counteract EntityLivingBase-applied microgravity
		} else if (entity.motionY<-0.20) {
			entity.motionY = -0.20;
		}

	}

	protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(ANCHOR);
		builder.add(EXTENDED);
	}

	@Override
	public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState newState, IWorld world, BlockPos pos, BlockPos posFrom) {
		if (!state.isValidPosition(world, pos) || !state.get(EXTENDED)) {
			world.getPendingBlockTicks().scheduleTick(pos, this, 2);
			return super.updatePostPlacement(state, facing, newState, world, pos, posFrom);
		}

		return super.updatePostPlacement(state, facing, newState, world, pos, posFrom);
	}

	public void tick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (!state.isValidPosition(world, pos)) {
			world.destroyBlock(pos, true);
		}
		if (world.getBlockState(pos.offset(EnumFacing.DOWN)).isAir() && !state.get(EXTENDED)) {
			world.setBlockState(pos.offset(EnumFacing.DOWN), this.getDefaultState());
			world.setBlockState(pos, state.with(EXTENDED, true));
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
		world.getPendingBlockTicks().scheduleTick(pos, this, 2);
		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	public boolean isValidPosition(IBlockState state, IWorldReaderBase world, BlockPos pos) {
		BlockPos checkPos = pos.offset(EnumFacing.UP);
		IBlockState checkBlock = world.getBlockState(checkPos);
		return !checkBlock.isAir() && (checkBlock.isFullCube() || checkBlock.getBlock() == Gluon.ROPE);
	}

	@Nullable
	public IBlockState getStateForPlacement(BlockItemUseContext ctx) {
		return this.getDefaultState().with(ANCHOR, true);
	}

	@Override
	public boolean isTopSolid(IBlockState p_isTopSolid_1_) {
		return false;
	}

	@Override
	public ItemStack getItem(IBlockReader reader, BlockPos pos, IBlockState state) {
		if (state.get(ANCHOR)) {
			return new ItemStack(Gluon.ROPE, 1);
		} else return ItemStack.EMPTY;
	}

	public IItemProvider getItemDropped(IBlockState state, World world, BlockPos pos, int i) {
		if (state.get(ANCHOR)) {
			return Gluon.ROPE;
		} else return Items.AIR;
	}

	@Override
	public SoundType getSoundType() {
		return SoundType.LADDER;
	}
}
