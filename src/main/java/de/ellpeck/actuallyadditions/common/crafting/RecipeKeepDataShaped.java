package de.ellpeck.actuallyadditions.crafting;

import de.ellpeck.actuallyadditions.util.ItemUtil;
import de.ellpeck.actuallyadditions.util.StackUtil;
import de.ellpeck.actuallyadditions.util.crafting.RecipeHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeKeepDataShaped extends ShapedOreRecipe {

    private final ItemStack nbtCopyStack;

    public RecipeKeepDataShaped(ResourceLocation group, ItemStack result, ItemStack nbtCopyStack, Object... recipe) {
        super(group, result, recipe);
        this.nbtCopyStack = nbtCopyStack;

        RecipeHelper.addRecipe(group.getPath(), this);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        ItemStack stack = super.getCraftingResult(inventory);
        if (StackUtil.isValid(stack)) {
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack input = inventory.getStackInSlot(i);
                if (ItemUtil.areItemsEqual(this.nbtCopyStack, input, true)) {
                    stack.setTagCompound(input.getTagCompound());
                    break;
                }
            }
        }
        return stack;
    }

}
