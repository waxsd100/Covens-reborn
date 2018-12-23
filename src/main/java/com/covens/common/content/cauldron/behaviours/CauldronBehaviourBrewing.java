package com.covens.common.content.cauldron.behaviours;

import com.covens.api.mp.IMagicPowerConsumer;
import com.covens.common.content.cauldron.BrewBuilder;
import com.covens.common.content.cauldron.BrewData;
import com.covens.common.item.ModItems;
import com.covens.common.tile.tiles.TileEntityCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.Optional;

public class CauldronBehaviourBrewing implements ICauldronBehaviour {

	private static final String ID = "brew";
	private int color = TileEntityCauldron.DEFAULT_COLOR;

	private TileEntityCauldron cauldron;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		cauldron = tile;
	}

	@Override
	public void handleParticles(boolean isActiveBehaviour) {
		//TODO particle indicators for energy missing
	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return true;
	}

	@Override
	public void statusChanged(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			checkBrew();
			if (cauldron.getInputs().size() == 1) {
				color = 0xe050a0;
			}
		}
	}

	@Override
	public void playerInteract(EntityPlayer player, EnumHand hand) {
		if (!player.world.isRemote) {
			Item heldItem = player.getHeldItem(hand).getItem();
			int potionAmountUsed = 500;

			if (heldItem == Items.ARROW) {
				potionAmountUsed = 100;
			} else if (heldItem == ModItems.empty_brew_drink) {
				potionAmountUsed = 300;
			}

			if (hasEnergy(1000) && hasRequiredFluidAmount(potionAmountUsed)) { //TODO make energy dependent on brew
				if (heldItem == ModItems.empty_brew_drink) {
					TileEntityCauldron.giveItemToPlayer(player, getBrewStackFor(new ItemStack(ModItems.brew_phial_drink)));
				} else if (heldItem == ModItems.empty_brew_linger) {
					TileEntityCauldron.giveItemToPlayer(player, getBrewStackFor(new ItemStack(ModItems.brew_phial_linger)));
				} else if (heldItem == ModItems.empty_brew_splash) {
					TileEntityCauldron.giveItemToPlayer(player, getBrewStackFor(new ItemStack(ModItems.brew_phial_splash)));
				} else if (heldItem == Items.ARROW) {
					TileEntityCauldron.giveItemToPlayer(player, getBrewStackFor(new ItemStack(ModItems.brew_arrow)));
				}
				cauldron.setTankLock(true);
				cauldron.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).drain(potionAmountUsed, true);
				cauldron.setTankLock(false);
			}

			if (!cauldron.getFluid().isPresent() || cauldron.getFluid().get().amount <= 0) {
				cauldron.setTankLock(true);
				cauldron.clearItemInputs();
				cauldron.setBehaviour(cauldron.getDefaultBehaviours().IDLE);
			}
			cauldron.markDirty();
			cauldron.syncToClient();
		}
	}
	
	private boolean hasEnergy(int amount) {
		return cauldron.getCapability(IMagicPowerConsumer.CAPABILITY, null).drainAltarFirst(null, cauldron.getPos(), cauldron.getWorld().provider.getDimension(), amount);
	}
	
	private boolean hasRequiredFluidAmount(int potionAmountUsed) {
		return cauldron.getFluid().isPresent() && cauldron.getFluid().get().amount >= potionAmountUsed;
	}

	@Override
	public void update(boolean isActiveBehaviour) {
		//NO-OP
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {
		tag.setInteger("potColor", color);
	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {
		color = tag.getInteger("potColor");
	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {
		saveToNBT(tag);
	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {
		loadFromNBT(tag);
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void onDeactivation() {
		color = TileEntityCauldron.DEFAULT_COLOR;
	}

	private void checkBrew() {
		if (cauldron.getInputs().size() > 1) { //Ignore the wart
			Optional<BrewData> data = new BrewBuilder(cauldron.getInputs()).build();
			if (data.isPresent()) {
				color = data.get().getColor();
			} else {
				cauldron.setBehaviour(cauldron.getDefaultBehaviours().FAILING);
			}
			cauldron.markDirty();
			cauldron.syncToClient();
		}
	}

	private ItemStack getBrewStackFor(ItemStack stack) {
		Optional<BrewData> data = new BrewBuilder(cauldron.getInputs()).build();
		if (data.isPresent()) {
			data.get().saveToStack(stack);
		}
		return stack;
	}

}
