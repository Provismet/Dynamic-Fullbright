package com.provismet.dynamicFB;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMain implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Dynamic Fullbright");

	public static KeyBinding toggleLighting = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.dynamicfullbright.toggle",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_UNKNOWN,
        "category.dynamicfullbright.keys"
    ));

	public static boolean hasCloth () {
        try {
            Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder");
            return true;
        }
		catch (ClassNotFoundException e) {
            return false;
        }
    }

	@Override
	public void onInitializeClient () {
		LightingManager.load();
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleLighting.wasPressed()) {
				LightingManager.toggleActive();
				String mes = "";
				if (LightingManager.isActive()) mes = "Dynamic Fullbright is on.";
				else mes = "Dynamic Fullbright is off.";
				client.player.sendMessage(Text.of(mes), true);	
			}
		});
	}
}
