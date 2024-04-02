package de.ellpeck.actuallyadditions.mixin;

import de.ellpeck.actuallyadditions.mod.items.ItemTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilMenu.class)
public class AnvilMixin{
    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    public void pickupMixin(Player pPlayer, boolean pHasStack, CallbackInfoReturnable<Boolean> cir) {
        AnvilMenu anvilMenu = (AnvilMenu) (Object) this;
        if (((ItemCombinerMenuAccessor)anvilMenu).getInputSlots().getItem(0).getItem() instanceof ItemTag) {
            cir.setReturnValue(true);
        }
    }
}
