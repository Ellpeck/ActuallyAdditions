/*
 * This file ("AtomicReconstructorRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.recipe;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.metalists.TheCrystals;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;

public class AtomicReconstructorRecipeHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public static void init(){
        addRecipe(Blocks.redstone_block, 0, InitBlocks.blockCrystal, TheCrystals.REDSTONE.ordinal());
        addRecipe(Blocks.lapis_block, 0, InitBlocks.blockCrystal, TheCrystals.LAPIS.ordinal());
        addRecipe(Blocks.diamond_block, 0, InitBlocks.blockCrystal, TheCrystals.DIAMOND.ordinal());
        addRecipe(Blocks.coal_block, 0, InitBlocks.blockCrystal, TheCrystals.COAL.ordinal());
        addRecipe(Blocks.emerald_block, 0, InitBlocks.blockCrystal, TheCrystals.EMERALD.ordinal());
    }

    public static void addRecipe(Block input, int inputMeta, Block output, int outputMeta){
        recipes.add(new Recipe(input, inputMeta, output, outputMeta));
    }

    public static Recipe getRecipe(Block input, int inputMeta){
        for(Recipe recipe : recipes){
            if(recipe.input == input && recipe.inputMeta == inputMeta){
                return recipe;
            }
        }
        return null;
    }

    public static class Recipe{

        public Block input;
        public int inputMeta;
        public Block output;
        public int outputMeta;

        public Recipe(Block input, int inputMeta, Block output, int outputMeta){
            this.input = input;
            this.inputMeta = inputMeta;
            this.output = output;
            this.outputMeta = outputMeta;
        }

    }

}
