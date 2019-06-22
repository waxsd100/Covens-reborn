package com.covens.common.content.cauldron;

import static com.covens.common.lib.LibIngredients.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.covens.api.CovensAPI;
import com.covens.api.cauldron.IBrewEffect;
import com.covens.api.cauldron.IBrewModifier;
import com.covens.api.cauldron.IBrewModifier.ModifierResult;
import com.covens.api.cauldron.IBrewModifier.ResultType;
import com.covens.api.cauldron.IBrewModifierList;
import com.covens.api.cauldron.ICauldronCraftingRecipe;
import com.covens.api.cauldron.ICauldronRecipeBuilder;
import com.covens.common.block.ModBlocks;
import com.covens.common.core.statics.ModFluids;
import com.covens.common.crafting.CauldronRecipe;
import com.covens.common.item.ModItems;
import com.covens.common.lib.LibMod;
import com.covens.common.potion.ModPotions;
import com.google.common.collect.HashBiMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import zabi.minecraft.minerva.common.crafting.IngredientMultiOreDict;

public class CauldronRegistry {

	public static final HashBiMap<IBrewEffect, Potion> BREW_POTION_MAP = HashBiMap.<IBrewEffect, Potion>create(90);
	public static final IForgeRegistry<IBrewModifier> BREW_MODIFIERS = new RegistryBuilder<IBrewModifier>().setName(new ResourceLocation(LibMod.MOD_ID, "brew modifiers")).setIDRange(0, 200).setType(IBrewModifier.class).create();
	public static final ArrayList<ICauldronCraftingRecipe> CRAFTING_REGISTRY = new ArrayList<>();
	private static final HashMap<Ingredient, IBrewEffect> BREW_INGREDIENT_REGISTRY = new HashMap<>();


	public static void bindPotionAndEffect(IBrewEffect effect, Potion potion) {
		BREW_POTION_MAP.put(effect, potion);
	}

	public static void registerBrewModifier(IBrewModifier modifier) {
		BREW_MODIFIERS.register(modifier);
	}

	public static void registerBrewIngredient(IBrewEffect effect, Ingredient ingredient) {
		BREW_INGREDIENT_REGISTRY.put(ingredient, effect);
	}

	public static Potion getPotionFromBrew(IBrewEffect effect) {
		return BREW_POTION_MAP.get(effect);
	}

	public static IBrewEffect getBrewFromPotion(Potion potion) {
		if (potion == null) {
			throw new IllegalArgumentException("The potion parameter cannot be null");
		}
		IBrewEffect effect = BREW_POTION_MAP.inverse().get(potion);
		if (effect == null) {
			throw new NoSuchElementException(potion.getName() + " has no associated IBrewEffect");
		}
		return effect;
	}

	public static Optional<IBrewModifierList> getModifierListFromStack(ItemStack stack, IBrewModifierList currentList, IBrewEffect currentEffect) {
		for (IBrewModifier bm : BREW_MODIFIERS) {
			if (bm.canApply(currentEffect)) {
				ModifierResult mr = bm.acceptIngredient(currentEffect, stack, currentList);
				if (mr.getResult() == ResultType.SUCCESS) {
					BrewModifierListImpl newList = new BrewModifierListImpl(currentList);
					newList.addModifier(bm, mr.getLevel());
					return Optional.of(newList);
				} else if (mr.getResult() == ResultType.FAIL) {
					return null;
				}
			}
		}
		return Optional.empty();
	}

	public static Optional<IBrewEffect> getBrewFromStack(ItemStack stack) {
		for (Ingredient i : BREW_INGREDIENT_REGISTRY.keySet()) {
			if (i.apply(stack)) {
				return Optional.ofNullable(BREW_INGREDIENT_REGISTRY.get(i));
			}
		}
		return Optional.empty();
	}

	public static Optional<Ingredient> getIngredientFromBrew(IBrewEffect effect) {
		return BREW_INGREDIENT_REGISTRY.keySet().parallelStream().filter(ing -> BREW_INGREDIENT_REGISTRY.get(ing) == effect).findFirst();
	}

	public static void init() {

		newRecipe("cleanPiston")
			.addInput(stickyPiston, 1)
			.setOutput(new ItemStack(Blocks.PISTON))
			.setFluidConsumed(Fluid.BUCKET_VOLUME/4)
			.buildAndRegister();
		
		newRecipe("wetSponge")
			.addInput(sponge, 1)
			.setOutput(new ItemStack(Blocks.SPONGE, 1, 1))
			.buildAndRegister();
		
		newRecipe("combToHoney")
			.addInput(honeycomb, 2)
			.setOutputFluid(ModFluids.HONEY)
			.setOutput(new ItemStack(ModItems.empty_honeycomb, 2))
			.setFluidConsumed(0)
			.setRequiredPower(2)
			.buildAndRegister();
		
		newRecipe("oil")
			.addInput(potato, 2)
			.addInput(sunflower, 1)
			.setOutputFluid(ModFluids.MUNDANE_OIL)
			.setFluidConsumed(0)
			.buildAndRegister();
		
		for (int i = 0; i < 16; i++) {
			newRecipe("clearBanner"+i)
				.addInput(Ingredient.fromStacks(new ItemStack(Items.BANNER, 1, i)), 1)
				.setOutput(new ItemStack(Items.BANNER, 1, i))
				.setFluidConsumed(250)
				.buildAndRegister();
		}
		
		newRecipe("wax")
			.addInput(empty_honeycomb, 1)
			.setRequiredPower(1)
			.setOutput(new ItemStack(ModItems.wax))
			.buildAndRegister();
		
		newRecipe("fillGoblet")
			.addInput(emptyGoblet, 1)
			.addInput(redstone, 5)
			.addInput(ghastTear, 1)
			.addInput(fumeCloudyOil, 1)
			.setOutput(new ItemStack(ModBlocks.goblet, 1, 1))
			.setRequiredPower(60)
			.buildAndRegister();
		
		newRecipe("fieryChalk")
			.addInput(normalRitualChalk, 1)
			.addInput(blazePowder, 4)
			.addInput(fumeFieryBreeze, 2)
			.setOutput(new ItemStack(ModItems.ritual_chalk_nether))
			.setFluidInput(ModFluids.MUNDANE_OIL)
			.setRequiredPower(30)
			.buildAndRegister();
		
		newRecipe("enderChalk")
			.setOutput(new ItemStack(ModItems.ritual_chalk_ender))
			.addInput(normalRitualChalk, 1)
			.addInput(dimensionalSand, 4)
			.addInput(fumeHeavenlyWind, 2)
			.setRequiredPower(30)
			.buildAndRegister();
		
		newRecipe("goldenChalk")
			.setFluidInput(ModFluids.HONEY)
			.setOutput(new ItemStack(ModItems.ritual_chalk_golden))
			.addInput(normalRitualChalk, 1)
			.addInput(goldNugget, 4)
			.addInput(fumeCleansingAura, 2)
			.setRequiredPower(30)
			.buildAndRegister();

		newRecipe("graveyardDirt")
			.setOutput(new ItemStack(ModBlocks.graveyard_dirt, 8))
			.addInput(wormwood, 2)
			.addInput(dirt, 8)
			.addInput(graveyardDust, 2)
			.setRequiredPower(40)
			.buildAndRegister();
		
		newRecipe("emberGrass")
			.addInput(blazePowder, 2)
			.addInput(wormwood, 2)
			.setOutput(new ItemStack(ModBlocks.ember_grass, 2))
			.setFluidInput(ModFluids.MUNDANE_OIL)
			.setRequiredPower(20)
			.buildAndRegister();
		
		newRecipe("torchwood")
			.addInput(glowstoneDust, 2)
			.addInput(anyLog, 1)
			.addInput(anyLeaf, 1)
			.setOutput(new ItemStack(ModBlocks.torchwood, 2))
			.setFluidInput(ModFluids.MUNDANE_OIL)
			.setRequiredPower(20)
			.buildAndRegister();
		
		newRecipe("spanishMoss")
			.addInput(vine, 3)
			.addInput(fumeBottledMagic, 1)
			.addInput(netherWart, 1)
			.setOutput(new ItemStack(ModBlocks.spanish_moss, 3))
			.setRequiredPower(20)
			.buildAndRegister();
			
		newRecipe("chalk")
			.addInput(fumeBirchSoul, 1)
			.addInput(clayBall, 1)
			.addInput(sand, 2)
			.setOutput(new ItemStack(ModItems.ritual_chalk_normal))
			.setRequiredPower(30)
			.buildAndRegister();
		
		registerVanillaBrewEffect(MobEffects.ABSORPTION, 20, goldenApple, 600);
		registerVanillaBrewEffect(MobEffects.FIRE_RESISTANCE, 20, magmaCream);
		registerVanillaBrewEffect(MobEffects.HUNGER, 30, rottenFlesh, 600);
		registerVanillaBrewEffect(MobEffects.INSTANT_DAMAGE, 100, fermentedSpiderEye);
		registerVanillaBrewEffect(MobEffects.INSTANT_HEALTH, 70, speckledMelon);
		registerVanillaBrewEffect(MobEffects.INVISIBILITY, 30, anyGlass);
		registerVanillaBrewEffect(MobEffects.JUMP_BOOST, 20, equineTail);
		registerVanillaBrewEffect(MobEffects.NIGHT_VISION, 20, goldenCarrot);
		registerVanillaBrewEffect(MobEffects.POISON, 70, spiderEye);
		registerVanillaBrewEffect(MobEffects.REGENERATION, 70, ghastTear);
		registerVanillaBrewEffect(MobEffects.SPEED, 20, sugar);
		registerVanillaBrewEffect(MobEffects.SLOWNESS, 40, soulSand);
		registerVanillaBrewEffect(MobEffects.WATER_BREATHING, 10, a3);
		registerVanillaBrewEffect(MobEffects.STRENGTH, 40, blazePowder);
		registerVanillaBrewEffect(MobEffects.WEAKNESS, 60, rabbitHide);
		registerVanillaBrewEffect(MobEffects.BLINDNESS, 50, belladonna, 200);
		registerVanillaBrewEffect(MobEffects.HASTE, 10, lavender, 1200);
		registerVanillaBrewEffect(MobEffects.RESISTANCE, 30, heart, 100);
		registerVanillaBrewEffect(MobEffects.LEVITATION, 80, shulker_shell, 100);

		registerCombinedBrewEffect(ModPotions.wolfsbane, aconitum);
		registerCombinedBrewEffect(ModPotions.arrow_deflect, fumeEverchangingPresence);
		registerCombinedBrewEffect(ModPotions.absence, coldIronDustSmall);
		registerCombinedBrewEffect(ModPotions.plant, redMushroom);
		registerCombinedBrewEffect(ModPotions.bane_arthropods, wormwood);
		registerCombinedBrewEffect(ModPotions.corruption, bone);
		registerCombinedBrewEffect(ModPotions.cursed_leaping, chorusFruit);
		registerCombinedBrewEffect(ModPotions.demons_bane, hellebore);
		registerCombinedBrewEffect(ModPotions.projectile_resistance, silverScales);
		registerCombinedBrewEffect(ModPotions.disrobing, wax);
		registerCombinedBrewEffect(ModPotions.ender_inhibition, dimensionalSand);
		registerCombinedBrewEffect(ModPotions.extinguish_fires, mintSeeds);
		registerCombinedBrewEffect(ModPotions.fertilize, thistle); // TODO make it bonemeal, but it will collide with the color modifier
		registerCombinedBrewEffect(ModPotions.fireworld, ginger);
		registerCombinedBrewEffect(ModPotions.grace, woolOfBat);
		registerCombinedBrewEffect(ModPotions.mending, goldenApple); //TODO change ingredient, absorption
		registerCombinedBrewEffect(ModPotions.flower_growth, Ingredient.fromItem(Item.getItemFromBlock(Blocks.RED_FLOWER)));
		registerCombinedBrewEffect(ModPotions.harvest, apple);
		registerCombinedBrewEffect(ModPotions.smite, Ingredient.fromItem(ModItems.white_sage));
		registerCombinedBrewEffect(ModPotions.ice_world, Ingredient.fromItem(ModItems.mint));
		registerCombinedBrewEffect(ModPotions.outcasts_shame, fumeReekOfDeath);
		registerCombinedBrewEffect(ModPotions.infestation, Ingredient.fromItem(Item.getItemFromBlock(Blocks.MYCELIUM)));
		registerCombinedBrewEffect(ModPotions.ozymandias, Ingredient.fromItem(Item.getItemFromBlock(Blocks.SANDSTONE)));
		registerCombinedBrewEffect(ModPotions.purification, Ingredient.fromItem(ModItems.tulsi));
		registerCombinedBrewEffect(ModPotions.path_of_the_deep, new IngredientMultiOreDict("kelp", "seaWeed", "cropSeaweed", "cropKelp"));
		registerCombinedBrewEffect(ModPotions.prune_leaves, Ingredient.fromItem(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)));
		registerCombinedBrewEffect(ModPotions.rotting, Ingredient.fromItem(ModItems.tongue_of_dog));
		registerCombinedBrewEffect(ModPotions.setehs_wastes, Ingredient.fromStacks(new ItemStack(Blocks.SAND, 1, 1)));
		registerCombinedBrewEffect(ModPotions.salted_earth, salt);
		registerCombinedBrewEffect(ModPotions.shell_armor, Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.purifying_earth)));
		registerCombinedBrewEffect(ModPotions.till_land, Ingredient.fromItem(Item.getItemFromBlock(Blocks.DIRT)));
		registerCombinedBrewEffect(ModPotions.snow_trail, Ingredient.fromItem(Item.getItemFromBlock(Blocks.PACKED_ICE)));
		registerCombinedBrewEffect(ModPotions.spider_nightmare, spider_web);
		registerCombinedBrewEffect(ModPotions.volatility, Ingredient.fromItem(Items.GUNPOWDER));
		registerCombinedBrewEffect(ModPotions.pulverize, Ingredient.fromItem(Item.getItemFromBlock(Blocks.COBBLESTONE)));
		registerCombinedBrewEffect(ModPotions.love, moonbell);
		registerCombinedBrewEffect(ModPotions.revealing, eyes);
		registerCombinedBrewEffect(ModPotions.deaths_ebb, Ingredient.fromItem(ModItems.asphodel));
		registerCombinedBrewEffect(ModPotions.power_boon, Ingredient.fromItem(ModItems.mandrake_root));
		registerCombinedBrewEffect(ModPotions.power_drain, Ingredient.fromItem(ModItems.adders_fork));
		registerCombinedBrewEffect(ModPotions.power_dampening, Ingredient.fromItem(ModItems.graveyard_dust));
		registerCombinedBrewEffect(ModPotions.power_boost, Ingredient.fromItem(ModItems.juniper_berries));
		registerCombinedBrewEffect(ModPotions.smite, garlic);
		registerCombinedBrewEffect(ModPotions.mowing, anyLeaf);
		registerCombinedBrewEffect(ModPotions.rooting, anyLog);
	}

	public static void postInit() {
		CovensAPI.getAPI().registerBrewEffect(ModPotions.freezing, ModPotions.freezing.getPotion(), snowball);
		CovensAPI.getAPI().registerBrewEffect(ModPotions.sinking, ModPotions.sinking.getPotion(), Ingredient.fromItem(Items.IRON_NUGGET));
	}

	private static void registerVanillaBrewEffect(Potion potion, int cost, Ingredient ingredient) {
		CovensAPI.getAPI().registerBrewEffect(new BrewVanilla(potion, cost), potion, ingredient);
	}

	private static void registerVanillaBrewEffect(Potion potion, int cost, Ingredient ingredient, int duration) {
		CovensAPI.getAPI().registerBrewEffect(new BrewVanilla(duration, cost), potion, ingredient);
	}
	
	private static ICauldronRecipeBuilder newRecipe(String name) {
		return CovensAPI.getAPI().getNewCauldronRecipeBuilder(new ResourceLocation(LibMod.MOD_ID, name));
	}

	private static void registerCombinedBrewEffect(Potion potion, Ingredient ingredient) {
		if (potion == null) {
			throw new IllegalArgumentException("Null potion cannot be registered");
		}
		if (ingredient.getMatchingStacks().length == 0) {
			throw new IllegalArgumentException("Ingredient for " + potion.getRegistryName() + " is empty");
		}
		if (potion instanceof IBrewEffect) {
			CovensAPI.getAPI().registerBrewEffect((IBrewEffect) potion, potion, ingredient);
			return;
		}
		throw new IllegalArgumentException(potion + " is not an IBrewEffect. Use CovensAPI#registerBrewEffect to register them as separate objects");
	}

	public static Optional<ICauldronCraftingRecipe> getCraftingResult(FluidStack fluidStack, NonNullList<ItemStack> inputs) {
		for (ICauldronCraftingRecipe cr: CRAFTING_REGISTRY) {
			if (CauldronRecipe.matches(cr, inputs, fluidStack)) {
				return Optional.of(cr);
			}
		}
		return Optional.empty();
	}
}
