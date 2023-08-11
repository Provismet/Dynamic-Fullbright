package com.provismet.dynamicFB.mixin.fabric;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.provismet.dynamicFB.LightingManager;

import net.fabricmc.fabric.impl.client.indigo.renderer.aocalc.AoCalculator;

@Mixin(value = AoCalculator.class, priority = 999)
public abstract class AoCalculatorMixin {
    @ModifyVariable(at = @At("STORE"), method = "getLightmapCoordinates", ordinal = 1)
    private static int adjustBlockLight (int blockLight) {
        if (LightingManager.isActive()) {
            return LightingManager.getLightingValue(LightingManager.LightType.BLOCK, blockLight);
        }
        return blockLight;
    }

    @ModifyVariable(at = @At("STORE"), method = "getLightmapCoordinates", ordinal = 0)
    private static int adjustSkyLight (int skyLight) {
        if (LightingManager.isActive()) {
            return LightingManager.getLightingValue(LightingManager.LightType.SKY, skyLight);
        }
        return skyLight;
    }
}
