package com.provismet.dynamicFB;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMain implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Dynamic Fullbright");

	@Override
	public void onInitializeClient () {
		LightingManager.load();
	}
}
