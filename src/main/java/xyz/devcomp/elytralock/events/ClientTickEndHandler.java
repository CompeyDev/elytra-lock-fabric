package xyz.devcomp.elytralock.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;    
import xyz.devcomp.elytralock.ElytraLock;

public class ClientTickEndHandler implements EndTick {
    private static boolean hasWarned = false;

    public void onEndTick(MinecraftClient client) {
        if (ElytraLock.isLocked()) {
            if (!hasWarned && client.isWindowFocused() && client.player != null) {
                PlayerInventory inventory = client.player.getInventory();
    
                // 0 -> boots 
                // 1 -> leggings
                // 2 -> chestplate
                // 3 -> helmet
                ItemStack chestArmor = inventory.armor.get(2);
                if (chestArmor.isOf(Items.ELYTRA)) {
                    ElytraLock.LOGGER.info("Detected player wearing elytra even though it's locked");
                    hasWarned = true;
                }
            }
        } else {
            hasWarned = false;
        }
    }
}
