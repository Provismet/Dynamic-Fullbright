package com.provismet.dynamicFB.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.provismet.dynamicFB.LightingManager;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getEndLightFlashManager()Lnet/minecraft/client/render/EndLightFlashManager;"))
    private void modifySky (float tickProgress, CallbackInfo info, @Local(ordinal = 1) LocalFloatRef skyLightFactor) {
        if (LightingManager.isActive()) skyLightFactor.set(LightingManager.modifySkyBrightness(skyLightFactor.get()));
    }
}
