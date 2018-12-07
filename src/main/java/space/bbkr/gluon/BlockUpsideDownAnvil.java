package space.bbkr.gluon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockUpsideDownAnvil extends BlockAnvil {

//	public static final IntegerProperty DAMAGE = IntegerProperty.create("damage", 0, 63);
	// the damage stuff is being super weird and I don't wanna deal with it so I'm just gonna comment it all out until I'm done making it data-driven

	public BlockUpsideDownAnvil(Properties props) {
		super(props);
		setDefaultState(getStateContainer().getBaseState().with(FACING, EnumFacing.NORTH)/*.with(DAMAGE, 0)*/);
	}

	@Override
	public void onEndFalling(World world, BlockPos pos, IBlockState state, IBlockState landState) {
		world.playEvent(1031, pos, 0);
		BlockPos landedOnPos = pos.offset(EnumFacing.DOWN);
		Block landedBlock = world.getBlockState(landedOnPos).getBlock();
		IInventory inv = new DummyAnvilInventory(landedBlock);
		IRecipe recipe = world.getRecipeManager().getRecipe(inv, world);
		if (recipe != null && recipe.matches(inv, world)) {
			if (recipe.getRecipeOutput().getItem() instanceof ItemBlock && recipe.getRecipeOutput().getCount() == 1) {
				Block blockResult = ((ItemBlock) recipe.getRecipeOutput().getItem()).getBlock();
				world.setBlockState(landedOnPos, blockResult.getDefaultState());
			} else {
				EntityItem item = new EntityItem(world);
				item.setItem(recipe.getRecipeOutput());
				world.spawnEntity(item);
				world.setBlockState(landedOnPos, Blocks.AIR.getDefaultState());
			}
		}
//		IBlockState damageBlock = damage(state);
//		System.out.println("onEndFalling damaged state: "+damageBlock);
//		if (damageBlock.equals(Blocks.AIR.getDefaultState()) && shouldBreak) {
//			this.onBroken(world, pos);
//		}
	}

//	public static IBlockState damage(IBlockState state) {
//		int damage = state.get(DAMAGE);
//		System.out.println("damage on current state: "+damage);
//		if (damage < 63) {
//			IBlockState retState = Gluon.UPSIDE_DOWN_ANVIL.getDefaultState().with(FACING, state.get(FACING)).with(DAMAGE, damage + 1);
//			System.out.println("damage state returning: "+retState);
//			return retState;
//		} else {
//			return Blocks.AIR.getDefaultState();
//		}
//	}

//	@Override
//	public ItemStack getItem(IBlockReader reader, BlockPos pos, IBlockState state) {
//		ItemStack dropStack = new ItemStack(Gluon.UPSIDE_DOWN_ANVIL, 1);
//		NBTTagCompound tag = new NBTTagCompound();
//		tag.putInt("damage", state.get(DAMAGE));
//		dropStack.setTag(tag);
//		System.out.println("getItem on current state: "+state.get(DAMAGE));
//		System.out.println("getItem tag adding: "+tag);
//		return dropStack;
//	}

//	@Override
//	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
//		spawnAsEntity(world, pos, this.getItem(world, pos, state));
////		super.harvestBlock(world, player, pos, state, te, stack);
//	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(FACING/*, DAMAGE*/);
	}

	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext ctx) {
		IBlockState state = this.getDefaultState().with(FACING, ctx.getPlacementHorizontalFacing().rotateY());
//		if (ctx.getItem().hasTag()) {
//			state.with(DAMAGE, ctx.getItem().getTag().getInt("damage"));
//			System.out.println("getStateForPlacement context tag: "+ctx.getItem().getTag());
//		}
		return state;
	}
}
