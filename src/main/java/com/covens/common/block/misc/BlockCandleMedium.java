package com.covens.common.block.misc;

import java.util.Random;

import com.covens.common.block.ModBlocks;
import com.covens.common.integration.optifine.Optifine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


public class BlockCandleMedium extends BlockCandle {

	private static final AxisAlignedBB MEDIUM_BOX = new AxisAlignedBB(0.31, 0, 0.31, 0.69, 0.75, 0.69);

	public BlockCandleMedium(String id, boolean lit) {
		super(id, lit);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (Optifine.isLoaded()) {
			return MEDIUM_BOX;
		}
		return MEDIUM_BOX.offset(state.getOffset(source, pos));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.candle_medium);
	}

	@Override
	public int getType() {
		return 1;
	}
}
