/*
 * This file ("FilterSettings.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFilter;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.items.ItemFilter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FilterSettings{

    public final int startSlot;
    public final int endSlot;

    public boolean isWhitelist;
    private boolean lastWhitelist;

    public boolean respectMeta;
    private boolean lastRespectMeta;

    public boolean respectNBT;
    private boolean lastRespectNBT;

    public final int whitelistButtonId;
    public final int metaButtonId;
    public final int nbtButtonId;

    public FilterSettings(int startSlot, int endSlot, boolean defaultWhitelist, boolean defaultRespectMeta, boolean defaultRespectNBT, int buttonIdStart){
        this.startSlot = startSlot;
        this.endSlot = endSlot;

        this.isWhitelist = defaultWhitelist;
        this.respectMeta = defaultRespectMeta;
        this.respectNBT = defaultRespectNBT;

        this.whitelistButtonId = buttonIdStart;
        this.metaButtonId = buttonIdStart+1;
        this.nbtButtonId = buttonIdStart+2;
    }

    public void writeToNBT(NBTTagCompound tag, String name){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("Whitelist", this.isWhitelist);
        compound.setBoolean("Meta", this.respectMeta);
        compound.setBoolean("NBT", this.respectNBT);
        tag.setTag(name, compound);
    }

    public void readFromNBT(NBTTagCompound tag, String name){
        NBTTagCompound compound = tag.getCompoundTag(name);
        this.isWhitelist = compound.getBoolean("Whitelist");
        this.respectMeta = compound.getBoolean("Meta");
        this.respectNBT = compound.getBoolean("NBT");
    }

    public boolean needsUpdateSend(){
        return this.lastWhitelist != this.isWhitelist || this.lastRespectMeta != this.respectMeta || this.lastRespectNBT != this.respectNBT;
    }

    public void updateLasts(){
        this.lastWhitelist = this.isWhitelist;
        this.lastRespectMeta = this.respectMeta;
        this.lastRespectNBT = this.respectNBT;
    }

    public void onButtonPressed(int id){
        if(id == this.whitelistButtonId){
            this.isWhitelist = !this.isWhitelist;
        }
        else if(id == this.metaButtonId){
            this.respectMeta = !this.respectMeta;
        }
        else if(id == this.nbtButtonId){
            this.respectNBT = !this.respectNBT;
        }
    }

    public boolean check(ItemStack stack, ItemStack[] slots){
        return check(stack, slots, this.startSlot, this.endSlot, this.isWhitelist, this.respectMeta, this.respectNBT);
    }

    public static boolean check(ItemStack stack, ItemStack[] slots, int startSlot, int endSlot, boolean whitelist, boolean meta, boolean nbt){
        if(stack != null){
            for(int i = startSlot; i < endSlot; i++){
                if(slots[i] != null){
                    if(areEqualEnough(slots[i], stack, meta, nbt)){
                        return whitelist;
                    }
                    else if(slots[i].getItem() instanceof ItemFilter){
                        ItemStack[] filterSlots = new ItemStack[ContainerFilter.SLOT_AMOUNT];
                        ItemDrill.loadSlotsFromNBT(filterSlots, slots[i]);
                        if(filterSlots != null && filterSlots.length > 0){
                            for(ItemStack filterSlot : filterSlots){
                                if(filterSlot != null && areEqualEnough(filterSlot, stack, meta, nbt)){
                                    return whitelist;
                                }
                            }
                        }
                    }
                }
            }
        }
        return !whitelist;
    }

    private static boolean areEqualEnough(ItemStack first, ItemStack second, boolean meta, boolean nbt){
        if(first.getItem() != second.getItem()){
            return false;
        }
        else{
            boolean metaFine = !meta || first.getItemDamage() == second.getItemDamage();
            boolean nbtFine = !nbt || ItemStack.areItemStackTagsEqual(first, second);
            return metaFine && nbtFine;
        }
    }
}
