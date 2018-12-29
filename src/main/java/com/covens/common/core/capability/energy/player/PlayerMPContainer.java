package com.covens.common.core.capability.energy.player;

import com.covens.api.mp.DefaultMPContainer;

public class PlayerMPContainer extends DefaultMPContainer {

	public static final int STARTING_PLAYER_POWER = 800;

	private boolean dirty = false;

	public PlayerMPContainer() {
		super(STARTING_PLAYER_POWER);
	}

	@Override
	public void setAmount(int newAmount) {
		dirty = true;
		super.setAmount(newAmount);
	}

	@Override
	public void setMaxAmount(int newMaxAmount) {
		dirty = true;
		super.setMaxAmount(newMaxAmount);
	}

	public void setClean() {
		dirty = false;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void markDirty() {
		dirty = true;
	}
}
