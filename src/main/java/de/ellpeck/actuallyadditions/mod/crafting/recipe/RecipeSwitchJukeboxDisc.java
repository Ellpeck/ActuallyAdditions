/*
 * This file ("RecipeSwitchJukeboxDisc.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting.recipe;

import de.ellpeck.actuallyadditions.mod.items.ItemPortableJukebox;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeSwitchJukeboxDisc implements IRecipe{

    @Override
    public boolean matches(InventoryCrafting inv, World world){
        ItemStack juke = StackUtil.getNull();
        boolean foundDisc = false;

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(StackUtil.isValid(stack)){
                if(stack.getItem() instanceof ItemPortableJukebox){
                    if(!StackUtil.isValid(juke)){
                        juke = stack;
                    }
                    else{
                        return false;
                    }
                }
                else if(stack.getItem() instanceof ItemRecord){
                    if(!foundDisc){
                        foundDisc = true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
        }

        if(StackUtil.isValid(juke)){
            String disc = ItemPortableJukebox.getDisc(juke);
            return (disc == null || disc.isEmpty()) == foundDisc;
        }
        else{
            return false;
        }
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv){
        ItemStack disc = StackUtil.getNull();
        ItemStack juke = StackUtil.getNull();

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(StackUtil.isValid(stack)){
                if(stack.getItem() instanceof ItemPortableJukebox){
                    juke = stack.copy();
                }
                else{
                    disc = stack;
                }
            }
        }

        if(StackUtil.isValid(juke)){
            if(StackUtil.isValid(disc)){
                ResourceLocation reg = disc.getItem().getRegistryName();
                if(reg != null){
                    ItemPortableJukebox.setDisc(juke, reg.toString());
                }
            }
            else{
                String onJuke = ItemPortableJukebox.getDisc(juke);
                if(onJuke != null && !onJuke.isEmpty()){
                    Item itemOnJuke = Item.REGISTRY.getObject(new ResourceLocation(onJuke));
                    if(itemOnJuke != null){
                        disc = new ItemStack(itemOnJuke);
                    }
                }
                return disc;
            }
        }

        return juke;
    }

    @Override
    public int getRecipeSize(){
        return 5;
    }

    @Override
    public ItemStack getRecipeOutput(){
        return StackUtil.getNull();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv){
        NonNullList<ItemStack> remain = NonNullList.withSize(inv.getSizeInventory(), StackUtil.getNull());

        int jukePlace = -1;
        ItemStack juke = StackUtil.getNull();
        boolean foundDisc = false;

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(StackUtil.isValid(stack)){
                if(stack.getItem() instanceof ItemPortableJukebox){
                    juke = stack;
                    jukePlace = i;
                }
                else if(stack.getItem() instanceof ItemRecord){
                    foundDisc = true;
                    break;
                }
            }
        }

        if(!foundDisc && jukePlace >= 0 && StackUtil.isValid(juke)){
            ItemStack copy = juke.copy();
            ItemPortableJukebox.setDisc(copy, null);
            remain.set(jukePlace, copy);
        }

        return remain;
    }
}
