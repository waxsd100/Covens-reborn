package com.covens.common.core.proxy;

import java.util.ArrayList;

import com.covens.api.hotbar.IHotbarAction;
import com.covens.client.fx.ParticleF;
import com.covens.common.content.tarot.TarotHandler.TarotInfo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class ServerProxy implements ISidedProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		// NO-OP
	}

	@Override
	public void init(FMLInitializationEvent event) {
		// NO-OP
	}

	@Override
	public void spawnParticle(ParticleF particleF, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
		// NO-OP
	}

	@Override
	public boolean isFancyGraphicsEnabled() {
		return false;
	}

	@Override
	public void handleTarot(ArrayList<TarotInfo> tarots) {
		// NO-OP
	}

	@Override
	public void loadActionsClient(ArrayList<IHotbarAction> actions, EntityPlayer player) {
		// NO-OP
	}

	@Override
	public boolean isPlayerInEndFire() {
		return false;
	}

	@Override
	public void stopMimicking(EntityPlayer p) {
		// NO-OP
	}
}
