package ellpeck.someprettyrandomstuff.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class ContainerCrafter extends Container{

    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();

    public final int x;
    public final int y;
    public final int z;
    public final World world;

    public ContainerCrafter(EntityPlayer player){
        InventoryPlayer inventory = player.inventory;

        this.world = player.worldObj;
        this.x = (int)player.posX;
        this.y = (int)player.posY;
        this.z = (int)player.posZ;

        this.addSlotToContainer(new SlotCrafting(inventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i*3, 30 + j*18, 17 + i*18));
            }
        }

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j + i*9 + 9, 8 + j*18, 84 + i*18));
            }
        }
        for (int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8 + i*18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_){
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.world));
    }

    @Override
    public void onContainerClosed(EntityPlayer player){
        super.onContainerClosed(player);

        if (!this.world.isRemote){
            for (int i = 0; i < 9; ++i){
                ItemStack stack = this.craftMatrix.getStackInSlotOnClosing(i);
                if(stack != null) player.dropPlayerItemWithRandomChoice(stack, false);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        ItemStack stack = null;
        Slot theSlot = (Slot)this.inventorySlots.get(slot);

        if(theSlot != null && theSlot.getHasStack()){
            ItemStack savedStack = theSlot.getStack();
            stack = savedStack.copy();

            if(slot == 0){
                if(!this.mergeItemStack(savedStack, 10, 46, true)) return null;
                theSlot.onSlotChange(savedStack, stack);
            }
            else if(slot >= 10 && slot < 37 && !this.mergeItemStack(savedStack, 37, 46, false)) return null;
            else if(slot >= 37 && slot < 46 && !this.mergeItemStack(savedStack, 10, 37, false)) return null;
            else if(!this.mergeItemStack(savedStack, 10, 46, false)) return null;

            if(savedStack.stackSize == 0) theSlot.putStack(null);
            else theSlot.onSlotChanged();

            if(savedStack.stackSize == stack.stackSize) return null;

            theSlot.onPickupFromSlot(player, savedStack);
        }
        return stack;
    }
}