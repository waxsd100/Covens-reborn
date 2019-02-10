package com.covens.common.item.natural.crop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.covens.common.core.helper.NBTHelper;
import com.covens.common.item.food.ItemModFood;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SuppressWarnings("WeakerAccess")
public class ItemCropFood extends ItemModFood {

	private static final String DRY = "dry";
	private List<Potion> potions;

	public ItemCropFood(String id, int amount, float saturation, boolean isWolfFood) {
		super(id, amount, saturation, isWolfFood);
	}

	protected void addPotion(Potion... potionsIn) {
		this.potions = new ArrayList<>();
		Collections.addAll(this.potions, potionsIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.ITALIC + I18n.format("witch.tooltip." + this.getNameInefficiently(stack) + "_description.name"));
	}

	public String getNameInefficiently(ItemStack stack) {
		return this.getTranslationKey().substring(5);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		if (!worldIn.isRemote && (this.potions != null)) {
			final int modifier = this.isDry(stack) ? 160 : 80;
			for (Potion effect : this.potions) {
				player.addPotionEffect(new PotionEffect(effect, modifier, modifier / 80));
			}
		}

	}

	public boolean isDry(ItemStack stack) {
		return NBTHelper.getBoolean(stack, DRY);
	}

	public void setDry(ItemStack stack, boolean isDry) {
		NBTHelper.setBoolean(stack, DRY, isDry);
	}
}
