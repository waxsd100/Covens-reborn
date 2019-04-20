package com.covens.common.core.capability.simple;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import zabi.minecraft.minerva.common.capability.SimpleCapability;

public class BarkCapability extends SimpleCapability {

	@CapabilityInject(BarkCapability.class)
	public static final Capability<BarkCapability> CAPABILITY = null;

	public int pieces = 0;

	@Override
	public boolean isRelevantFor(Entity object) {
		return object instanceof EntityPlayer;
	}

	@Override
	public SimpleCapability getNewInstance() {
		return new BarkCapability();
	}

}
