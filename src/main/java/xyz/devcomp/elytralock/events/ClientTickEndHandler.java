package xyz.devcomp.elytralock.events;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import xyz.devcomp.elytralock.ElytraLock;
import xyz.devcomp.elytralock.util.RunOnceOnToggle;

public class ClientTickEndHandler implements EndTick {
    private static RunOnceOnToggle<MinecraftClient> impl = new RunOnceOnToggle<MinecraftClient>(
            new Consumer<MinecraftClient>() {
                public void accept(MinecraftClient client) {
                    ItemStack chestArmor = client.player.getEquippedStack(EquipmentSlot.CHEST);
                    if (chestArmor.isOf(Items.ELYTRA)) {
                        ElytraLock.LOGGER.info("Detected player wearing elytra even though it's locked");
                    }
                }
            });

    public void onEndTick(MinecraftClient client) {
        if (client.player != null) {
            impl.run(client);
        }
    }
}
