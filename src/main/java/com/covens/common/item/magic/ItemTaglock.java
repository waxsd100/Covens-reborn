package com.covens.common.item.magic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.covens.common.core.helper.NBTHelper;
import com.covens.common.item.ItemMod;
import com.covens.common.lib.LibItemName;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zabi.minecraft.minerva.common.entity.RayTraceHelper;


public class ItemTaglock extends ItemMod {

	public static final String TAGLOCK_ENTITY = null;
	public static final String TAGLOCK_ENTITY_NAME = null;

	public ItemTaglock() {
		super(LibItemName.TAGLOCK);
	}

	public static Optional<EntityLivingBase> getVictim(ItemStack stack, World world) {
		UUID uuid = NBTHelper.getUniqueID(stack, TAGLOCK_ENTITY);
		for (Entity entity : world.loadedEntityList) {
			if ((entity instanceof EntityLivingBase) && entity.getUniqueID().equals(uuid)) {
				return Optional.of((EntityLivingBase) entity);
			}
		}
		EntityPlayer victim = world.getPlayerEntityByUUID(uuid);
		return Optional.ofNullable(victim);
	}

	public static void setVictim(ItemStack stack, EntityLivingBase victim) {
		NBTHelper.setUniqueID(stack, TAGLOCK_ENTITY, victim.getUniqueID());
		NBTHelper.setString(stack, TAGLOCK_ENTITY_NAME, victim.getName());
	}

	public static void removeVictim(ItemStack stack) {
		NBTHelper.removeTag(stack, TAGLOCK_ENTITY);
		NBTHelper.removeTag(stack, TAGLOCK_ENTITY_NAME);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			RayTraceResult result = RayTraceHelper.rayTraceResult(player, RayTraceHelper.fromLookVec(player, 2), true, true);
			if ((result != null) && (result.typeOfHit == Type.ENTITY) && (result.entityHit instanceof EntityLivingBase)) {
				setVictim(player.getHeldItem(hand), (EntityLivingBase) result.entityHit);
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		if (NBTHelper.hasTag(stack, TAGLOCK_ENTITY_NAME)) {
			tooltip.add(TextFormatting.DARK_GRAY + NBTHelper.getString(stack, TAGLOCK_ENTITY_NAME));
		} else {
			tooltip.add(TextFormatting.DARK_GRAY + I18n.format("item.tag_lock.empty"));
		}
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock().isBed(state, world, pos, player)) {
			Optional<EntityPlayer> victim = this.getPlayerFromBed(world, pos);
			if (victim.isPresent()) {
				setVictim(player.getHeldItem(hand), victim.get());
				return EnumActionResult.SUCCESS;
			}
		}

		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}

	private Optional<EntityPlayer> getPlayerFromBed(World world, BlockPos bed) {
		return world.playerEntities.stream().filter(player -> player.bedLocation != null).filter(player -> player.getBedLocation().equals(bed)).findAny();
	}
}
