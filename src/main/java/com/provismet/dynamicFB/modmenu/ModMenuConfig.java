package com.provismet.dynamicFB.modmenu;

import com.provismet.dynamicFB.ClientMain;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory () {
        if (ClientMain.hasCloth()) {
            return parent -> {
                return ConfigScreen.build(parent);
            };
        }
        else {
            return parent -> null;
        }
    }
}
