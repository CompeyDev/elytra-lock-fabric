package xyz.devcomp.elytralock.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStopping;
import net.minecraft.client.MinecraftClient;
import xyz.devcomp.elytralock.ElytraLock;
import xyz.devcomp.elytralock.config.ConfigHandler;

public class ClientExitHandler implements ClientStopping {
    @Override
    public void onClientStopping(MinecraftClient client) {
        ElytraLock.config.getInstance().toggle = ElytraLock.isLocked();
        ConfigHandler.saveConfig();
    }
}
