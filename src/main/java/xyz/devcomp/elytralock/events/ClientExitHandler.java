package xyz.devcomp.elytralock.events;

import xyz.devcomp.elytralock.ElytraLock;
import xyz.devcomp.elytralock.config.ConfigHandler;

import net.minecraft.client.Minecraft;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStopping;

import org.jetbrains.annotations.NotNull;

public class ClientExitHandler implements ClientStopping {
    @Override
    public void onClientStopping(@NotNull Minecraft client) {
        ElytraLock.config.getInstance().toggle = ElytraLock.isLocked();
        ConfigHandler.saveConfig();
    }
}
