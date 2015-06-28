package ellpeck.actuallyadditions.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class GrinderRecipeManualRegistry{

    public static ArrayList<GrinderRecipe> recipes = new ArrayList<GrinderRecipe>();

    public static void registerRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int secondChance){
        recipes.add(new GrinderRecipe(input, outputOne, outputTwo, secondChance));
    }

    public static void registerRecipe(String input, String outputOne, String outputTwo, int secondChance, int outputAmount){
        ArrayList<ItemStack> inputStacks = (ArrayList<ItemStack>)OreDictionary.getOres(input, false);
        ArrayList<ItemStack> outputOneStacks = (ArrayList<ItemStack>)OreDictionary.getOres(outputOne, false);
        ArrayList<ItemStack> outputTwoStacks = (ArrayList<ItemStack>)OreDictionary.getOres(outputTwo, false);

        if(inputStacks != null && !inputStacks.isEmpty()){
            for(ItemStack anInput : inputStacks){
                ItemStack theInput = anInput.copy();
                if(outputOneStacks != null && !outputOneStacks.isEmpty()){
                    for(ItemStack anOutputOne : outputOneStacks){
                        ItemStack theOutputOne = anOutputOne.copy();
                        theOutputOne.stackSize =  outputAmount;
                        if(outputTwoStacks != null && !outputTwoStacks.isEmpty()){
                            for(ItemStack anOutputTwo : outputTwoStacks){
                                ItemStack theOutputTwo = anOutputTwo.copy();
                                registerRecipe(theInput, theOutputOne, theOutputTwo, secondChance);
                            }
                        }
                        else registerRecipe(theInput, theOutputOne, null, 0);
                    }
                }
            }
        }
    }

    public static void registerRecipe(ItemStack input, ItemStack outputOne){
        registerRecipe(input, outputOne, null, 0);
    }

    public static ItemStack getOutput(ItemStack input, boolean wantSecond){
        for(GrinderRecipe recipe : recipes){
            if(recipe.input.isItemEqual(input)){
                return wantSecond ? recipe.secondOutput : recipe.firstOutput;
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input, ItemStack outputOne){
        for(GrinderRecipe recipe : recipes){
            if(recipe.input.isItemEqual(input) && recipe.firstOutput.isItemEqual(outputOne)) return true;
        }
        return false;
    }

    public static int getSecondChance(ItemStack input){
        for(GrinderRecipe recipe : recipes){
            if(recipe.input.isItemEqual(input)){
                return recipe.secondChance;
            }
        }
        return 0;
    }

    public static class GrinderRecipe{

        public final ItemStack input;
        public final ItemStack firstOutput;
        public final ItemStack secondOutput;
        public final int secondChance;

        public GrinderRecipe(ItemStack input, ItemStack firstOutput, ItemStack secondOutput, int secondChance){
            this.input = input;
            this.firstOutput = firstOutput;
            this.secondOutput = secondOutput;
            this.secondChance = secondChance;
        }

    }
}