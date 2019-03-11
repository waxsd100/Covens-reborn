package com.covens.common.item.block;

import com.covens.common.block.ModBlocks;
import com.covens.common.item.ItemBlockMod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import zabi.minecraft.minerva.client.blockmodels.IModelRegister;

public class ItemGoblet extends ItemBlockMod implements IModelRegister {

	public ItemGoblet() {
		super(ModBlocks.goblet);
		this.setHasSubtypes(true);
		this.setRegistryName(ModBlocks.goblet.getRegistryName());
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (stack.getMetadata() != 1) {
			return super.getTranslationKey(stack);
		}
		return super.getTranslationKey(stack) + "_filled";
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public void registerModel() {
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(new ResourceLocation(this.getRegistryName().toString()), "inventory");
		ModelLoader.setCustomModelResourceLocation(this, 0, modelResourceLocation);
		ModelLoader.setCustomModelResourceLocation(this, 1, modelResourceLocation);
	}
	
}
