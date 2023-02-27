package com.provismet.dynamicFB.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.provismet.dynamicFB.LightingManager;

import net.minecraft.client.render.WorldRenderer;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @ModifyVariable(at = @At("STORE"), method = "getLightmapCoordinates(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I", ordinal = 2)
    private static int adjustBlockLight (int blockLight) {
        if (LightingManager.isActive()) {
            return LightingManager.getLightingValue(false, blockLight);
        }
        return blockLight;
    }
}
