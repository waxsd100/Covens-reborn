

package com.covens.common.block.natural.tree;

import com.covens.common.core.statics.ModCreativeTabs;
import com.covens.common.lib.LibMod;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zabi.minecraft.minerva.client.blockmodels.IModelRegister;
import zabi.minecraft.minerva.client.blockmodels.ModelHandler;

public class BlockModLog extends BlockLog implements IModelRegister {

	public BlockModLog(String id) {
		super();
		this.setTranslationKey(id);
		this.setRegistryName(LibMod.MOD_ID, id);
		this.setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LOG_AXIS).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumAxis axis = EnumAxis.values()[meta];
		return this.getDefaultState().withProperty(LOG_AXIS, axis);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty<?>[] {
				LOG_AXIS
		});
	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
