package com.provismet.dynamicFB.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.dynamicFB.LightingManager;

import net.minecraft.client.render.entity.EntityRenderer;

@Mixin(value = EntityRenderer.class, priority = 999)
public abstract class EntityRendererMixin {
    @Inject(at = @At("RETURN"), method = "getBlockLight", cancellable = true)
    private void changeLighting (CallbackInfoReturnable<Integer> cir) {
        if (LightingManager.isActive() && LightingManager.isEntityActive()) {
            int out = cir.getReturnValue();
            cir.setReturnValue(LightingManager.getLightingValue(LightingManager.LightType.ENTITY, out));
        }
    }
}
