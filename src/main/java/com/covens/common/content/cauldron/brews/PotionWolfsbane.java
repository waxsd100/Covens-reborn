package com.covens.common.content.cauldron.brews;

import com.covens.common.core.helper.MobHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class PotionWolfsbane extends GenericBrewDamageVS {

	public PotionWolfsbane() {
		super("wolfsbane", 0xEFCC00, 50);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return MobHelper.isCanid(entity);
	}

	@Override
	protected void applyExtraEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		if (amplifier > 2) {
			entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.WITHER, 40 * amplifier, 0));
		}
	}

}
