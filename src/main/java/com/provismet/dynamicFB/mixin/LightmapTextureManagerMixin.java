package com.provismet.dynamicFB.mixin;

import com.provismet.dynamicFB.LightingManager;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapRenderStateExtractor.class)
public abstract class LightmapTextureManagerMixin {
    @Inject(method = "extract", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;endFlashState()Lnet/minecraft/client/renderer/EndFlashState;"))
    private void modifySky (LightmapRenderState renderState, float partialTicks, CallbackInfo info) {
        if (LightingManager.isActive()) renderState.skyFactor = LightingManager.modifySkyBrightness(renderState.skyFactor);
    }
}
