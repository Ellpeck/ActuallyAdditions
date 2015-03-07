package ellpeck.actuallyadditions.recipe;

import net.minecraft.item.ItemStack;

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

    public ItemStack getOutput(ItemStack input, boolean wantSecond){
        for(GrinderRecipe recipe : recipes){
            if(recipe.input.isItemEqual(input)){
                return wantSecond ? recipe.secondOutput : recipe.firstOutput;
            }
        }
        return null;
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