package xyz.devcomp.elytralock.mixin;

import java.util.function.Consumer;

import xyz.devcomp.elytralock.ElytraLock;
import xyz.devcomp.elytralock.util.RunOnceOnToggle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private static RunOnceOnToggle<String> logOnce = new RunOnceOnToggle<String>(
            new Consumer<String>() {
                public void accept(String msg) {
                    ElytraLock.LOGGER.info(msg);
                }
            });

    @Inject(method = "checkGliding()Z", at = @At("HEAD"), cancellable = true)
    private void preventFallFlying(CallbackInfoReturnable<Boolean> info) {
        var config = ElytraLock.config.getInstance();
        if (config.preventFallFlying && logOnce.run("Elytra is locked, so preventing fall flying"))
            info.setReturnValue(false);
    }
}