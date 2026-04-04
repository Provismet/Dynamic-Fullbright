package com.provismet.dynamicFB;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMain implements ClientModInitializer {
    public static final String MODID = "dynamicfullbright";
	public static final Logger LOGGER = LoggerFactory.getLogger("Dynamic Fullbright");
    public static final KeyMapping.Category KEYBIND_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MODID, "keys"));

	public static KeyMapping toggleLighting = KeyMappingHelper.registerKeyMapping(new KeyMapping(
        "key.dynamicfullbright.toggle",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_UNKNOWN,
        KEYBIND_CATEGORY
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
			while (toggleLighting.consumeClick()) {
				LightingManager.toggleActive();
				String mes = LightingManager.isActive() ? "message.dynamicfullbright.on" : "message.dynamicfullbright.off";
				client.player.sendOverlayMessage(Component.translatable(mes));
			}
		});
	}
}
