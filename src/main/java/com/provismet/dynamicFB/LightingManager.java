package com.provismet.dynamicFB;

public class LightingManager {
    private static final int MAX_LIGHT = 15;
    private static final int MIN_LIGHT = 0;

    public static boolean isActive = false;
    public static boolean separateEntityLight = false;
    public static boolean shouldScaleLighting = true;
    
    private static int minBlockLight = 4;
    private static int maxBlockLight = 15;
    private static int minEntityLight = 4;
    private static int maxEntityLight = 15;

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

    }

    public static void load () {

    }

    private static int clamp (int value, int min, int max) {
        if (value > max) value = max;
        else if (value < min) value = min;
        return value;
    }

    private static int scale (int value, int min, int max) {
        return 0;
    }
}
