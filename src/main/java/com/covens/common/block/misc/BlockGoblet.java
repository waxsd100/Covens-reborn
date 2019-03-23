package com.covens.common.block.misc;

import javax.annotation.Nullable;

import com.covens.common.block.BlockMod;
import com.covens.common.core.capability.altar.MultProvider;
import com.covens.common.integration.optifine.Optifine;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class BlockGoblet extends BlockMod {

	public static final PropertyBool FULL = PropertyBool.create("filled");

	private static final AxisAlignedBB bounding_box = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 0.375, 0.625);
	private static final MultProvider mult_full = new MultProvider(0.25);
	private static final MultProvider mult_empty = new MultProvider(0.05);

	public BlockGoblet(String id) {
		super(id, new Material(MapColor.IRON));
		this.setDefaultState(this.blockState.getBaseState().withProperty(FULL, false));
		this.setLightOpacity(0);
		this.setHardness(0.3f);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (Optifine.isLoaded()) {
			return bounding_box;
		}
		return bounding_box.offset(state.getOffset(source, pos));
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return state.getValue(FULL);
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (blockState.getValue(FULL)) {
			return 8;
		}
		return 0;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FULL);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FULL, meta == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FULL) ? 1 : 0;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return this.getMetaFromState(state);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}

	@Override
	public EnumOffsetType getOffsetType() {
		if (Optifine.isLoaded()) {
			return EnumOffsetType.NONE;
		}
		return EnumOffsetType.XZ;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, 0));
		items.add(new ItemStack(this, 1, 1));
	}
	
	@Override
	public boolean hasItemCapabilities() {
		return true;
	}
	
	@Override
	public ICapabilityProvider getExtraCaps(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return stack.getMetadata()==1?mult_full:mult_empty;
	}
}
