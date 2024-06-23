package xyz.devcomp.elytralock.config;

import xyz.devcomp.elytralock.ElytraLock;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

public class ConfigHandler {
    private boolean isLoaded = false;
    public static final ConfigClassHandler<ConfigModel> HANDLER = ConfigClassHandler.createBuilder(ConfigModel.class)
            .id(Identifier.of("elytralock", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(ElytraLock.LOADER.getConfigDir().resolve("elytra-lock.json"))
                    .setJson5(true)
                    .build())
            .build();

    private void loadConfig() {
        if (!this.isLoaded) {
            ElytraLock.LOGGER.info("ElytraLock config not loaded, loading");
            this.isLoaded = HANDLER.load();
        }
    }

    public Screen showGui(Screen parent) {
        this.loadConfig();
        return HANDLER.generateGui().generateScreen(parent);
    }

    public ConfigModel getInstance() {
        this.loadConfig();
        return HANDLER.instance();
    }
}