package xyz.devcomp.elytralock.integrations;

import xyz.devcomp.elytralock.ElytraLock;
import xyz.devcomp.elytralock.config.ConfigUtil;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> {
            if (!ConfigUtil.isYaclLoaded())
                return parent;
            return ElytraLock.config.showGui(parent);
        };
    }
}
