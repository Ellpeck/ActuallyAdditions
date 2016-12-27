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
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

public class FilterSettings{

    public final int whitelistButtonId;
    public final int metaButtonId;
    public final int nbtButtonId;
    public final int oredictButtonId;
    public final int modButtonId;

    public final int startSlot;
    public final int endSlot;

    public boolean isWhitelist;
    public boolean respectMeta;
    public boolean respectNBT;
    public boolean respectMod;
    public int respectOredict;

    private boolean lastWhitelist;
    private boolean lastRespectMeta;
    private boolean lastRespectNBT;
    private boolean lastRespectMod;
    private int lastRecpectOredict;

    public FilterSettings(int startSlot, int endSlot, boolean defaultWhitelist, boolean defaultRespectMeta, boolean defaultRespectNBT, boolean defaultRespectMod, int defaultRespectOredict, int buttonIdStart){
        this.startSlot = startSlot;
        this.endSlot = endSlot;

        this.isWhitelist = defaultWhitelist;
        this.respectMeta = defaultRespectMeta;
        this.respectNBT = defaultRespectNBT;
        this.respectMod = defaultRespectMod;
        this.respectOredict = defaultRespectOredict;

        this.whitelistButtonId = buttonIdStart;
        this.metaButtonId = buttonIdStart+1;
        this.nbtButtonId = buttonIdStart+2;
        this.oredictButtonId = buttonIdStart+3;
        this.modButtonId = buttonIdStart+4;
    }

    public static boolean check(ItemStack stack, ItemStack[] slots, int startSlot, int endSlot, boolean whitelist, boolean meta, boolean nbt, boolean mod, int oredict){
        if(StackUtil.isValid(stack)){
            for(int i = startSlot; i < endSlot; i++){
                if(StackUtil.isValid(slots[i])){
                    if(areEqualEnough(slots[i], stack, meta, nbt, mod, oredict)){
                        return whitelist;
                    }
                    else if(slots[i].getItem() instanceof ItemFilter){
                        ItemStack[] filterSlots = new ItemStack[ContainerFilter.SLOT_AMOUNT];
                        ItemDrill.loadSlotsFromNBT(filterSlots, slots[i]);
                        if(filterSlots != null && filterSlots.length > 0){
                            for(ItemStack filterSlot : filterSlots){
                                if(StackUtil.isValid(filterSlot) && areEqualEnough(filterSlot, stack, meta, nbt, mod, oredict)){
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

    private static boolean areEqualEnough(ItemStack first, ItemStack second, boolean meta, boolean nbt, boolean mod, int oredict){
        Item firstItem = first.getItem();
        Item secondItem = second.getItem();
        if(mod){
            ResourceLocation firstReg = firstItem.getRegistryName();
            ResourceLocation secondReg = secondItem.getRegistryName();
            if(firstReg != null && secondReg != null){
                String firstDomain = firstReg.getResourceDomain();
                String secondDomain = secondReg.getResourceDomain();
                if(firstDomain != null && secondDomain != null){
                    if(!firstDomain.equals(secondDomain)){
                        return false;
                    }
                }
            }
        }
        else if(firstItem != secondItem){
            return false;
        }

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

    public void writeToNBT(NBTTagCompound tag, String name){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("Whitelist", this.isWhitelist);
        compound.setBoolean("Meta", this.respectMeta);
        compound.setBoolean("NBT", this.respectNBT);
        compound.setBoolean("Mod", this.respectMod);
        compound.setInteger("Oredict", this.respectOredict);
        tag.setTag(name, compound);
    }

    public void readFromNBT(NBTTagCompound tag, String name){
        NBTTagCompound compound = tag.getCompoundTag(name);
        this.isWhitelist = compound.getBoolean("Whitelist");
        this.respectMeta = compound.getBoolean("Meta");
        this.respectNBT = compound.getBoolean("NBT");
        this.respectMod = compound.getBoolean("Mod");
        this.respectOredict = compound.getInteger("Oredict");
    }

    public boolean needsUpdateSend(){
        return this.lastWhitelist != this.isWhitelist || this.lastRespectMeta != this.respectMeta || this.lastRespectNBT != this.respectNBT || this.lastRespectMod != this.respectMod || this.lastRecpectOredict != this.respectOredict;
    }

    public void updateLasts(){
        this.lastWhitelist = this.isWhitelist;
        this.lastRespectMeta = this.respectMeta;
        this.lastRespectNBT = this.respectNBT;
        this.lastRespectMod = this.respectMod;
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
        else if(id == this.modButtonId){
            this.respectMod = !this.respectMod;

            if(this.respectMod){
                this.respectMeta = false;
                this.respectNBT = false;
                this.respectOredict = 0;
            }
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
        return check(stack, slots, this.startSlot, this.endSlot, this.isWhitelist, this.respectMeta, this.respectNBT, this.respectMod, this.respectOredict);
    }
}
