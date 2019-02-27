package com.covens.common.content.cauldron.brews;

import java.util.ArrayList;

import com.covens.common.content.cauldron.BrewMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionCorruption extends BrewMod {

	public PotionCorruption() {
		super("corruption", true, 0x66FF00, true, 0, 40);
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		ArrayList<Potion> removalList = new ArrayList<>();
		entity.getActivePotionEffects().stream().map(PotionEffect::getPotion).filter(potion -> !potion.isBadEffect()).forEach(pot -> removalList.add(pot));
		removalList.forEach(pot -> entity.removePotionEffect(pot));
	}
}
