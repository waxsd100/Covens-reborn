package com.covens.client.jei;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.covens.api.cauldron.IBrewEffect;
import com.covens.api.cauldron.IBrewModifier;
import com.covens.api.ritual.EnumGlyphType;
import com.covens.client.jei.components.BrewModifierCategory;
import com.covens.client.jei.components.BrewModifierWrapper;
import com.covens.client.jei.components.BrewingCategory;
import com.covens.client.jei.components.BrewingWrapper;
import com.covens.client.jei.components.CauldronCraftingCategory;
import com.covens.client.jei.components.CauldronCraftingWrapper;
import com.covens.client.jei.components.DistilleryCategory;
import com.covens.client.jei.components.DistilleryWrapper;
import com.covens.client.jei.components.OvenCategory;
import com.covens.client.jei.components.OvenWrapper;
import com.covens.client.jei.components.RitualCategory;
import com.covens.client.jei.components.RitualWrapper;
import com.covens.client.jei.components.SpinnerCategory;
import com.covens.client.jei.components.SpinnerWrapper;
import com.covens.common.block.ModBlocks;
import com.covens.common.content.cauldron.BrewData;
import com.covens.common.content.cauldron.CauldronCraftingRecipe;
import com.covens.common.content.cauldron.CauldronRegistry;
import com.covens.common.content.ritual.AdapterIRitual;
import com.covens.common.crafting.DistilleryRecipe;
import com.covens.common.crafting.ModDistilleryRecipes;
import com.covens.common.crafting.OvenSmeltingRecipe;
import com.covens.common.crafting.SpinningThreadRecipe;
import com.covens.common.item.ModItems;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class CovensJEIPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new RitualCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new SpinnerCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new OvenCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new BrewingCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new BrewModifierCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new CauldronCraftingCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new DistilleryCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(AdapterIRitual.class, new RitualWrapperFactory(registry.getJeiHelpers().getGuiHelper()), RitualCategory.UID);
		registry.addRecipes(AdapterIRitual.REGISTRY.getValuesCollection().stream()
				.sorted(Comparator.comparingInt(air -> (air.getInput().size()/3) + (air.getCircles() & 3)))
				.collect(Collectors.toList()), RitualCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModItems.ritual_chalk, 1, EnumGlyphType.GOLDEN.ordinal()), RitualCategory.UID);

		registry.handleRecipes(SpinningThreadRecipe.class, i -> new SpinnerWrapper(i), SpinnerCategory.UID);
		registry.addRecipes(SpinningThreadRecipe.REGISTRY.getValuesCollection(), SpinnerCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.thread_spinner), SpinnerCategory.UID);

		registry.handleRecipes(OvenSmeltingRecipe.class, i -> new OvenWrapper(i), OvenCategory.UID);
		registry.addRecipes(OvenSmeltingRecipe.REGISTRY.getValuesCollection(), OvenCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.oven), OvenCategory.UID);

		registry.handleRecipes(IBrewEffect.class, BrewingWrapper::new, BrewingCategory.UID);
		registry.addRecipes(CauldronRegistry.BREW_POTION_MAP.keySet(), BrewingCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.cauldron), BrewingCategory.UID);

		registry.handleRecipes(IBrewModifier.class, BrewModifierWrapper::new, BrewModifierCategory.UID);
		registry.addRecipes(CauldronRegistry.BREW_MODIFIERS.getValuesCollection(), BrewModifierCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.cauldron), BrewModifierCategory.UID);

		registry.handleRecipes(CauldronCraftingRecipe.class, CauldronCraftingWrapper::new, CauldronCraftingCategory.UID);
		registry.addRecipes(CauldronRegistry.CRAFTING_REGISTRY, CauldronCraftingCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.cauldron), CauldronCraftingCategory.UID);

		registry.handleRecipes(DistilleryRecipe.class, DistilleryWrapper::new, DistilleryCategory.UID);
		registry.addRecipes(ModDistilleryRecipes.REGISTRY.getValuesCollection(), DistilleryCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.distillery), DistilleryCategory.UID);
		
		registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.candle_medium_lit, 1, OreDictionary.WILDCARD_VALUE));
		registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.candle_small_lit, 1, OreDictionary.WILDCARD_VALUE));
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

		ISubtypeInterpreter brewDataInterpreter = new BrewDataInterpreter();

		subtypeRegistry.registerSubtypeInterpreter(ModItems.brew_phial_drink, brewDataInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(ModItems.brew_phial_linger, brewDataInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(ModItems.brew_phial_splash, brewDataInterpreter);
		subtypeRegistry.registerSubtypeInterpreter(ModItems.brew_arrow, brewDataInterpreter);
	}

	private static final class BrewDataInterpreter implements ISubtypeInterpreter {

		@Override
		public String apply(ItemStack itemStack) {
			BrewData data = BrewData.fromStack(itemStack);
			if (data.getEffects().isEmpty()) {
				return NONE;
			}
			return data.getEffects().get(0).getPotion().getRegistryName().toString();
		}
	}

	protected static class RitualWrapperFactory implements IRecipeWrapperFactory<AdapterIRitual> {

		private IGuiHelper igh;

		public RitualWrapperFactory(IGuiHelper igh) {
			this.igh = igh;
		}

		@Override
		public IRecipeWrapper getRecipeWrapper(AdapterIRitual recipe) {
			return new RitualWrapper(recipe, this.igh);
		}
	}
}
