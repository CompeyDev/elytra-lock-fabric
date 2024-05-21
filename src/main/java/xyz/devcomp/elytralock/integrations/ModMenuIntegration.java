package xyz.devcomp.elytralock.integrations;

import xyz.devcomp.elytralock.config.ConfigHandler;
import xyz.devcomp.elytralock.config.Util;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> {
            if (!Util.isYaclLoaded())
                return parent;
            return new ConfigHandler().showGui(parent);
        };
    }
}
