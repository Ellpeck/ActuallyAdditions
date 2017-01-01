/*
 * This file ("ContainerCrafter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;


import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;


public class ContainerCrafter extends Container{

    public final World world;
    public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public final IInventory craftResult = new InventoryCraftResult();

    public ContainerCrafter(EntityPlayer player){
        InventoryPlayer inventory = player.inventory;

        this.world = player.world;

        this.addSlotToContainer(new SlotCrafting(inventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.addSlotToContainer(new Slot(this.craftMatrix, j+i*3, 30+j*18, 17+i*18));
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 84+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index){
        ItemStack itemstack = StackUtil.getNull();
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(index == 0){
                if(!this.mergeItemStack(itemstack1, 10, 46, true)){
                    return StackUtil.getNull();
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(index >= 10 && index < 37){
                if(!this.mergeItemStack(itemstack1, 37, 46, false)){
                    return StackUtil.getNull();
                }
            }
            else if(index >= 37 && index < 46){
                if(!this.mergeItemStack(itemstack1, 10, 37, false)){
                    return StackUtil.getNull();
                }
            }
            else if(!this.mergeItemStack(itemstack1, 10, 46, false)){
                return StackUtil.getNull();
            }

            if(!StackUtil.isValid(itemstack1)){
                slot.putStack(StackUtil.getNull());
            }
            else{
                slot.onSlotChanged();
            }

            if(StackUtil.getStackSize(itemstack1) == StackUtil.getStackSize(itemstack)){
                return StackUtil.getNull();
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn){
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }

    @Override
    public void onContainerClosed(EntityPlayer player){
        super.onContainerClosed(player);

        if(!this.world.isRemote){
            for(int i = 0; i < 9; ++i){
                ItemStack stack = this.craftMatrix.removeStackFromSlot(i);
                if(StackUtil.isValid(stack)){
                    player.dropItem(stack, false);
                }
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inv){
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.world));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return true;
    }
}