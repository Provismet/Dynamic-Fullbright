package com.provismet.dynamicFB.mixin.sodium;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.dynamicFB.LightingManager;

import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;

@Pseudo
@Mixin(value = LightDataAccess.class, priority = 999)
public abstract class LightDataAccessMixin {
    @Inject(at=@At("RETURN"), method="unpackBL(I)I", cancellable=true, remap=false)
    private static void scaleBlockLight (CallbackInfoReturnable<Integer> cir) {
        if (LightingManager.isActive()) {
            cir.setReturnValue(LightingManager.getLightingValue(LightingManager.LightType.BLOCK, cir.getReturnValueI()));
        }
    }

    @Inject(at=@At("RETURN"), method="unpackSL(I)I", cancellable=true, remap=false)
    private static void scaleSkyLight (CallbackInfoReturnable<Integer> cir) {
        if (LightingManager.isActive()) {
            cir.setReturnValue(LightingManager.getLightingValue(LightingManager.LightType.SKY, cir.getReturnValueI()));
        }
    }
}
