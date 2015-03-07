package ellpeck.actuallyadditions.inventory;

import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityGiantChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGiantChest extends Container{

    public TileEntityGiantChest tileChest;

    public ContainerGiantChest(InventoryPlayer inventory, TileEntityBase tile){
        this.tileChest = (TileEntityGiantChest)tile;

        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 13; j++){
                this.addSlotToContainer(new Slot(this.tileChest, j + (i*13), 5 + j * 18, 5 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 33 + 8 + j * 18, 244 + 4 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 33 + 8 + i * 18, 244 + 62));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileChest.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        return null;
    }
}