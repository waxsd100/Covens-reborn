package com.covens.common.content.cauldron.brews;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;

public class PotionBaneArthropods extends GenericBrewDamageVS {

	public PotionBaneArthropods() {
		super("bane_arthropods", 0x50C878, 20);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return entity.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD;
	}

}
