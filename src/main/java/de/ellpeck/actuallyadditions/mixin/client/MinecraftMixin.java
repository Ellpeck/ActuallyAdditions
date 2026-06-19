package de.ellpeck.actuallyadditions.mixin.client;

import de.ellpeck.actuallyadditions.mod.event.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	@Inject(method = "shouldEntityAppearGlowing(Lnet/minecraft/world/entity/Entity;)Z",
			at = @At("HEAD"), cancellable = true)
	public void armorposer$shouldEntityAppearGlowing(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		if (ClientEvents.shouldGlow(entity))
			cir.setReturnValue(true);
	}
}
