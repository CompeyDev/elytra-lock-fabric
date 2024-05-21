package xyz.devcomp.elytralock.mixin;

import java.util.function.Consumer;

import xyz.devcomp.elytralock.ElytraLock;
import xyz.devcomp.elytralock.util.RunOnceOnToggle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;

// TODO: In the future, make fall flying prevention and elytra lock separate
// Fall flying prevention should be subset of elytra locking which should be 
// individually toggleable

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private static RunOnceOnToggle<String> logOnce = new RunOnceOnToggle<String>(
            new Consumer<String>() {
                public void accept(String msg) {
                    ElytraLock.LOGGER.info(msg);
                }
            });

    @Inject(method = "checkFallFlying()Z", at = @At("HEAD"), cancellable = true)
    private void preventFallFlying(CallbackInfoReturnable<Boolean> info) {
        if (logOnce.run("Elytra is locked, so preventing fall flying"))
            info.setReturnValue(false);
    }
}