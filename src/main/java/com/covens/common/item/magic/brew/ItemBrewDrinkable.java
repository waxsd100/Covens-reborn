package com.covens.common.item.magic.brew;

import com.covens.common.content.cauldron.BrewData;
import com.covens.common.content.cauldron.BrewData.ApplicationType;
import com.covens.common.lib.LibItemName;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBrewDrinkable extends ItemBrew {

	public ItemBrewDrinkable() {
		super(LibItemName.BREW_PHIAL_DRINK);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		BrewData.fromStack(stack).applyToEntity(entityLiving, null, null, ApplicationType.GENERAL);
		ItemStack res = stack.copy();
		res.shrink(1);
		if ((entityLiving instanceof EntityPlayer) && ((EntityPlayer) entityLiving).isCreative()) {
			res = stack;
		}
		return res;
	}

}
