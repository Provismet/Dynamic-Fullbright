package com.provismet.dynamicFB.modmenu;

import com.provismet.dynamicFB.LightingManager;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen {
    public static Screen build (Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create();
        builder.setParentScreen(parent);
        builder.setTitle(Text.translatable("title.dynamicfullbright.config"));

        ConfigEntryBuilder entryBuilder  = builder.entryBuilder();
        ConfigCategory world = builder.getOrCreateCategory(Text.translatable("category.dynamicfullbright.world"));

        world.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.dynamicfullbright.world.active"), LightingManager.isActive())
            .setDefaultValue(false)
            .setSaveConsumer(LightingManager::setActive)
            .setTooltip(Text.translatable("tooltip.dynamicfullbright.world.active"))
            .build()
        );

        world.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.dynamicfullbright.world.retain_active"), LightingManager.rememberActive)
            .setDefaultValue(false)
            .setSaveConsumer(newValue -> LightingManager.rememberActive = newValue)
            .setTooltip(Text.translatable("tooltip.dynamicfullbright.world.retain_active"))
            .build()
        );

        world.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.dynamicfullbright.world.scaling"), LightingManager.shouldScaleLighting)
            .setDefaultValue(true)
            .setSaveConsumer(newValue -> LightingManager.shouldScaleLighting = newValue)
            .setTooltip(Text.translatable("tooltip.dynamicfullbright.world.scaling"))
            .build()
        );
        
        world.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.dynamicfullbright.world.block.min"), LightingManager.getMinimumBlockLight(), 0, 15)
            .setDefaultValue(4)
            .setSaveConsumer(LightingManager::setMinimumBlockLight)
            .build()
        );

        world.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.dynamicfullbright.world.block.max"), LightingManager.getMaximumBlockLight(), 0, 15)
            .setDefaultValue(15)
            .setSaveConsumer(LightingManager::setMaximumBlockLight)
            .build()
        );

        world.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.dynamicfullbright.world.sky.min"), LightingManager.getMinimumSkyLight(), 0, 15)
            .setDefaultValue(4)
            .setSaveConsumer(LightingManager::setMinimumSkyLight)
            .build()
        );

        world.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.dynamicfullbright.world.sky.max"), LightingManager.getMaximumSkyLight(), 0, 15)
            .setDefaultValue(15)
            .setSaveConsumer(LightingManager::setMaximumSkyLight)
            .setTooltip(Text.translatable("tooltip.dynamicfullbright.world.sky"))
            .build()
        );

        world.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.dynamicfullbright.world.sky_scale"), LightingManager.shouldScaleSkyBrightness)
            .setDefaultValue(true)
            .setSaveConsumer(newValue -> LightingManager.shouldScaleSkyBrightness = newValue)
            .setTooltip(Text.translatable("tooltip.dynamicfullbright.world.sky_brightness"))
            .build()
        );

        ConfigCategory entities = builder.getOrCreateCategory(Text.translatable("category.dynamicfullbright.entity"));
        entities.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.dynamicfullbright.entity.separate"), LightingManager.separateEntityLight)
            .setDefaultValue(false)
            .setSaveConsumer(newValue -> LightingManager.separateEntityLight = newValue)
            .setTooltip(Text.translatable("tooltip.dynamicfullbright.entity.separate"))
            .build()
        );

        entities.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.dynamicfullbright.entity.min"), LightingManager.getMinimumEntityLight(), 0, 15)
            .setDefaultValue(4)
            .setSaveConsumer(LightingManager::setMinimumEntityLight)
            .build()
        );

        entities.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.dynamicfullbright.entity.max"), LightingManager.getMaximumEntityLight(), 0, 15)
            .setDefaultValue(15)
            .setSaveConsumer(LightingManager::setMaximumEntityLight)
            .build()
        );

        builder.setSavingRunnable(LightingManager::save);
        return builder.build();
    }    
}
