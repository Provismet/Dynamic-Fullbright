package com.provismet.dynamicFB;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import com.provismet.lilylib.util.json.JsonConfig;
import com.provismet.lilylib.util.json.JsonReader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class LightingManager {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("dynamic-fullbright.json");

    private static final int MAX_LIGHT = 15;
    private static final int MIN_LIGHT = 0;

    private static boolean isActive = false;
    public static boolean rememberActive = false;
    public static boolean separateEntityLight = false;
    public static boolean shouldScaleLighting = true;
    public static boolean shouldScaleSkyBrightness = true;
    
    private static int minBlockLight = 4;
    private static int maxBlockLight = 15;

    private static int minSkyLight = 4;
    private static int maxSkyLight = 15;

    private static int minEntityLight = 4;
    private static int maxEntityLight = 15;

    private static final JsonConfig SERIALISER = new JsonConfig()
        .addBoolean("separate entity lighting", () -> separateEntityLight, val -> separateEntityLight = val)
        .addBoolean("scaling", () -> shouldScaleLighting, val -> shouldScaleLighting = val)
        .addInteger("min block", () -> minBlockLight, LightingManager::setMinimumBlockLight)
        .addInteger("max block", () -> maxBlockLight, LightingManager::setMaximumBlockLight)
        .addInteger("min entity", () -> minEntityLight, LightingManager::setMinimumEntityLight)
        .addInteger("max entity", () -> maxEntityLight, LightingManager::setMaximumEntityLight)
        .addInteger("min sky", () -> minSkyLight, LightingManager::setMinimumSkyLight)
        .addInteger("max sky", () -> maxSkyLight, LightingManager::setMaximumSkyLight)
        .addBoolean("retain on-state", () -> rememberActive, val -> rememberActive = val)
        .addBoolean("on-state", () -> isActive, val -> isActive = rememberActive ? val : isActive)
        .addBoolean("scale sky", () -> shouldScaleSkyBrightness, val -> shouldScaleSkyBrightness = val);

    public static boolean isActive () {
        return isActive;
    }

    public static void setActive (boolean value) {
        isActive = value;
        Minecraft.getInstance().levelRenderer.allChanged();
    }

    public static boolean isEntityActive () {
        return (!(minEntityLight == MIN_LIGHT && maxEntityLight == MAX_LIGHT) && separateEntityLight) || (!separateEntityLight && isBlockActive());
    }

    public static boolean isBlockActive  () {
        return !(minBlockLight == MIN_LIGHT && minSkyLight == MIN_LIGHT && maxBlockLight == MAX_LIGHT && maxSkyLight == MAX_LIGHT);
    }

    public static void toggleActive () {
        setActive(!isActive);
    }

    public static void setMinimumBlockLight (int value) {
        value = clamp(value, MIN_LIGHT, maxBlockLight);
        minBlockLight = value;
    }

    public static int getMinimumBlockLight () {
        return minBlockLight;
    }

    public static void setMaximumBlockLight (int value) {
        value = clamp(value, minBlockLight, MAX_LIGHT);
        maxBlockLight = value;
    }

    public static int getMaximumBlockLight () {
        return maxBlockLight;
    }

    public static void setMinimumSkyLight (int value) {
        value = clamp(value, MIN_LIGHT, maxSkyLight);
        minSkyLight = value;
    }

    public static int getMinimumSkyLight () {
        return minSkyLight;
    }

    public static void setMaximumSkyLight (int value) {
        value = clamp(value, minSkyLight, MAX_LIGHT);
        maxSkyLight = value;
    }

    public static int getMaximumSkyLight () {
        return maxSkyLight;
    }

    public static void setMinimumEntityLight (int value) {
        value = clamp(value, MIN_LIGHT, maxEntityLight);
        minEntityLight = value;
    }

    public static int getMinimumEntityLight () {
        return minEntityLight;
    }
    public static void setMaximumEntityLight (int value) {
        value = clamp(value, minEntityLight, MAX_LIGHT);
        maxEntityLight = value;
    }

    public static int getMaximumEntityLight () {
        return maxEntityLight;
    }

    public static int getLightingValue (LightType lightType, int trueLightLevel) {
        int min = 0;
        int max = 0;
        switch (lightType) {
            case ENTITY:
                min = getMinimumEntityLight();
                max = getMaximumEntityLight();
                break;
            
            case BLOCK:
                min = getMinimumBlockLight();
                max = getMaximumBlockLight();
                break;
            
            case SKY:
                min = getMinimumSkyLight();
                max = getMaximumSkyLight();
                break;
        
            default:
                break;
        }

        if (shouldScaleLighting) return scale(trueLightLevel, min, max);
        else return clamp(trueLightLevel, min, max);
    }

    public static float modifySkyBrightness (float currentBrightness) {
        if (!LightingManager.shouldScaleSkyBrightness) return currentBrightness;
        int outOf15 = Mth.lerpInt(currentBrightness, 0, 15);
        float modified = LightingManager.getLightingValue(LightType.SKY, outOf15);
        return modified / 15f;
    }

    public static void save () {
        try {
            SERIALISER.saveToFile(FILE);
        }
        catch (IOException e) {
            ClientMain.LOGGER.error("Failed to write Dynamic Fullbright JSON config: ", e);
        }
    }

    public static void load () {
        try {
            JsonReader reader = JsonReader.file(FILE);
            if (reader != null) SERIALISER.loadFromJson(reader);
            else save();
        }
        catch (FileNotFoundException e) {
            ClientMain.LOGGER.warn("No Dynamic Fullbright config file found, creating new file...");
            save();
        }
        catch (Exception e) {
            ClientMain.LOGGER.error("Encountered error while parsing config:", e);
        }
    }

    public enum LightType {
        ENTITY,
        BLOCK,
        SKY
    }

    private static int clamp (int value, int min, int max) {
        if (value > max) value = max;
        else if (value < min) value = min;
        return value;
    }

    private static int scale (int value, int min, int max) {
        return (int)(((double)value / (double)MAX_LIGHT) * (double)((max - min))) + min;
    }
}
