package com.covens.common.content.cauldron.behaviours;

import java.util.Optional;

import com.covens.api.mp.MPUsingMachine;
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

public class CauldronBehaviourBrewing implements ICauldronBehaviour {

	private static final String ID = "brew";
	private int color = TileEntityCauldron.DEFAULT_COLOR;
	private boolean finalized = false;
	private int projectedCost = 0;

	private TileEntityCauldron cauldron;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		this.cauldron = tile;
	}

	@Override
	public void handleParticles(boolean isActiveBehaviour) {
		// TODO particle indicators for energy missing
	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return !finalized;
	}

	@Override
	public void statusChanged(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			this.checkBrew();
			if (this.cauldron.getInputs().size() == 1) {
				this.color = 0xe050a0;
				this.finalized = false;
				this.projectedCost = 0;
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
			int cost = new BrewBuilder(this.cauldron.getInputs()).build().orElseGet(BrewData::new).getCost();
			if ((finalized || this.hasEnergy(cost)) && this.hasRequiredFluidAmount(potionAmountUsed)) { // TODO make energy dependent on brew
				finalized = true;
				if (heldItem == ModItems.empty_brew_drink) {
					TileEntityCauldron.giveItemToPlayer(player, this.getBrewStackFor(new ItemStack(ModItems.brew_phial_drink)));
				} else if (heldItem == ModItems.empty_brew_linger) {
					TileEntityCauldron.giveItemToPlayer(player, this.getBrewStackFor(new ItemStack(ModItems.brew_phial_linger)));
				} else if (heldItem == ModItems.empty_brew_splash) {
					TileEntityCauldron.giveItemToPlayer(player, this.getBrewStackFor(new ItemStack(ModItems.brew_phial_splash)));
				} else if (heldItem == Items.ARROW) {
					TileEntityCauldron.giveItemToPlayer(player, this.getBrewStackFor(new ItemStack(ModItems.brew_arrow)));
				}
				this.cauldron.setTankLock(true);
				this.cauldron.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).drain(potionAmountUsed, true);
				this.cauldron.setTankLock(false);
			}

			if (!this.cauldron.getFluid().isPresent() || (this.cauldron.getFluid().get().amount <= 0)) {
				this.cauldron.setTankLock(true);
				this.cauldron.clearItemInputs();
				this.cauldron.setBehaviour(this.cauldron.getDefaultBehaviours().IDLE);
				this.finalized = false;
			}
			this.cauldron.markDirty();
			this.cauldron.syncToClient();
		}
	}

	private boolean hasEnergy(int amount) {
		return this.cauldron.getCapability(MPUsingMachine.CAPABILITY, null).drainAltarFirst(null, this.cauldron.getPos(), this.cauldron.getWorld().provider.getDimension(), amount);
	}

	private boolean hasRequiredFluidAmount(int potionAmountUsed) {
		return this.cauldron.getFluid().isPresent() && (this.cauldron.getFluid().get().amount >= potionAmountUsed);
	}

	@Override
	public void update(boolean isActiveBehaviour) {
		// NO-OP
	}

	@Override
	public int getColor() {
		return this.color;
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {
		saveToSyncNBT(tag);
		tag.setBoolean("finalized", finalized);
	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {
		this.loadFromSyncNBT(tag);
		this.finalized = tag.getBoolean("finalized");
	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {
		tag.setInteger("potColor", this.color);
		tag.setInteger("cost", this.projectedCost);
	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {
		this.color = tag.getInteger("potColor");
		this.projectedCost = tag.getInteger("cost");
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void onDeactivation() {
		this.color = TileEntityCauldron.DEFAULT_COLOR;
		this.projectedCost = 0;
	}

	private void checkBrew() {
		if (this.cauldron.getInputs().size() > 1) { // Ignore the wart
			Optional<BrewData> data = new BrewBuilder(this.cauldron.getInputs()).build();
			if (data.isPresent()) {
				this.color = data.get().getColor();
				this.projectedCost = data.get().getCost();
			} else {
				this.cauldron.setBehaviour(this.cauldron.getDefaultBehaviours().FAILING);
				this.projectedCost = 0;
			}
			this.cauldron.markDirty();
			this.cauldron.syncToClient();
		}
	}

	private ItemStack getBrewStackFor(ItemStack stack) {
		Optional<BrewData> data = new BrewBuilder(this.cauldron.getInputs()).build();
		if (data.isPresent()) {
			data.get().saveToStack(stack);
		}
		return stack;
	}

}
