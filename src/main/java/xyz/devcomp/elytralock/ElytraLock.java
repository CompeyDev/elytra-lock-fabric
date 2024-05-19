package xyz.devcomp.elytralock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

public class ElytraLock implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Elytra Lock");
	
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		LOGGER.info("Elytra lock initializing!");
	}

	public static boolean isLocked() {
		// TODO: Load from config
		return true;
	}
}