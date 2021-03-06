package com.covens.common.integration.thaumcraft;

import com.covens.common.block.ModBlocks;
import com.covens.common.item.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

public class ThaumcraftInternal {

	@Deprecated // DONT USE THIS DIRECTLY
	public static void init() {
		MinecraftForge.EVENT_BUS.register(new ThaumcraftInternal());
	}

	@SubscribeEvent
	public void aspectRegistrationEvent(AspectRegistryEvent evt) {

		// Items
		evt.register.registerObjectTag(new ItemStack(ModItems.tarots), new AspectList().add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 10));
		evt.register.registerObjectTag(new ItemStack(ModItems.heart), new AspectList().add(Aspect.DEATH, 7).add(Aspect.MAN, 7));
		evt.register.registerObjectTag(new ItemStack(ModItems.wood_ash), new AspectList().add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.moonbell), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.aconitum), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.belladonna), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2).add(Aspect.MAGIC, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.chrysanthemum), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.garlic), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.AVERSION, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.hellebore), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.FIRE, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.mint), new AspectList().add(Aspect.PLANT, 2).add(Aspect.COLD, 2).add(Aspect.SENSES, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.mandrake_root), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.EARTH, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.sagebrush), new AspectList().add(Aspect.PLANT, 2).add(Aspect.VOID, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.thistle), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AVERSION, 2).add(Aspect.PROTECT, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.tulsi), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.white_sage), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2).add(Aspect.SOUL, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.wormwood), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SOUL, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.tongue_of_dog), new AspectList().add(Aspect.SENSES, 4).add(Aspect.BEAST, 4).add(Aspect.DEATH, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.needle_bone), new AspectList().add(Aspect.DEATH, 2).add(Aspect.CRAFT, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.salt), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.honey), new AspectList().add(Aspect.DESIRE, 6).add(Aspect.LIFE, 6));
		evt.register.registerObjectTag(new ItemStack(ModItems.girdle_of_the_wooded), new AspectList().add(Aspect.PLANT, 20).add(Aspect.LIFE, 20).add(Aspect.PROTECT, 20).add(Aspect.MAGIC, 20));
		evt.register.registerObjectTag(new ItemStack(ModItems.omen_necklace), new AspectList().add(Aspect.DESIRE, 8).add(Aspect.METAL, 8).add(Aspect.CRYSTAL, 8).add(Aspect.PROTECT, 8).add(Aspect.MAGIC, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.horseshoe), new AspectList().add(Aspect.METAL, 8).add(Aspect.BEAST, 8).add(Aspect.PROTECT, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.wax), new AspectList().add(Aspect.CRAFT, 8).add(Aspect.ALCHEMY, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.taglock), new AspectList().add(Aspect.SOUL, 8).add(Aspect.LIFE, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.cold_iron_ingot), new AspectList().add(Aspect.AVERSION, 15).add(Aspect.COLD, 15).add(Aspect.METAL, 15));
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_honeycomb), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.VOID, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.honeycomb), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.LIFE, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem_garnet), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem_tigers_eye), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.SENSES, 4).add(Aspect.LIGHT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem_tourmaline), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.AURA, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem_malachite), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.EARTH, 4).add(Aspect.EXCHANGE, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.glass_jar), new AspectList().add(Aspect.VOID, 6).add(Aspect.CRYSTAL, 6));
		evt.register.registerObjectTag(new ItemStack(ModItems.spectral_dust), new AspectList().add(Aspect.VOID, 4).add(Aspect.SOUL, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.equine_tail), new AspectList().add(Aspect.BEAST, 4).add(Aspect.AIR, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.silver_scales), new AspectList().add(Aspect.BEAST, 4).add(Aspect.METAL, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.dimensional_sand), new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.DARKNESS, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.wool_of_bat), new AspectList().add(Aspect.BEAST, 4).add(Aspect.AIR, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.diabolic_vein), new AspectList().add(Aspect.CRAFT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.golden_thread), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.DESIRE, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.witches_stitching), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.MAGIC, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.pure_filament), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.AURA, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.soul_string), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.SOUL, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.regal_silk), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.SENSES, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.ritual_chalk_normal), new AspectList().add(Aspect.EARTH, 4).add(Aspect.MIND, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.ritual_chalk_golden), new AspectList().add(Aspect.EARTH, 4).add(Aspect.MAGIC, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.ritual_chalk_nether), new AspectList().add(Aspect.EARTH, 4).add(Aspect.FIRE, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.ritual_chalk_ender), new AspectList().add(Aspect.EARTH, 4).add(Aspect.ELDRITCH, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_hellebore), new AspectList().add(Aspect.PLANT, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_garlic), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.AVERSION, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_mandrake), new AspectList().add(Aspect.PLANT, 1).add(Aspect.MAGIC, 1).add(Aspect.EARTH, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_tulsi), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AURA, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_sagebrush), new AspectList().add(Aspect.PLANT, 1).add(Aspect.VOID, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_white_sage), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AURA, 1).add(Aspect.SOUL, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_aconitum), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_wormwood), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SOUL, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_chrysanthemum), new AspectList().add(Aspect.PLANT, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_mint), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.COLD, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_belladonna), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1).add(Aspect.MAGIC, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_thistle), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AVERSION, 1).add(Aspect.PROTECT, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.spanish_moss), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AIR, 2).add(Aspect.MAGIC, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.owlets_wing), new AspectList().add(Aspect.BEAST, 3).add(Aspect.AIR, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.ravens_feather), new AspectList().add(Aspect.BEAST, 3).add(Aspect.AIR, 2).add(Aspect.DARKNESS, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.graveyard_dust), new AspectList().add(Aspect.DEATH, 3).add(Aspect.SOUL, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.adders_fork), new AspectList().add(Aspect.DEATH, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.eye_of_newt), new AspectList().add(Aspect.WATER, 4).add(Aspect.SENSES, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.toe_of_frog), new AspectList().add(Aspect.WATER, 4).add(Aspect.ALCHEMY, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.lizard_leg), new AspectList().add(Aspect.MOTION, 4).add(Aspect.EARTH, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.juniper_berries), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MAGIC, 3));
		// Todo: Dynamic aspects based on brew contents
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_brew_drink), new AspectList().add(Aspect.ALCHEMY, 6).add(Aspect.CRYSTAL, 6).add(Aspect.VOID, 6));
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_brew_linger), new AspectList().add(Aspect.ALCHEMY, 6).add(Aspect.CRYSTAL, 6).add(Aspect.AIR, 6).add(Aspect.VOID, 6));
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_brew_splash), new AspectList().add(Aspect.ALCHEMY, 6).add(Aspect.CRYSTAL, 6).add(Aspect.EARTH, 6).add(Aspect.VOID, 6));

		// Fumes
		evt.register.registerObjectTag(new ItemStack(ModItems.unfired_jar), new AspectList().add(Aspect.EARTH, 11).add(Aspect.WATER, 11));
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_jar), new AspectList().add(Aspect.EARTH, 11).add(Aspect.WATER, 11).add(Aspect.FIRE, 11));
		evt.register.registerObjectTag(new ItemStack(ModItems.oak_spirit), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.birch_soul), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.SOUL, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.acacia_essence), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.MAGIC, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.spruce_heart), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.LIFE, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.cloudy_oil), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.DARKNESS, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.cleansing_aura), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.VOID, 5).add(Aspect.AURA, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.emanation_of_dishonesty), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.DARKNESS, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.everchanging_presence), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.SOUL, 5).add(Aspect.EXCHANGE, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.undying_image), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.BEAST, 5).add(Aspect.EXCHANGE, 3).add(Aspect.LIFE, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.demonic_dew), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.FIRE, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.otherworld_tears), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.ELDRITCH, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fiery_breeze), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.FIRE, 10));
		evt.register.registerObjectTag(new ItemStack(ModItems.heavenly_winds), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.AIR, 10));
		evt.register.registerObjectTag(new ItemStack(ModItems.petrichor_odour), new AspectList().add(Aspect.EARTH, 10).add(Aspect.WATER, 5).add(Aspect.SENSES, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.zephyr_of_the_depths), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 10).add(Aspect.AIR, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.reek_of_death), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.DEATH, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.vital_essence), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.LIFE, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.droplet_of_wisdom), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.MIND, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.bottled_magic), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.MAGIC, 5));

		// Todo: Make spells choose their aspects based on NBT Data.
		// Spells
		evt.register.registerObjectTag(new ItemStack(ModItems.spell_page, 1, 0), new AspectList().add(Aspect.MAGIC, 6).add(Aspect.TOOL, 6));

		// Blocks
		evt.register.registerObjectTag(new ItemStack(ModBlocks.moonbell), new AspectList().add(Aspect.PLANT, 6).add(Aspect.DARKNESS, 6).add(Aspect.MAGIC, 6));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.crystal_ball), new AspectList().add(Aspect.CRYSTAL, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.tarot_table), new AspectList().add(Aspect.EARTH, 25).add(Aspect.PLANT, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.fake_ice), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.goblet), new AspectList().add(Aspect.METAL, 15).add(Aspect.MAGIC, 15).add(Aspect.VOID, 15));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.thread_spinner), new AspectList().add(Aspect.PLANT, 30).add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 25));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.witchfire), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.oven), new AspectList().add(Aspect.FIRE, 25).add(Aspect.METAL, 30).add(Aspect.CRAFT, 30));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.nethersteel), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.METAL, 5));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.beehive), new AspectList().add(Aspect.BEAST, 15).add(Aspect.DESIRE, 15).add(Aspect.PLANT, 13));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.salt_ore), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));

		// Ores
		evt.register.registerObjectTag(new ItemStack(ModBlocks.garnet_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.PROTECT, 4).add(Aspect.EARTH, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.tigers_eye_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.SENSES, 4).add(Aspect.EARTH, 4).add(Aspect.LIGHT, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.tourmaline_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.AURA, 4).add(Aspect.EARTH, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.malachite_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.EARTH, 6).add(Aspect.EXCHANGE, 4));

		// Baubles
		evt.register.registerObjectTag(new ItemStack(ModItems.talisman_ruby_orb), new AspectList().add(Aspect.METAL, 25).add(Aspect.DESIRE, 25).add(Aspect.CRYSTAL, 25));
		evt.register.registerObjectTag(new ItemStack(ModItems.talisman_aquamarine_crown), new AspectList().add(Aspect.METAL, 15).add(Aspect.WATER, 25).add(Aspect.LIGHT, 10));
		evt.register.registerObjectTag(new ItemStack(ModItems.talisman_adamantine_star_ring), new AspectList().add(Aspect.CRYSTAL, 25).add(Aspect.DESIRE, 15));
		evt.register.registerObjectTag(new ItemStack(ModItems.talisman_watching_eye), new AspectList().add(Aspect.METAL, 25).add(Aspect.ELDRITCH, 15).add(Aspect.SENSES, 15));

		// Silver
		evt.register.registerObjectTag(new ItemStack(ModItems.silver_ingot), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.silver_ore), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5).add(Aspect.EARTH, 5));

		// Saplings
		evt.register.registerObjectTag(new ItemStack(ModBlocks.sapling, 1, 0), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.MIND, 3));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.sapling, 1, 1), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.MAGIC, 3));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.sapling, 1, 2), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.EXCHANGE, 3));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.sapling, 1, 3), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.DEATH, 3));

		// Technical
		evt.register.registerObjectTag(new ItemStack(ModBlocks.salt_barrier), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.EARTH, 5));

		// Vampire Gear
		evt.register.registerObjectTag(new ItemStack(ModItems.sanguine_fabric), new AspectList().add(Aspect.CRAFT, 10).add(Aspect.UNDEAD, 10));

		this.registerEntities();
	}

	@SuppressWarnings("deprecation")
	public void registerEntities() {
		ThaumcraftApi.registerEntityTag("entity_owl", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 10));
		ThaumcraftApi.registerEntityTag("entity_snake", new AspectList().add(Aspect.BEAST, 10).add(Aspect.AVERSION, 10));
		ThaumcraftApi.registerEntityTag("entity_raven", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 10).add(Aspect.DARKNESS, 8));
		ThaumcraftApi.registerEntityTag("entity_toad", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10).add(Aspect.ALCHEMY, 8));
		ThaumcraftApi.registerEntityTag("entity_lizard", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10));
	}

}
