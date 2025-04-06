package com.provismet.dynamicFB.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.provismet.dynamicFB.LightingManager;

import net.minecraft.client.render.WorldRenderer;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @ModifyVariable(method = "getLightmapCoordinates(Lnet/minecraft/client/render/WorldRenderer$BrightnessGetter;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I", at = @At("STORE"), ordinal = 0)
    private static int adjustBlockLight (int packedLight) {
        if (LightingManager.isActive()) {
            int blockLight = LightingManager.getLightingValue(LightingManager.LightType.BLOCK, LightmapTextureManager.getBlockLightCoordinates(packedLight));
            int skyLight = LightingManager.getLightingValue(LightingManager.LightType.SKY, LightmapTextureManager.getSkyLightCoordinates(packedLight));
            return LightmapTextureManager.pack(blockLight, skyLight);
        }
        return packedLight;
    }
}
