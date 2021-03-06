package com.covens.common.core.helper;

import java.util.Set;

import com.covens.api.CovensAPI;
import com.covens.api.transformation.DefaultTransformations;
import com.covens.common.content.transformation.CapabilityTransformation;
import com.covens.common.core.capability.familiar.CapabilityFamiliarCreature;
import com.covens.common.entity.EntityBatSwarm;
import com.google.common.collect.Sets;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.INpc;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 8/25/2018. Credit to Alexthe666 for some of the code.
 */

public class MobHelper {

	public static final Set<String> VILLAGERS = Sets.newHashSet(); // Hmm hmmm mmph
	public static final Set<String> SPIRITS = Sets.newHashSet(); // Spirits and discarnate undead
	public static final Set<String> CANIDS = Sets.newHashSet(); // The entire canid family of carnivorans
	public static final Set<String> DEMONS = Sets.newHashSet(); // Infernal beings
	public static final Set<String> UNDEAD_BODY = Sets.newHashSet(); // Corporeal undead, such as zombies and mummies
	public static final Set<String> HUMANS = Sets.newHashSet(); // People

	public static void init() {

		/*
		 * Don't overload them for the sake of adding stuff Eg: endermen are not in any
		 * of these lists Less is more in this case
		 */

		// This class is to be used in extending certain brews/weapons/mechanics only.
		// Things like mobs eating each other go in their own class.

		SPIRITS.add(EntityGhast.class.getName());
		SPIRITS.add(EntityVex.class.getName());
		SPIRITS.add("thaumcraft.common.entities.monster.EntityWisp"); // <-- Example of fully qualified name
		SPIRITS.add("astralsorcery.common.entities.EntityFlare");
		SPIRITS.add("its_meow.betteranimalsplus.common.entity.miniboss.hirschgeist.EntityHirschgeist");
		SPIRITS.add("com.jarhax.eerieentities.entities.EntityCursedArmor");
		SPIRITS.add("familiarfauna.entities.EntityPixie");
		SPIRITS.add("com.github.alexthe666.iceandfire.entity.EntityPixie");
		SPIRITS.add("teamroots.roots.entity.EntityFairy");

		DEMONS.add(EntityBlaze.class.getName());

		CANIDS.add(EntityWolf.class.getName());
		CANIDS.add("evilcraft.common.entity.monster.Werewolf");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityFeralWolf");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityFox");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityCoyote");
		CANIDS.add("elucent.mysticalworld.entity.EntityFox");

		// Villagers are already included in the list
		HUMANS.add(EntityWitch.class.getName());
		HUMANS.add(EntityIllusionIllager.class.getName());
		HUMANS.add(EntityEvoker.class.getName());
		HUMANS.add(EntityVindicator.class.getName());
		HUMANS.add("thaumcraft.common.entities.monster.cult.EntityCultistCleric");
		HUMANS.add("thaumcraft.common.entities.monster.cult.EntityCultistKnight");
		
		UNDEAD_BODY.add(EntityBatSwarm.class.getName());

		VILLAGERS.add("mca.entity.EntityVillagerMCA");

		if (System.getProperty("stickdebug", "").equals("true")) {
			MinecraftForge.EVENT_BUS.register(new Object() {
				@SubscribeEvent
				public void onStickInteraction(EntityInteractSpecific evt) {
					if (evt.getItemStack().getItem() == Items.STICK) {
						Log.i(evt.getTarget().getClass().getName());
					}
				}
			});
		}

	}
	
	public static boolean isLivingCorporeal(EntityLivingBase entity) {
		return !isSpirit(entity) && !isDemon(entity) && !isCorporealUndead(entity);
	}

	public static boolean isVillager(EntityLivingBase entity) {
		if (entity instanceof INpc) {
			return true;
		}
		return VILLAGERS.contains(entity.getClass().getName());
	}

	public static boolean isHumanoid(EntityLivingBase entity) {
		if (isVillager(entity) || (entity instanceof EntityPlayer)) {
			return true;
		}
		return HUMANS.contains(entity.getClass().getName());
	}

	public static boolean isDemon(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == CovensAPI.getAPI().DEMON) {
			return true;
		}
		return DEMONS.contains(entity.getClass().getName());
	}

	// For usage in cold iron
	public static boolean isSpirit(EntityLivingBase entity) {
		if (entity == null) {
			Log.w("Null entity cannot be spirit");
			Thread.dumpStack();
			return false;
		}
		if (entity.getCreatureAttribute() == CovensAPI.getAPI().SPIRIT) {
			return true;
		}
		if  (CovensAPI.getAPI().isValidFamiliar(entity) && entity.getCapability(CapabilityFamiliarCreature.CAPABILITY, null).hasOwner()) {
			return true;
		}
		if (CovensAPI.getAPI().isValidFamiliar(entity) && entity.getCapability(CapabilityFamiliarCreature.CAPABILITY, null).hasOwner()) {
			return true;
		}
		return SPIRITS.contains(entity.getClass().getName());
	}

	// For usage in silver
	public static boolean isCorporealUndead(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
			return true;
		}
		if ((entity instanceof EntityPlayer) && (entity.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE)) {
			return true;
		}
		return UNDEAD_BODY.contains(entity.getClass().getName());
	}

	// For usage in aconite
	public static boolean isCanid(EntityLivingBase entity) {
		if ((entity instanceof EntityPlayer) && (entity.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF)) {
			return true;
		}
		return CANIDS.contains(entity.getClass().getName());
	}

	public static boolean isSupernatural(EntityLivingBase e) {
		if (e instanceof EntityPlayer) {
			if (((EntityPlayer) e).isCreative()) {
				return false;
			}
			return !e.getCapability(CapabilityTransformation.CAPABILITY, null).getType().canCrossSalt();
		}
		return isDemon(e) || isSpirit(e) || isCorporealUndead(e);
	}
}
