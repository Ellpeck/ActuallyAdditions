/*
 * This file ("TileEntityGiantChest.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;


import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

import java.util.Random;

public class TileEntityGiantChest extends TileEntityInventoryBase implements IButtonReactor, ILootContainer{

    public ResourceLocation lootTable;

    public TileEntityGiantChest(int slotAmount, String name){
        super(slotAmount, name);
    }

    public TileEntityGiantChest(){
        this(9*13, "giantChest");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);

        if(this.lootTable != null){
            compound.setString("LootTable", this.lootTable.toString());
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);

        if(compound.hasKey("LootTable")){
            this.lootTable = new ResourceLocation(compound.getString("LootTable"));
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return true;
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(player != null && this.pos != null){
            GuiHandler.GuiTypes type;

            if(buttonID == 0){
                type = GuiHandler.GuiTypes.GIANT_CHEST;
            }
            else if(buttonID == 1){
                type = GuiHandler.GuiTypes.GIANT_CHEST_PAGE_2;
            }
            else{
                type = GuiHandler.GuiTypes.GIANT_CHEST_PAGE_3;
            }

            player.openGui(ActuallyAdditions.instance, type.ordinal(), this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ());
        }
    }

    @Override
    public ResourceLocation getLootTable(){
        return this.lootTable;
    }

    public void fillWithLoot(EntityPlayer player){
        if(this.lootTable != null && !this.worldObj.isRemote && this.worldObj instanceof WorldServer){
            LootTable table = this.worldObj.getLootTableManager().getLootTableFromLocation(this.lootTable);
            this.lootTable = null;

            LootContext.Builder builder = new LootContext.Builder((WorldServer)this.worldObj);
            if(player != null){
                builder.withLuck(player.getLuck());
            }
            table.fillInventory(this, new Random(), builder.build());
        }
    }
}
