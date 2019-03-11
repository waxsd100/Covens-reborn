package com.covens.common.core.capability.energy;

import com.covens.api.mp.DefaultMPContainer;
import com.covens.api.mp.MPContainer;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class MagicPowerContainer {
	public static void init() {
		CapabilityManager.INSTANCE.register(MPContainer.class, new MagicPowerContainerStorage(), () -> new DefaultMPContainer(0));
	}
}
