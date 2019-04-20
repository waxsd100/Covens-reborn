package com.covens.common.core.event;

import com.covens.common.content.crystalBall.capability.FortuneCapabilityProvider;
import com.covens.common.content.infusion.capability.InfusionProvider;
import com.covens.common.content.transformation.vampire.blood.BloodReserveProvider;
import com.covens.common.core.capability.bedowner.CapabilityBedOwnerProvider;
import com.covens.common.core.capability.energy.player.PlayerMPContainerProvider;
import com.covens.common.core.capability.energy.player.expansion.MagicPowerExpansionProvider;
import com.covens.common.core.capability.mimic.MimicDataProvider;
import com.covens.common.lib.LibMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityAttacher {

	public static final ResourceLocation MP_PLAYER_TAG = new ResourceLocation(LibMod.MOD_ID, "mp");
	public static final ResourceLocation DIVINATION_TAG = new ResourceLocation(LibMod.MOD_ID, "divination");
	public static final ResourceLocation BLOOD_TAG = new ResourceLocation(LibMod.MOD_ID, "blood_pool");
	public static final ResourceLocation MIMIC_TAG = new ResourceLocation(LibMod.MOD_ID, "mimic_data");
	public static final ResourceLocation TRANSFORMATION_TAG = new ResourceLocation(LibMod.MOD_ID, "transformations");
	public static final ResourceLocation INFUSION_TAG = new ResourceLocation(LibMod.MOD_ID, "infusion");
	public static final ResourceLocation MP_EXPANSION_TAG = new ResourceLocation(LibMod.MOD_ID, "mp_expansion");
	public static final ResourceLocation BED_OWNER = new ResourceLocation(LibMod.MOD_ID, "bed_owner");

	@SubscribeEvent
	public static void attach(AttachCapabilitiesEvent<Entity> evt) {

		if (evt.getObject() instanceof EntityLivingBase) {
			evt.addCapability(BLOOD_TAG, new BloodReserveProvider());
		}

		if (evt.getObject() instanceof EntityPlayer) {
			evt.addCapability(DIVINATION_TAG, new FortuneCapabilityProvider());
			evt.addCapability(MP_PLAYER_TAG, new PlayerMPContainerProvider());
			evt.addCapability(MIMIC_TAG, new MimicDataProvider());
			evt.addCapability(INFUSION_TAG, new InfusionProvider());
			evt.addCapability(MP_EXPANSION_TAG, new MagicPowerExpansionProvider());
		}
	}
	
	@SubscribeEvent
	public static void attachTiles(AttachCapabilitiesEvent<TileEntity> evt) {
		if (evt.getObject() instanceof TileEntityBed) {
			evt.addCapability(BED_OWNER, new CapabilityBedOwnerProvider());
		}
	}

}
