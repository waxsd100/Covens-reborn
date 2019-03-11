

package com.covens.common.item.block;

import java.util.List;

import com.covens.api.mp.MPContainer;
import com.covens.common.block.ModBlocks;
import com.covens.common.block.misc.BlockWitchFire;
import com.covens.common.block.misc.BlockWitchFire.EnumFireType;
import com.covens.common.item.ItemBlockMod;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockRevealingLantern extends ItemBlockMod {

	private boolean lit;

	public ItemBlockRevealingLantern(Block block, boolean lit) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.lit = lit;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if ((stack.getMetadata() < 16) && (stack.getMetadata() >= 0)) {
			return super.getTranslationKey(stack) + "." + EnumDyeColor.values()[stack.getMetadata()].name().toLowerCase();
		}
		return super.getTranslationKey(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (!this.lit) {
			tooltip.add(TextFormatting.GRAY + TextFormatting.ITALIC.toString() + I18n.format("tile.lantern.desc"));
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return this.lit;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!this.lit && (worldIn.getBlockState(pos).getBlock() == ModBlocks.witchfire) && (worldIn.getBlockState(pos).getValue(BlockWitchFire.TYPE) == EnumFireType.SIGHTFIRE) && player.getHeldItem(hand).getItem().equals(Item.getItemFromBlock(ModBlocks.lantern))) {
			player.setHeldItem(hand, new ItemStack(ModBlocks.revealing_lantern, 1, player.getHeldItem(hand).getMetadata()));
			worldIn.setBlockToAir(pos);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (player.isSneaking()) {
			return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
		}

		if (this.lit && world.getBlockState(pos.offset(side)).getBlock().isReplaceable(world, pos.offset(side)) && player.getCapability(MPContainer.CAPABILITY, null).drain(50)) {
			world.setBlockState(pos.offset(side), ModBlocks.witches_light.getDefaultState(), 3);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

}
