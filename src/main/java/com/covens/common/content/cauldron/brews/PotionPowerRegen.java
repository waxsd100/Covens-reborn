package com.covens.common.content.cauldron.brews;

import com.covens.api.mp.IMagicPowerContainer;
import com.covens.common.content.cauldron.BrewMod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionPowerRegen extends BrewMod {

	public PotionPowerRegen() {
		super("power_regen", false, 0x7851A9, false, 400, 80);
		this.setIconIndex(3, 2);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return (duration % 20) == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity instanceof EntityPlayer) {
			IMagicPowerContainer playerMP = entity.getCapability(IMagicPowerContainer.CAPABILITY, null);
			playerMP.fill(amplifier);
		}
	}

}
