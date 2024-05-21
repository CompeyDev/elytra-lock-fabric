package xyz.devcomp.elytralock.mixin;

import xyz.devcomp.elytralock.ElytraLock;

import org.apache.commons.lang3.mutable.MutableObject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "interactItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerInteractionManager.sendSequencedPacket (Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/network/SequencedPacketCreator;)V"), cancellable = true)
    private void skipElytra(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info, @Local MutableObject<ActionResult> mutableObject) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.ELYTRA) && ElytraLock.isLocked()) {
            ElytraLock.LOGGER.info("Skipping sending PlayerInteractItemC2SPacket for locked elytra");
            ElytraLock.client.inGameHud.getChatHud().addMessage(Text.translatable("elytralock.chat.lockedMessage"));

            mutableObject.setValue(ActionResult.FAIL);
            info.setReturnValue((ActionResult) mutableObject.getValue());
        }
    }
}
