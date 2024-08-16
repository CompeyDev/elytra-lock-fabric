package xyz.devcomp.elytralock;

import xyz.devcomp.elytralock.config.ConfigHandler;
import xyz.devcomp.elytralock.config.ConfigModel;
import xyz.devcomp.elytralock.config.ConfigUtil;
import xyz.devcomp.elytralock.events.ClientExitHandler;
import xyz.devcomp.elytralock.events.ClientTickEndHandler;
import xyz.devcomp.elytralock.events.HudRenderHandler;

import org.lwjgl.glfw.GLFW;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class ElytraLock implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Elytra Lock");
	public static final FabricLoader LOADER = FabricLoader.getInstance();
	private static KeyBinding lockKeybind;
	private static boolean locked = false;
	public static MinecraftClient client;
	public static ConfigHandler config;

	@Override
	public void onInitializeClient() {
		LOGGER.info("ElytraLock initializing!");

		lockKeybind = KeyBindingHelper.registerKeyBinding(
				new KeyBinding("key.elytralock.lock", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "category.elytralock"));
		LOGGER.info("Registered keybind for locking elytra");

		client = MinecraftClient.getInstance();

		if (ConfigUtil.isYaclLoaded()) {
			LOGGER.info("YACL_v3 is loaded, loading elytra toggle");
			config = new ConfigHandler();
			locked = config.getInstance().toggle;
		} else {
			LOGGER.warn("YACL_v3 is not loaded, not persisting elytra toggle");
		}

		HudRenderCallback.EVENT.register(new HudRenderHandler());
		ClientTickEvents.END_CLIENT_TICK.register(new ClientTickEndHandler());
		ClientLifecycleEvents.CLIENT_STOPPING.register(new ClientExitHandler());
		LOGGER.info("Registered HUD_RENDER, END_CLIENT_TICK and CLIENT_STOPPING events successfully!");
	}

	public static boolean isLocked() {
		if (lockKeybind.wasPressed()) {
			locked = !locked;
		}

		return locked;
	}
}