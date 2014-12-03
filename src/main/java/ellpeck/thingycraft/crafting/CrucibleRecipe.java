package ellpeck.thingycraft.crafting;

import ellpeck.thingycraft.GemType;
import net.minecraft.item.ItemStack;

public class CrucibleRecipe{

    public final ItemStack[] recipeItems;
    public final ItemStack recipeOutput;
    public final GemType fluidNeeded;
    public final int processTimeNeeded;

    public CrucibleRecipe(ItemStack[] items, ItemStack output, GemType fluid, int processTimeNeeded){
        this.recipeItems = items;
        this.recipeOutput = output;
        this.fluidNeeded = fluid;
        this.processTimeNeeded = processTimeNeeded;
    }
}
