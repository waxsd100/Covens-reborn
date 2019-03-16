package com.covens.common.item.equipment.baubles;

import com.covens.common.core.helper.MobHelper;
import com.covens.common.core.statics.ModCreativeTabs;
import com.covens.common.item.ItemMod;
import com.covens.common.lib.LibItemName;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 1/1/2018.
 */
public class ItemHorseshoe extends ItemMod implements IBauble {
	public ItemHorseshoe() {
		super(LibItemName.HORSESHOE);
		this.setMaxStackSize(1);
		this.setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for (int i = 0; i < baubles.getSlots(); i++) {
				if (baubles.getStackInSlot(i).isEmpty() && baubles.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
					if (!player.capabilities.isCreativeMode) {
						player.setHeldItem(hand, ItemStack.EMPTY);
					}
					this.onEquipped(player.getHeldItem(hand), player);
					break;
				}
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemStack) {
		return BaubleType.TRINKET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if ((itemstack.getItemDamage() == 0) && ((player.ticksExisted % 40) == 0)) {
			player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 120, 0, true, true));
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundCategory.PLAYERS, .75F, 1.9f);
	}


	@SubscribeEvent
	public void onEntityDamage(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityLivingBase && MobHelper.isSpirit((EntityLivingBase) event.getSource().getTrueSource())) {
			event.setAmount(event.getAmount() * 0.80F);
		}
	}
}
