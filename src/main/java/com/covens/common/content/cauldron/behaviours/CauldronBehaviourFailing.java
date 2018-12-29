package com.covens.common.content.cauldron.behaviours;

import com.covens.common.tile.tiles.TileEntityCauldron;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;

import java.util.Random;

public class CauldronBehaviourFailing implements ICauldronBehaviour {

	private static final String ID = "fail";
	private TileEntityCauldron cauldron;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		cauldron = tile;
	}

	@Override
	public void handleParticles(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			Random r = cauldron.getWorld().rand;
			cauldron.getWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, cauldron.getPos().getX() + 0.4 + 0.2 * r.nextDouble(), cauldron.getPos().getY() + 0.5, cauldron.getPos().getZ() + 0.4 + 0.2 * r.nextDouble(), 0, 0, 0);
		}
	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return true;
	}

	@Override
	public void statusChanged(boolean isActiveBehaviour) {
		//NO-OP
	}

	@Override
	public void update(boolean isActiveBehaviour) {
		//NO-OP
	}

	@Override
	public int getColor() {
		return 0x819804;
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {
		//NO-OP
	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {
		//NO-OP
	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {
		//NO-OP
	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {
		//NO-OP
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void onDeactivation() {
		//NO-OP
	}

}
