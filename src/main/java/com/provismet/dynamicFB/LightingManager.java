package com.provismet.dynamicFB;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

import net.minecraft.client.MinecraftClient;

public class LightingManager {
    private static final int MAX_LIGHT = 15;
    private static final int MIN_LIGHT = 0;
    
    private static final String FILENAME = "config/dynamic-fullbright.json";
    private static final String ENTITY_LIGHT = "separate entity lighting";
    private static final String SCALING = "scaling";
    private static final String MIN_BLOCK = "min block";
    private static final String MAX_BLOCK = "max block";
    private static final String MIN_ENTITY = "min entity";
    private static final String MAX_ENTITY = "max entity";

    private static boolean isActive = false;
    public static boolean separateEntityLight = false;
    public static boolean shouldScaleLighting = true;
    
    private static int minBlockLight = 4;
    private static int maxBlockLight = 15;
    private static int minEntityLight = 4;
    private static int maxEntityLight = 15;

    public static boolean isActive () {
        return isActive;
    }

    @SuppressWarnings("resource")
    public static void setActive (boolean value) {
        isActive = value;
        MinecraftClient.getInstance().worldRenderer.reload();
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

    public static void setMinimumEntityLight (int value) {
        value = clamp(value, MIN_LIGHT, maxEntityLight);
        minEntityLight = value;
    }

    public static int getMinimumEntityLight () {
        return separateEntityLight ? minEntityLight : minBlockLight;
    }

    public static void setMaximumEntityLight (int value) {
        value = clamp(value, minEntityLight, MAX_LIGHT);
        maxEntityLight = value;
    }

    public static int getMaximumEntityLight () {
        return separateEntityLight ? maxEntityLight : maxBlockLight;
    }

    public static int getLightingValue (boolean isEntity, int trueLightLevel) {
        int min = isEntity ? getMinimumEntityLight() : getMinimumBlockLight();
        int max = isEntity ? getMaximumEntityLight() : getMaximumBlockLight();

        if (shouldScaleLighting) return scale(trueLightLevel, min, max);
        else return clamp(trueLightLevel, min, max);
    }

    public static void save () {
        String json = String.format("{\n\t\"%s\": %b,\n\t\"%s\": %b,\n\t\"%s\": %d,\n\t\"%s\": %d,\n\t\"%s\": %d,\n\t\"%s\": %d\n}",
            ENTITY_LIGHT, separateEntityLight, SCALING, shouldScaleLighting, MIN_BLOCK, minBlockLight, MAX_BLOCK, maxBlockLight, MIN_ENTITY, minEntityLight, MAX_ENTITY, maxEntityLight);
        
        FileWriter writer;
        try {
            writer = new FileWriter(FILENAME);
            writer.write(json);
            writer.close();
        }
        catch (IOException e) {
            ClientMain.LOGGER.error("Failed to write JSON config: ", e);
        }
    }

    public static void load () {
        try {
            FileReader reader = new FileReader(FILENAME);
            JsonReader parser = new JsonReader(reader);

            parser.beginObject();

            while (parser.hasNext()) {
                String name = parser.nextName();
                switch (name) {
                    case ENTITY_LIGHT:
                        separateEntityLight = parser.nextBoolean();
                        break;
                    
                    case SCALING:
                        shouldScaleLighting = parser.nextBoolean();
                        break;
                    
                    case MIN_BLOCK:
                        setMinimumBlockLight(parser.nextInt());
                        break;
                    
                    case MAX_BLOCK:
                        setMaximumBlockLight(parser.nextInt());

                    case MIN_ENTITY:
                        setMinimumEntityLight(parser.nextInt());
                        break;
                    
                    case MAX_ENTITY:
                        setMaximumEntityLight(parser.nextInt());
                        break;
                
                    default:
                        ClientMain.LOGGER.warn("Illegal identifier '" + name + "' found in config.");
                        break;
                }
            }

            parser.close();
        }
        catch (FileNotFoundException e) {
            save();
        }
        catch (Exception e) {
            // Do nothing.
        }
    }

    private static int clamp (int value, int min, int max) {
        if (value > max) value = max;
        else if (value < min) value = min;
        return value;
    }

    private static int scale (int value, int min, int max) {
        return (int)(((double)value / 15.0) * (double)((max - min))) + min;
    }
}
