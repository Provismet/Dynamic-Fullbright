package com.provismet.dynamicFB.modmenu;

import com.provismet.dynamicFB.LightingManager;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class ConfigScreen {
    public static Screen build (Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create();
        builder.setParentScreen(parent);
        builder.setTitle(new TranslatableText("title.dynamicfullbright.config"));

        ConfigEntryBuilder entryBuilder  = builder.entryBuilder();
        ConfigCategory world = builder.getOrCreateCategory(new TranslatableText("category.dynamicfullbright.world"));

        world.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.dynamicfullbright.world.active"), LightingManager.isActive())
            .setDefaultValue(false)
            .setSaveConsumer(newValue -> LightingManager.setActive(newValue))
            .build()
        );

        world.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.dynamicfullbright.world.scaling"), LightingManager.shouldScaleLighting)
            .setDefaultValue(true)
            .setSaveConsumer(newValue -> LightingManager.shouldScaleLighting = newValue)
            .setTooltip(new TranslatableText("tooltip.dynamicfullbright.world.scaling"))
            .build()
        );
        
        world.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.dynamicfullbright.world.block.min"), LightingManager.getMinimumBlockLight(), 0, 15)
            .setDefaultValue(4)
            .setSaveConsumer(newValue -> LightingManager.setMinimumBlockLight(newValue))
            .build()
        );

        world.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.dynamicfullbright.world.block.max"), LightingManager.getMaximumBlockLight(), 0, 15)
            .setDefaultValue(15)
            .setSaveConsumer(newValue -> LightingManager.setMaximumBlockLight(newValue))
            .build()
        );

        world.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.dynamicfullbright.world.sky.min"), LightingManager.getMinimumSkyLight(), 0, 15)
            .setDefaultValue(4)
            .setSaveConsumer(newValue -> LightingManager.setMinimumSkyLight(newValue))
            .build()
        );

        /**
         * Somewhere in the MC source code there is an override for skylight rendering and that makes it impossible to upper-bound for the moment.
         * This block will remain commented out to avoid confusion for the end user.
        world.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.dynamicfullbright.world.sky.max"), LightingManager.getMaximumSkyLight(), 0, 15)
            .setDefaultValue(15)
            .setSaveConsumer(newValue -> LightingManager.setMaximumSkyLight(newValue))
            .build()
        );
        */

        ConfigCategory entities = builder.getOrCreateCategory(new TranslatableText("category.dynamicfullbright.entity"));
        entities.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.dynamicfullbright.entity.separate"), LightingManager.separateEntityLight)
            .setDefaultValue(false)
            .setSaveConsumer(newValue -> LightingManager.separateEntityLight = newValue)
            .setTooltip(new TranslatableText("tooltip.dynamicfullbright.entity.separate"))
            .build()
        );

        entities.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.dynamicfullbright.entity.min"), LightingManager.getMinimumEntityLight(true), 0, 15)
            .setDefaultValue(4)
            .setSaveConsumer(newValue -> LightingManager.setMinimumEntityLight(newValue))
            .build()
        );

        entities.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.dynamicfullbright.entity.max"), LightingManager.getMaximumEntityLight(true), 0, 15)
            .setDefaultValue(15)
            .setSaveConsumer(newValue -> LightingManager.setMaximumEntityLight(newValue))
            .build()
        );

        builder.setSavingRunnable(() -> {
            LightingManager.save();
        });
        return builder.build();
    }    
}
