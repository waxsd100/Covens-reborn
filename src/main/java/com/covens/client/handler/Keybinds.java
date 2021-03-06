package com.covens.client.handler;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Keybinds {

	public static KeyBinding gotoExtraBar = new KeyBinding("key.description.jump_to_bar", Keyboard.KEY_NONE, "key.categories.covens");
	public static KeyBinding alwaysEnableBar = new KeyBinding("key.description.enable_bar", Keyboard.KEY_NONE, "key.categories.covens");
	public static KeyBinding placeItem = new KeyBinding("key.description.place_item", Keyboard.KEY_NONE, "key.categories.covens");

	public static void registerKeys() {
		ClientRegistry.registerKeyBinding(gotoExtraBar);
		ClientRegistry.registerKeyBinding(alwaysEnableBar);
		ClientRegistry.registerKeyBinding(placeItem);
	}

}
