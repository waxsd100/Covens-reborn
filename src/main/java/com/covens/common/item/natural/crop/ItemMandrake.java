package com.covens.common.item.natural.crop;

import com.covens.api.mp.MPContainer;
import com.covens.api.mp.PlayerMPExpander;
import com.covens.common.core.statics.ModCreativeTabs;
import com.covens.common.lib.LibItemName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 03/07/2017, and modified by
 * Sunconure11 on 03/17/2017. It's distributed as part of Covens under the MIT
 * license.
 */
public class ItemMandrake extends ItemCropFood implements PlayerMPExpander {

	public ItemMandrake() {
		super(LibItemName.MANDRAKE, 1, 2F, false);
		this.setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
		this.setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 35, 1));
		MPContainer playerMP = player.getCapability(MPContainer.CAPABILITY, null);
		playerMP.fill(25);
	}

	@Override
	public ResourceLocation getID() {
		return this.getRegistryName();
	}

	@Override
	public int getExtraAmount(EntityPlayer p) {
		return 0;
	}
}
