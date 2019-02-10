package com.covens.common.block.natural.crop;

import java.util.Random;

import com.covens.common.lib.LibBlockName;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class CropWormwood extends BlockCrop {

	public CropWormwood() {
		super(LibBlockName.CROP_WORMWOOD);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return this.canSustainBush(worldIn.getBlockState(pos.down()));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (rand.nextBoolean() || !worldIn.getBiome(pos).canRain()) {
			return;
		}
		if (this.isMaxAge(state) && (state.getValue(AGE) != 7) && this.canSustainBush(worldIn.getBlockState(pos.down())) && worldIn.isAirBlock(pos.up())) {
			if (worldIn.getBlockState(pos.down()).getBlock() == this) {
				worldIn.setBlockState(pos, state.withProperty(AGE, 7), 2);
			} else if ((rand.nextInt(20) == 0) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
				worldIn.setBlockState(pos.up(), this.getDefaultState());
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
			}
		}
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.checkForDrop(worldIn, pos, state);
	}

	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (this.canSustainBush(worldIn.getBlockState(pos.down()))) {
			return true;
		}
		this.dropBlockAsItem(worldIn, pos, state, 0);
		worldIn.setBlockToAir(pos);
		return false;
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return (state.getBlock() == Blocks.FARMLAND) || ((state.getBlock() == this) && this.isMaxAge(state));
	}
}
