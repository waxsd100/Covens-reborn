package com.covens.common.content.ritual.rituals;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.covens.api.mp.MPUsingMachine;
import com.covens.api.ritual.IRitual;
import com.covens.common.content.ritual.AdapterIRitual;
import com.covens.common.item.ModItems;
import com.covens.common.item.magic.ItemLocationStone;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import zabi.minecraft.minerva.common.utils.DimensionalPosition;

public class RitualGateway implements IRitual {

	@Override
	public Optional<ITextComponent> isValid(EntityPlayer player, World world, BlockPos mainGlyphPos, List<ItemStack> recipe, BlockPos effectivePosition, int covenSize) {
		for (ItemStack stack : recipe) {
			if (stack.getItem().equals(ModItems.location_stone)) {
				if (ItemLocationStone.isBound(stack)) {
					return ItemLocationStone.getLocation(stack).get().getDim() == world.provider.getDimension()?Optional.empty():Optional.of(new TextComponentTranslation("ritual.problem.cross_dimension"));
				} else {
					return Optional.of(new TextComponentTranslation("ritual.problem.unbound_stone"));
				}
			}
		}
		return Optional.of(new TextComponentTranslation("ritual.problem.unbound_stone"));
	}

	@Override
	public void onUpdate(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, int ticks, BlockPos effectivePosition, int covenSize) {
		// TODO fix following particles
		world.spawnParticle(EnumParticleTypes.PORTAL, effectivePosition.getX() + 0.5, effectivePosition.getY() + 0.1, effectivePosition.getZ() + 0.5, 0, 0, 0);
		if ((ticks % 5) != 0) {
			return;
		}
		List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(effectivePosition).grow(0, 2, 0));
		if (list.isEmpty()) {
			return;
		}
		for (ItemStack stack : AdapterIRitual.getItemsUsedForInput(data)) {
			if (stack.getItem().equals(ModItems.location_stone) && ItemLocationStone.isBound(stack)) {
				DimensionalPosition pdest = ItemLocationStone.getLocation(stack).get();
				BlockPos dest = pdest.getPosition();
				int distance = (int) pdest.getDistanceSqFrom(new DimensionalPosition(effectivePosition, world.provider.getDimension()));
				list.stream().filter(e -> !e.isSneaking()).filter(elb -> tile.getCapability(MPUsingMachine.CAPABILITY, null).drainPlayerFirst((elb instanceof EntityPlayer ? ((EntityPlayer) elb) : null), circlePos, world.provider.getDimension(), distance)).forEach(elb -> elb.setPositionAndUpdate(dest.getX() + 0.5, dest.getY() + 0.1, dest.getZ() + 0.5));
				break;
			}
		}
	}

	@Override
	public boolean onLowPower(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, int ticks, BlockPos effectivePosition, int covenSize) {
		Random r = new Random();
		int max = r.nextInt(3);
		for (int i = 0; i < max; i++) {
			EntityEndermite mite = new EntityEndermite(world);
			mite.setPosition(effectivePosition.getX() + (r.nextGaussian() * 3), effectivePosition.getY() + (r.nextGaussian() * 3), effectivePosition.getZ() + (r.nextGaussian() * 3));
			world.spawnEntity(mite);
		}
		return true;
	}

	@Override
	public void onRandomDisplayTick(World world, BlockPos mainGlyphPos, BlockPos ep, Random rng) {
		for (int i=0; i< 20; i++) {
			double cx = ep.getX() + 0.5;
			double cy = ep.getY() + 0.5;
			double cz = ep.getZ() + 0.5;
			double sx = cx + rng.nextGaussian()*0.5;
			double sy = cy + rng.nextGaussian()*0.5;
			double sz = cz + rng.nextGaussian()*0.5;
			world.spawnParticle(EnumParticleTypes.PORTAL, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz));
		}
	}

}
