package xyz.devcomp.elytralock;

import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import xyz.devcomp.elytralock.config.ConfigHandler;
import xyz.devcomp.elytralock.config.ConfigUtil;
import xyz.devcomp.elytralock.events.ClientExitHandler;
import xyz.devcomp.elytralock.events.ClientTickEndHandler;
import xyz.devcomp.elytralock.events.HudRenderHandler;

public class ElytraLock implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Elytra Lock");
	public static final FabricLoader LOADER = FabricLoader.getInstance();

	public static Minecraft client;
	public static ConfigHandler config;

	public static Category category = Category.register(Identifier.fromNamespaceAndPath("elytralock", "category.elytralock"));
	public static KeyMapping lockKeybind = new KeyMapping("key.elytralock.lock", GLFW.GLFW_KEY_J, category);

	@Override
	public void onInitializeClient() {
		LOGGER.info("ElytraLock initializing!");

		KeyMappingHelper.registerKeyMapping(lockKeybind);
		LOGGER.info("Registered keybind for locking elytra");

		client = Minecraft.getInstance();

		if (ConfigUtil.isYaclLoaded()) {
			LOGGER.info("YACL_v3 is loaded, loading elytra toggle");
			config = new ConfigHandler();
		} else {
			LOGGER.warn("YACL_v3 is not loaded, not persisting elytra toggle");
		}

		ClientTickEvents.END_CLIENT_TICK.register(new ClientTickEndHandler());
		ClientLifecycleEvents.CLIENT_STOPPING.register(new ClientExitHandler());
		HudRenderHandler.register();

		LOGGER.info("Registered END_CLIENT_TICK, CLIENT_STOPPING, and HUD render events successfully!");
	}

	public static boolean isLocked() {
		return config.getInstance().toggle;
	}
}