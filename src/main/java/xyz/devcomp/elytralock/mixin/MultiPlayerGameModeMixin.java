package xyz.devcomp.elytralock.mixin;

import xyz.devcomp.elytralock.ElytraLock;

import org.apache.commons.lang3.mutable.MutableObject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Unique
    private static final Component ELYTRA_LOCKED_MESSAGE = Component.translatable("elytralock.chat.lockedMessage",
            Component.translatable("elytralock.name").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD),
            Component.translatable("elytralock.state.locked").withStyle(ChatFormatting.ITALIC)
    );

    @Inject(method = "useItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At(value = "INVOKE", target = "net/minecraft/client/multiplayer/MultiPlayerGameMode.startPrediction (Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/multiplayer/prediction/PredictiveAction;)V"), cancellable = true)
    private void skipElytra(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info, @Local MutableObject<InteractionResult> mutableObject) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.ELYTRA) && ElytraLock.isLocked()) {
            ElytraLock.LOGGER.info("Skipping sending PlayerInteractItemC2SPacket for locked elytra");
            ElytraLock.client.gui.getChat().addClientSystemMessage(ELYTRA_LOCKED_MESSAGE);

            mutableObject.setValue(InteractionResult.FAIL);
            info.setReturnValue(mutableObject.get());
        }
    }
}
