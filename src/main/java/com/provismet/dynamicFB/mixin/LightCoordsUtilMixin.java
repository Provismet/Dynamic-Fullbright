package com.provismet.dynamicFB.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.provismet.dynamicFB.LightingManager;

@Mixin(LightCoordsUtil.class)
public abstract class LightCoordsUtilMixin {
    @ModifyVariable(method = "getLightCoords(Lnet/minecraft/util/LightCoordsUtil$BrightnessGetter;Lnet/minecraft/world/level/BlockAndLightGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I", at = @At("STORE"), name = "packedBrightness")
    private static int adjustBlockLight (int packedBrightness, final LightCoordsUtil.BrightnessGetter brightnessGetter, final BlockAndLightGetter level, final BlockState state, final BlockPos pos) {
        if (LightingManager.isActive()) {
            int blockLight = LightingManager.getLightingValue(LightingManager.LightType.BLOCK, level.getBrightness(LightLayer.BLOCK, pos));
            int skyLight = LightingManager.getLightingValue(LightingManager.LightType.SKY, level.getBrightness(LightLayer.SKY, pos));
            return LightCoordsUtil.pack(blockLight, skyLight);
        }
        return packedBrightness;
    }
}
