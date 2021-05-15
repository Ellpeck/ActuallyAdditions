package de.ellpeck.actuallyadditions.common.items.useables;

import de.ellpeck.actuallyadditions.common.container.DrillContainer;
import de.ellpeck.actuallyadditions.common.container.VoidSackContainer;
import de.ellpeck.actuallyadditions.common.items.ActuallyItem;
import de.ellpeck.actuallyadditions.common.utilities.Help;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class VoidSack extends ActuallyItem {
    public VoidSack() {
        super(baseProps());
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote()) {
            return ActionResult.resultPass(stack);
        }

        if (handIn == Hand.MAIN_HAND) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new SimpleNamedContainerProvider(
                (windowId, playerInv, playerEntity) -> new VoidSackContainer(windowId, playerInv, stack),
                Help.trans("gui.name.void_sack")
            ));
        }

        return ActionResult.resultSuccess(stack);
    }
}
