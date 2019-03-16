package com.covens.common.item.magic.brew;

import java.util.List;
import java.util.Optional;

import com.covens.api.CovensAPI;
import com.covens.api.cauldron.DefaultModifiers;
import com.covens.api.cauldron.IBrewEffect;
import com.covens.api.cauldron.IBrewModifierList;
import com.covens.common.content.cauldron.BrewData;
import com.covens.common.content.cauldron.BrewData.BrewEntry;
import com.covens.common.content.cauldron.BrewModifierListImpl;
import com.covens.common.content.cauldron.CauldronRegistry;
import com.covens.common.core.helper.RomanNumberHelper;
import com.covens.common.core.statics.ModCreativeTabs;
import com.covens.common.item.ItemMod;
import com.covens.common.item.ModItems;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBrew extends ItemMod {

	public ItemBrew(String id) {
		super(id);
		this.setMaxStackSize(16);
		this.setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.BREW_CREATIVE_TAB);
	}

	@SideOnly(Side.CLIENT)
	public static void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		BrewData.fromStack(stack).getEffects().stream().filter(be -> be.getPotion() != null).forEach(brewEntry -> {
			TextFormatting color = brewEntry.getPotion().isBadEffect() ? TextFormatting.RED : TextFormatting.DARK_AQUA;
			IBrewModifierList list = brewEntry.getModifierList();
			if (GuiScreen.isShiftKeyDown()) {
				tooltip.add(color + I18n.format(brewEntry.getPotion().getName()));
				if (!list.getModifiers().isEmpty()) {
					list.getModifiers().stream().filter(modifier -> list.getLevel(modifier).isPresent()).forEach(bm -> {
						tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("brew.parameters.formatting", bm.getTooltipString(brewEntry.getModifierList().getLevel(bm).get())));
					});
				} else {
					tooltip.add(TextFormatting.DARK_GRAY.toString() + I18n.format("brew.parameters.none"));
				}
			} else {
				Optional<Integer> power = list.getLevel(DefaultModifiers.POWER);
				int lengthMod = list.getLevel(DefaultModifiers.DURATION).orElse(0);
				String powerString = "";
				String lengthString = "";
				if (power.isPresent() && (power.get() > 1)) {
					powerString = RomanNumberHelper.getRoman(power.get());
				}
				lengthString = getLengthTTip(lengthMod, brewEntry.getPotion(), stack.getItem());
				tooltip.add(color + I18n.format("brew.effects.formatting", I18n.format(brewEntry.getPotion().getName()), powerString, lengthString).replace("  ", " "));
			}
		});
	}

	@SideOnly(Side.CLIENT)
	private static String getLengthTTip(int lengthMod, Potion potion, Item item) {
		if (potion.isInstant()) {
			return I18n.format("brew.instant");
		}
		IBrewEffect effect = CovensAPI.getAPI().getBrewFromPotion(potion);
		int baseDuration = effect.getDefaultDuration();
		if (item == ModItems.brew_arrow) {
			baseDuration = effect.getArrowDuration();
		} else if (item == ModItems.brew_phial_linger) {
			baseDuration = effect.getLingeringDuration();
		}
		baseDuration /= 2;
		baseDuration *= 1 + (lengthMod * 0.3);
		baseDuration /= 20;
		int seconds = baseDuration % 60;
		int minutes = baseDuration / 60;
		return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			CauldronRegistry.BREW_POTION_MAP.values().forEach(p -> this.addPotionType(items, p));
		}
	}

	private void addPotionType(NonNullList<ItemStack> items, Potion p) {
		BrewData data = new BrewData();
		BrewModifierListImpl list = new BrewModifierListImpl();
		data.addEntry(new BrewEntry(p, list));
		ItemStack stack = new ItemStack(this);
		data.saveToStack(stack);
		items.add(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		addTooltip(stack, worldIn, tooltip, flagIn);
	}

}
