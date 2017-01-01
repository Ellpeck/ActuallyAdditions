/*
 * This file ("RecipeKeepDataShapeless.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeKeepDataShapeless extends ShapelessOreRecipe{

    private final ItemStack nbtCopyStack;

    public RecipeKeepDataShapeless(ItemStack result, ItemStack nbtCopyStack, Object... recipe){
        super(result, recipe);
        this.nbtCopyStack = nbtCopyStack;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory){
        ItemStack stack = super.getCraftingResult(inventory);
        if(StackUtil.isValid(stack)){
            for(int i = 0; i < inventory.getSizeInventory(); i++){
                ItemStack input = inventory.getStackInSlot(i);
                if(ItemUtil.areItemsEqual(this.nbtCopyStack, input, true)){
                    stack.setTagCompound(input.getTagCompound());
                    break;
                }
            }
        }
        return stack;
    }

}
