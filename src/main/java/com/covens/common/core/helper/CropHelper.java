package com.covens.common.core.helper;

import java.util.LinkedHashMap;
import java.util.Map;

import com.covens.common.block.natural.crop.BlockCrop;
import com.covens.common.core.statics.ModCrops;
import com.covens.common.item.ModItems;
import com.covens.common.item.natural.crop.ItemCropFood;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;


public final class CropHelper {

	private static final Map<ModCrops, Item> seeds = new LinkedHashMap<>();
	private static final Map<ModCrops, Block> crops = new LinkedHashMap<>();
	private static final Map<ModCrops, Item> foods = new LinkedHashMap<>();

	private CropHelper() {
	}

	/**
	 * Register a Crop to the {@link CropHelper}.
	 * <p>
	 * The Item Seed needs to be different, for ex the Kelp seed, that needs to be
	 * placed on water so it uses a different placement recipeDropLogic.
	 * </p>
	 *
	 * @param crop     The Crop enum
	 * @param cropItem The item this Crop will drop when harvested
	 * @param seedItem The seed that will place the Crop
	 */
	public static <T extends BlockCrop> void registerCrop(ModCrops crop, T placed, Item cropItem, Item seedItem) {
		placed.setCrop(cropItem);
		placed.setSeed(seedItem);
		if ((cropItem instanceof ItemCropFood) && (crop.getMPExpansionOnEaten() > 0)) {
			((ItemCropFood) cropItem).setMPExpansionValue(crop.getMPExpansionOnEaten());
		}
		CropHelper.getSeeds().put(crop, seedItem);
		CropHelper.getCrops().put(crop, placed);
		CropHelper.getFoods().put(crop, cropItem);
	}

	public static Map<ModCrops, Item> getSeeds() {
		return seeds;
	}

	public static Map<ModCrops, Block> getCrops() {
		return crops;
	}

	public static Map<ModCrops, Item> getFoods() {
		return foods;
	}

	public static void initSeedDrops() {
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_belladonna), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_thistle), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_wormwood), 8);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_asphodel), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_aconitum), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_mint), 6);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_lavender), 4);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_mandrake), 4);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_tulsi), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_hellebore), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModItems.seed_ginger), 3);
	}
}
