package com.covens.common.item.natural.crop;

import com.covens.common.core.statics.ModCreativeTabs;
import com.covens.common.lib.LibItemName;

import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;


public class ItemAconitum extends ItemCropFood {

	public ItemAconitum() {
		super(LibItemName.ACONITUM, 2, 0.6F, false);
		this.addPotion(MobEffects.POISON, MobEffects.NAUSEA);
		this.setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}
}
