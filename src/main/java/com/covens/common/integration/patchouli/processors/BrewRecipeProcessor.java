package com.covens.common.integration.patchouli.processors;

import java.util.NoSuchElementException;
import java.util.Objects;

import com.covens.api.cauldron.IBrewEffect;
import com.covens.common.content.cauldron.CauldronRegistry;
import com.covens.common.core.helper.Log;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.util.text.TextFormatting;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

public class BrewRecipeProcessor implements IComponentProcessor {

	private Ingredient brew;
	private Potion p;
	private IBrewEffect brewEff;

	@Override
	public void setup(IVariableProvider<String> json) {
		String[] potionAlternatives = json.get("brew").split(";");
		for (String name:potionAlternatives) {
			try {
				Potion tp = Potion.getPotionFromResourceLocation(name);
				Ingredient ing = CauldronRegistry.getIngredientFromBrew(CauldronRegistry.getBrewFromPotion(tp)).orElse(null);
				IBrewEffect be = CauldronRegistry.getBrewFromPotion(tp);
				if (ing != null && tp != null && be != null) {
					this.brew = ing;
					this.p = tp;
					this.brewEff = be;
					break;
				}
			} catch (NoSuchElementException e) {
				//Ignore
				continue;
			} catch (IllegalArgumentException e) {
				Log.e("Potion "+name+" not found");
				continue;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Objects.requireNonNull(this.p);
		Objects.requireNonNull(this.brew);
		Objects.requireNonNull(this.brewEff);
	}

	@Override
	public String process(String val) {
		try {
			if ("potionname".equals(val)) {
				return (this.p.isBeneficial() ? TextFormatting.DARK_BLUE : TextFormatting.DARK_RED) + TextFormatting.BOLD.toString() + I18n.format(this.p.getName());
			}
			if ("ingredient".equals(val)) {
				return PatchouliAPI.instance.serializeIngredient(this.brew);
			}
			if ("cost".equals(val)) {
				return I18n.format("covens.brew.cost", brewEff.getCost());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		throw new IllegalArgumentException("Unrecognized potion/brew value: " + val);
	}

}
