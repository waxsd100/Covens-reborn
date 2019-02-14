package com.covens.common.content.crystalBall.fortunes;

import java.util.Random;

import com.covens.common.content.crystalBall.Fortune;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class FortuneMeetMerchant extends Fortune {

	public FortuneMeetMerchant(int weight, String name, String modid) {
		super(weight, name, modid);
	}

	@Override
	public boolean canBeUsedFor(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canShouldBeAppliedNow(EntityPlayer player) {
		return player.getRNG().nextDouble() < 0.0001;
	}

	@Override
	public boolean apply(EntityPlayer player) {
		for (int i = 0; i < 10; i++) {
			BlockPos pos = new BlockPos(player.posX + (player.getRNG().nextGaussian() * 4), player.posY, player.posZ + (player.getRNG().nextGaussian() * 4));
			EntityVillager villager = new EntityVillager(player.world);
			if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(villager)) {
				villager.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				player.world.spawnEntity(villager);
				VillagerRegistry.setRandomProfession(villager, new Random());
				if (villager.getProfessionForge().getRegistryName().getPath().equals("nitwit")) {
					villager.setProfession(VillagerRegistry.FARMER);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isNegative() {
		return false;
	}

}
