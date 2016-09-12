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
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

public class FilterSettings{

    public final int startSlot;
    public final int endSlot;
    public final int whitelistButtonId;
    public final int metaButtonId;
    public final int nbtButtonId;
    public final int oredictButtonId;
    public boolean isWhitelist;
    public boolean respectMeta;
    public boolean respectNBT;
    public int respectOredict;
    private boolean lastWhitelist;
    private boolean lastRespectMeta;
    private boolean lastRespectNBT;
    private int lastRecpectOredict;

    public FilterSettings(int startSlot, int endSlot, boolean defaultWhitelist, boolean defaultRespectMeta, boolean defaultRespectNBT, int defaultRespectOredict, int buttonIdStart){
        this.startSlot = startSlot;
        this.endSlot = endSlot;

        this.isWhitelist = defaultWhitelist;
        this.respectMeta = defaultRespectMeta;
        this.respectNBT = defaultRespectNBT;
        this.respectOredict = defaultRespectOredict;

        this.whitelistButtonId = buttonIdStart;
        this.metaButtonId = buttonIdStart+1;
        this.nbtButtonId = buttonIdStart+2;
        this.oredictButtonId = buttonIdStart+3;
    }

    public static boolean check(ItemStack stack, ItemStack[] slots, int startSlot, int endSlot, boolean whitelist, boolean meta, boolean nbt, int oredict){
        if(stack != null){
            for(int i = startSlot; i < endSlot; i++){
                if(slots[i] != null){
                    if(areEqualEnough(slots[i], stack, meta, nbt, oredict)){
                        return whitelist;
                    }
                    else if(slots[i].getItem() instanceof ItemFilter){
                        ItemStack[] filterSlots = new ItemStack[ContainerFilter.SLOT_AMOUNT];
                        ItemDrill.loadSlotsFromNBT(filterSlots, slots[i]);
                        if(filterSlots != null && filterSlots.length > 0){
                            for(ItemStack filterSlot : filterSlots){
                                if(filterSlot != null && areEqualEnough(filterSlot, stack, meta, nbt, oredict)){
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

    private static boolean areEqualEnough(ItemStack first, ItemStack second, boolean meta, boolean nbt, int oredict){
        if(first.getItem() != second.getItem()){
            return false;
        }
        else{
            boolean metaFine = !meta || first.getItemDamage() == second.getItemDamage();
            boolean nbtFine = !nbt || ItemStack.areItemStackTagsEqual(first, second);
            if(metaFine && nbtFine){
                if(oredict == 0){
                    return true;
                }
                else{
                    int[] firstIds = OreDictionary.getOreIDs(first);
                    int[] secondIds = OreDictionary.getOreIDs(second);
                    boolean firstEmpty = ArrayUtils.isEmpty(firstIds);
                    boolean secondEmpty = ArrayUtils.isEmpty(secondIds);

                    //Both empty, meaning none has OreDict entries, so they are equal
                    if(firstEmpty && secondEmpty){
                        return true;
                    }
                    //Only one empty, meaning they are not equal
                    else if(firstEmpty || secondEmpty){
                        return false;
                    }
                    else{
                        for(int id : firstIds){
                            if(ArrayUtils.contains(secondIds, id)){
                                //Needs to match only one id, so return true on first match
                                if(oredict == 1){
                                    return true;
                                }
                            }
                            //Needs to match every id, so just return false when no match
                            else if(oredict == 2){
                                return false;
                            }

                        }
                        //If oredict mode 1, this will fail because nothing matched
                        //If oredict mode 2, this will mean nothing hasn't matched
                        return oredict == 2;
                    }
                }
            }
            else{
                return false;
            }
        }
    }

    public void writeToNBT(NBTTagCompound tag, String name){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("Whitelist", this.isWhitelist);
        compound.setBoolean("Meta", this.respectMeta);
        compound.setBoolean("NBT", this.respectNBT);
        compound.setInteger("Oredict", this.respectOredict);
        tag.setTag(name, compound);
    }

    public void readFromNBT(NBTTagCompound tag, String name){
        NBTTagCompound compound = tag.getCompoundTag(name);
        this.isWhitelist = compound.getBoolean("Whitelist");
        this.respectMeta = compound.getBoolean("Meta");
        this.respectNBT = compound.getBoolean("NBT");
        this.respectOredict = compound.getInteger("Oredict");
    }

    public boolean needsUpdateSend(){
        return this.lastWhitelist != this.isWhitelist || this.lastRespectMeta != this.respectMeta || this.lastRespectNBT != this.respectNBT || this.lastRecpectOredict != this.respectOredict;
    }

    public void updateLasts(){
        this.lastWhitelist = this.isWhitelist;
        this.lastRespectMeta = this.respectMeta;
        this.lastRespectNBT = this.respectNBT;
        this.lastRecpectOredict = this.respectOredict;
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
        else if(id == this.oredictButtonId){
            if(this.respectOredict+1 > 2){
                this.respectOredict = 0;
            }
            else{
                this.respectOredict++;
            }
        }
    }

    public boolean check(ItemStack stack, ItemStack[] slots){
        return check(stack, slots, this.startSlot, this.endSlot, this.isWhitelist, this.respectMeta, this.respectNBT, this.respectOredict);
    }
}
