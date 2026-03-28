package xyz.devcomp.elytralock.events;

import java.util.function.Consumer;

import xyz.devcomp.elytralock.ElytraLock;
import xyz.devcomp.elytralock.util.RunOnceOnToggle;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;

public class ClientTickEndHandler implements EndTick {
    private static final RunOnceOnToggle<Minecraft> impl = new RunOnceOnToggle<Minecraft>(
            new Consumer<Minecraft>() {
                public void accept(Minecraft client) {
                    ItemStack chestArmor = client.player.getItemBySlot(EquipmentSlot.CHEST);
                    if (chestArmor.is(Items.ELYTRA)) {
                        ElytraLock.LOGGER.info("Detected player wearing elytra even though it's locked");
                    }
                }
            });

    public void onEndTick(Minecraft client) {
        if (client.player != null) {
            impl.run(client);

            // Update the config state to reflect the real lock state
            var instance = ElytraLock.config.getInstance();
            if (ElytraLock.lockKeybind.consumeClick()) {
                instance.toggle = !instance.toggle;
            }
        }
    }
}
