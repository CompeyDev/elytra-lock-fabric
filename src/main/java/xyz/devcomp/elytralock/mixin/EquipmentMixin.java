package xyz.devcomp.elytralock.mixin;

import xyz.devcomp.elytralock.ElytraLock;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

@Mixin(Equipment.class)
@Environment(EnvType.CLIENT)
public interface EquipmentMixin {
	@Inject(method = "equipAndSwap(Lnet/minecraft/item/Item;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/entity/player/PlayerEntity.getStackInHand (Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
	private void injected(CallbackInfoReturnable<TypedActionResult<ItemStack>> info,
			@Local(ordinal = 0) ItemStack itemStack) {
		if (itemStack.isOf(Items.ELYTRA) && ElytraLock.isLocked()) {
			info.setReturnValue(TypedActionResult.fail(itemStack));
		}
	}
}