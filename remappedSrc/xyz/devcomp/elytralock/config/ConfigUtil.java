package xyz.devcomp.elytralock.config;

import xyz.devcomp.elytralock.ElytraLock;

public class ConfigUtil {
    public static boolean isYaclLoaded() {
        return ElytraLock.LOADER.isModLoaded("yet_another_config_lib_v3");
    }
}
