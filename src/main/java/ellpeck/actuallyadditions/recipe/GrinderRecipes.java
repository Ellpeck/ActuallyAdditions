package ellpeck.actuallyadditions.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class GrinderRecipes{

    private static final GrinderRecipes instance = new GrinderRecipes();

    public ArrayList<GrinderRecipe> recipes = new ArrayList<GrinderRecipe>();

    public static GrinderRecipes instance(){
        return instance;
    }

    public void registerRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int secondChance){
        this.recipes.add(new GrinderRecipe(input, outputOne, outputTwo, secondChance));
    }

    public void registerRecipe(String input, String outputOne, String outputTwo, int secondChance, int outputAmount){
        ArrayList<ItemStack> inputStacks = OreDictionary.getOres(input);
        ArrayList<ItemStack> outputOneStacks = OreDictionary.getOres(outputOne);
        ArrayList<ItemStack> outputTwoStacks = OreDictionary.getOres(outputTwo);

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
                                this.registerRecipe(theInput, theOutputOne, theOutputTwo, secondChance);
                            }
                        }
                        else this.registerRecipe(theInput, theOutputOne, null, 0);
                    }
                }
            }
        }
    }

    public void registerRecipe(ItemStack input, ItemStack outputOne){
        this.registerRecipe(input, outputOne, null, 0);
    }

    public ItemStack getOutput(ItemStack input, boolean wantSecond){
        for(GrinderRecipe recipe : recipes){
            if(recipe.input.isItemEqual(input)){
                return wantSecond ? recipe.secondOutput : recipe.firstOutput;
            }
        }
        return null;
    }

    public boolean hasRecipe(String input, String outputOne){
        boolean containsInput = false;
        boolean containsOutput = false;

        for(GrinderRecipe recipe : recipes){
            int[] recipeInputIDs = OreDictionary.getOreIDs(recipe.input);
            for(int recipeInputID : recipeInputIDs){
                if(input.equals(OreDictionary.getOreName(recipeInputID))){
                    containsInput = true;
                    break;
                }
            }

            int[] recipeOutputIDs = OreDictionary.getOreIDs(recipe.firstOutput);
            for(int recipeOutputID : recipeOutputIDs){
                if(outputOne.equals(OreDictionary.getOreName(recipeOutputID))){
                    containsOutput = true;
                    break;
                }
            }

            if(containsInput && containsOutput) break;
        }
        return containsInput && containsOutput;
    }

    public int getSecondChance(ItemStack input){
        for(GrinderRecipe recipe : recipes){
            if(recipe.input.isItemEqual(input)){
                return recipe.secondChance;
            }
        }
        return 0;
    }

    public class GrinderRecipe{

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