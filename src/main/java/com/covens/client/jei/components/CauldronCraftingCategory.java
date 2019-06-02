package com.covens.client.jei.components;

import java.util.List;

import com.covens.common.lib.LibMod;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CauldronCraftingCategory implements IRecipeCategory<CauldronCraftingWrapper> {

	public static final String UID = LibMod.MOD_ID + ":cauldron_crafting";
	public IDrawable bg;
	private static ResourceLocation rl = new ResourceLocation(LibMod.MOD_ID, "textures/gui/jei_cauldron_crafting.png");

	public CauldronCraftingCategory(IGuiHelper igh) {
		this.bg = igh.drawableBuilder(rl, 0, 0, 120, 88).setTextureSize(120, 88).build();
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.category.crafting");
	}

	@Override
	public String getModName() {
		return LibMod.MOD_NAME;
	}

	@Override
	public IDrawable getBackground() {
		return this.bg;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CauldronCraftingWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup is = recipeLayout.getItemStacks();
		recipeLayout.setShapeless();
		List<List<ItemStack>> isList = recipeWrapper.getOriginal().getJEIItemStacksInput();
		int lastAdded = 0;
		for (int i = 0; i < isList.size(); i++) {
			is.init(i + 3, true, (18 * i) + ((120 - (18 * isList.size())) / 2), 5);
			is.set(i + 3, isList.get(i));
			lastAdded++;
		}
		IGuiFluidStackGroup fs = recipeLayout.getFluidStacks();
		fs.init(0, true, 52, 30);
		fs.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0).get(0));

		for (List<ItemStack> isl:recipeWrapper.getOriginal().getOutputList()) {
			lastAdded++;
			is.init(lastAdded, false, 72, 67);
			is.set(lastAdded, isl);
		}

		fs.init(2, false, 31, 65);
		fs.set(2, recipeWrapper.getOriginal().getJEIFluidCache());
	}

}
