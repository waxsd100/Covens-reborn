package com.covens.common.block.natural.crop;

import java.util.Random;

import com.covens.common.lib.LibBlockName;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class CropMint extends BlockCrop {

	public CropMint() {
		super(LibBlockName.CROP_MINT, 6);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if (this.isMaxAge(state) && (state.getValue(AGE) != 7)) {
			if (rand.nextBoolean()) {
				worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, 7));
			} else {
				this.trySpread(worldIn, pos, rand);
			}
		}
	}

	private void trySpread(World world, BlockPos center, Random rand) {
		BlockPos I = center.add(-1, -1, -1);
		BlockPos F = center.add(1, 1, 1);
		BlockPos.getAllInBox(I, F).forEach(pos -> {
			if (rand.nextBoolean() && this.canSustainBush(world.getBlockState(pos.down())) && (world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos))) {
				world.setBlockState(pos, this.getDefaultState(), 2);
			}
		});
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double x = rand.nextFloat();
		double y = rand.nextFloat();
		double z = rand.nextFloat();
		worldIn.spawnParticle(EnumParticleTypes.END_ROD, x, y, z, 0, 0.05, 0);
	}
}
