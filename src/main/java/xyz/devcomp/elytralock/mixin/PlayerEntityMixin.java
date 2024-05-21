package xyz.devcomp.elytralock.mixin;

import xyz.devcomp.elytralock.ElytraLock;

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
    @Inject(method = "checkFallFlying()Z", at = @At("HEAD"), cancellable = true)
    private void preventFallFlying(CallbackInfoReturnable<Boolean> info) {
        if (ElytraLock.isLocked()) {
            ElytraLock.LOGGER.info("Elytra is locked, so preventing fall flying");
            info.setReturnValue(false);
        }
    }
}