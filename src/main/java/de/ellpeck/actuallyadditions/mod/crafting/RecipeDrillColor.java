/*
 * This file ("RecipeDrillColor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

public class RecipeDrillColor implements IRecipe{

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn){
        boolean hasDrill = false;
        boolean hasColor = false;

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null && stack.getItem() != null){
                if(stack.getItem() instanceof ItemDrill){
                    if(!hasDrill){
                        hasDrill = true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    int[] ids = OreDictionary.getOreIDs(stack);
                    if(ids != null){
                        for(int id : ids){
                            String name = OreDictionary.getOreName(id);
                            if(name != null){
                                TheColoredLampColors color = TheColoredLampColors.getColorFromDyeName(name);
                                if(color != null){
                                    if(!hasColor){
                                        hasColor = true;
                                    }
                                    else{
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return hasDrill && hasColor;
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv){
        ItemStack drillStack = null;
        ItemStack colorStack = null;

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(stack != null && stack.getItem() != null){
                if(stack.getItem() instanceof ItemDrill){
                    drillStack = stack.copy();
                }
                else{
                    colorStack = stack.copy();
                }
            }
        }

        if(drillStack != null && colorStack != null){
            int[] ids = OreDictionary.getOreIDs(colorStack);
            if(ids != null){
                for(int id : ids){
                    String name = OreDictionary.getOreName(id);
                    if(name != null){
                        TheColoredLampColors color = TheColoredLampColors.getColorFromDyeName(name);
                        if(color != null){
                            if(color.ordinal() != drillStack.getItemDamage()){
                                drillStack.setItemDamage(color.ordinal());
                                return drillStack;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public int getRecipeSize(){
        return 2;
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput(){
        return null;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv){
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }
}
