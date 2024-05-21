package xyz.devcomp.elytralock.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import xyz.devcomp.elytralock.ElytraLock;

public class ClientTickEndHandler implements EndTick {
    public void onEndTick(MinecraftClient client) {
        if (client.isWindowFocused() && ElytraLock.isLocked() && client.player != null) {
            PlayerInventory inventory = client.player.getInventory();

            // 0 -> boots 
            // 1 -> leggings
            // 2 -> chestplate
            // 3 -> helmet
            ItemStack chestArmor = inventory.armor.get(2);

            if (chestArmor.isOf(Items.ELYTRA)) {
                ElytraLock.LOGGER.info("Detected player wearing elytra even though it's locked");
                ItemStack elytra = chestArmor.copyAndEmpty();
                int inventorySlot = inventory.getSwappableHotbarSlot();
                
                boolean ok = inventory.insertStack(inventorySlot, elytra);

                if (!ok) {
                    ElytraLock.LOGGER.warn("Failed to remove equipped elytra, which is locked");
                }
            }
        }
    }
}
