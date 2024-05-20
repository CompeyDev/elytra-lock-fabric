package xyz.devcomp.elytralock;

import org.lwjgl.glfw.GLFW;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.networking.client.ClientPlayNetworkAddon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.SlotChangedStateC2SPacket;

public class ElytraLock implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Elytra Lock");
	private static KeyBinding lockKeybind;
	private static boolean locked = false;
	private static MinecraftClient client;

	@Override
	public void onInitializeClient() {
		lockKeybind = KeyBindingHelper.registerKeyBinding(
				new KeyBinding("key.elytralock.lock", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "category.elytralock"));
		client = MinecraftClient.getInstance();

		LOGGER.info("Elytra lock initializing!");
	}

	public static boolean isLocked() {
		if (lockKeybind.wasPressed()) {
			locked = !locked;
		}

		client.inGameHud.getChatHud().addMessage(Text.translatable("elytralock.chat.lockedMessage"));

		return locked;
	}
}