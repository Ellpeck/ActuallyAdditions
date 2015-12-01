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
import ellpeck.actuallyadditions.blocks.metalists.TheColoredLampColors;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheCrystals;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReconstructorRecipeHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public static ArrayList<Recipe> mainPageRecipes = new ArrayList<Recipe>();
    public static Recipe recipeColorLens;
    public static Recipe recipeSoulSand;
    public static Recipe recipeGreenWall;
    public static Recipe recipeWhiteWall;
    public static Recipe recipeExplosionLens;
    public static Recipe recipeDamageLens;
    public static Recipe recipeLeather;
    public static ArrayList<Recipe> colorConversionRecipes = new ArrayList<Recipe>();

    public static void init(){
        //Crystal Blocks
        addRecipe(new ItemStack(Blocks.redstone_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.REDSTONE.ordinal()), 400);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Blocks.lapis_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.LAPIS.ordinal()), 400);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Blocks.diamond_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()), 600);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Blocks.emerald_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.EMERALD.ordinal()), 1000);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Blocks.coal_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal()), 600);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Blocks.iron_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()), 800);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());

        //Crystal Items
        addRecipe(new ItemStack(Items.redstone), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 40);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Items.dye, 1, 4), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), 40);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Items.diamond), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), 60);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Items.emerald), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), 100);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Items.coal), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), 60);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe(new ItemStack(Items.iron_ingot), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 80);

        //Lenses
        addRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), new ItemStack(InitItems.itemColorLens), 5000);
        recipeColorLens = Util.GetRecipes.lastReconstructorRecipe();

        if(ConfigCrafting.RECONSTRUCTOR_EXPLOSION_LENS.isEnabled()){
            addRecipe(new ItemStack(InitItems.itemColorLens), new ItemStack(InitItems.itemExplosionLens), 5000);
            recipeExplosionLens = Util.GetRecipes.lastReconstructorRecipe();
            addRecipe(new ItemStack(InitItems.itemExplosionLens), new ItemStack(InitItems.itemDamageLens), 5000);
        }
        else{
            addRecipe(new ItemStack(InitItems.itemColorLens), new ItemStack(InitItems.itemDamageLens), 5000);
        }
        recipeDamageLens = Util.GetRecipes.lastReconstructorRecipe();

        addRecipe(new ItemStack(InitItems.itemDamageLens), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), 5000);

        //Misc
        if(ConfigCrafting.RECONSTRUCTOR_MISC.isEnabled()){
            addRecipe(new ItemStack(Blocks.sand), new ItemStack(Blocks.soul_sand), 20000);
            recipeSoulSand = Util.GetRecipes.lastReconstructorRecipe();
            addRecipe(new ItemStack(Items.rotten_flesh), new ItemStack(Items.leather), 8000);
            recipeLeather = Util.GetRecipes.lastReconstructorRecipe();
            addRecipe(new ItemStack(Blocks.quartz_block), new ItemStack(InitBlocks.blockTestifiBucksWhiteWall), 10);
            recipeWhiteWall = Util.GetRecipes.lastReconstructorRecipe();
            addRecipe(new ItemStack(Blocks.quartz_block), new ItemStack(InitBlocks.blockTestifiBucksGreenWall), 10, LensType.COLOR);
            recipeGreenWall = Util.GetRecipes.lastReconstructorRecipe();

            //Colors
            for(int i = 0; i < TheColoredLampColors.values().length-1; i++){
                addRecipe("dye"+TheColoredLampColors.values()[i].name, "dye"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
                addRecipe(new ItemStack(Blocks.wool, 1, i), new ItemStack(Blocks.wool, 1, i+1), 2000, LensType.COLOR);
                colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
                addRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, i), new ItemStack(Blocks.stained_hardened_clay, 1, i+1), 2000, LensType.COLOR);
                colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
                addRecipe("blockGlass"+TheColoredLampColors.values()[i].name, "blockGlass"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
            }
            addRecipe("dye"+TheColoredLampColors.values()[15].name, "dye"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
            addRecipe(new ItemStack(Blocks.wool, 1, 15), new ItemStack(Blocks.wool, 1, 0), 2000, LensType.COLOR);
            colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
            addRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, 15), new ItemStack(Blocks.stained_hardened_clay, 1, 0), 2000, LensType.COLOR);
            colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
            addRecipe("blockGlass"+TheColoredLampColors.values()[15].name, "blockGlass"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        }
    }

    public static void addRecipe(ItemStack input, ItemStack output, int energyUse){
        addRecipe(input, output, energyUse, LensType.NONE);
    }

    public static void addRecipe(ItemStack input, ItemStack output, int energyUse, LensType type){
        if(type.hasRecipes){
            recipes.add(new Recipe(input, output, energyUse, type));
        }
    }

    public static void addRecipe(String input, String output, int energyUse, LensType type){
        if(type.hasRecipes){
            recipes.add(new Recipe(input, output, energyUse, type));
        }
    }

    public static ArrayList<Recipe> getRecipes(ItemStack input){
        ArrayList<Recipe> possibleRecipes = new ArrayList<Recipe>();
        for(Recipe recipe : recipes){
            if(ItemUtil.contains(recipe.getInputs(), input, true)){
                possibleRecipes.add(recipe);
            }
        }
        return possibleRecipes;
    }

    public enum LensType{

        NONE(true),
        COLOR(true),
        DETONATION(false),
        JUST_DAMAGE(false);

        //Thanks to xdjackiexd for this, as I couldn't be bothered
        private static final float[][] possibleColorLensColors = {
                {158F, 43F, 39F}, //Red
                {234F, 126F, 53F}, //Orange
                {194F, 181F, 28F}, //Yellow
                {57F, 186F, 46F}, //Lime Green
                {54F, 75F, 24F}, //Green
                {99F, 135F, 210F}, //Light Blue
                {38F, 113F, 145F}, //Cyan
                {37F, 49F, 147F}, //Blue
                {126F, 52F, 191F}, //Purple
                {190F, 73F, 201F}, //Magenta
                {217F, 129F, 153F}, //Pink
                {86F, 51F, 28F}, //Brown
        };
        public ItemStack lens;
        public boolean hasRecipes;

        LensType(boolean hasRecipes){
            this.hasRecipes = hasRecipes;
        }

        public float[] getColor(){
            if(this == COLOR){
                float[] colors = possibleColorLensColors[Util.RANDOM.nextInt(possibleColorLensColors.length)];
                return new float[]{colors[0]/255F, colors[1]/255F, colors[2]/255F};
            }
            else if(this == DETONATION){
                return new float[]{158F/255F, 43F/255F, 39F/255F};
            }
            else if(this == JUST_DAMAGE){
                return new float[]{188F/255F, 222F/255F, 1F};
            }
            else{
                return new float[]{27F/255F, 109F/255F, 1F};
            }
        }

        public void setLens(Item lens){
            this.lens = new ItemStack(lens);
        }

        public int getDistance(){
            return this == DETONATION ? 30 : (this == JUST_DAMAGE ? 15 : 10);
        }
    }

    public static class Recipe{

        private String input;
        private String output;
        public int energyUse;
        public LensType type;

        private ItemStack inputStack;
        private ItemStack outputStack;

        public Recipe(ItemStack input, ItemStack output, int energyUse, LensType type){
            this.inputStack = input;
            this.outputStack = output;
            this.energyUse = energyUse;
            this.type = type;
        }

        public Recipe(String input, String output, int energyUse, LensType type){
            this.input = input;
            this.output = output;
            this.energyUse = energyUse;
            this.type = type;
        }

        public List<ItemStack> getOutputs(){
            if(this.outputStack != null){
                return Collections.singletonList(this.outputStack.copy());
            }

            if(this.output == null || this.output.isEmpty()){
                return null;
            }

            List<ItemStack> stacks = OreDictionary.getOres(this.output, false);
            for(ItemStack stack : stacks){
                stack.stackSize = 1;
            }
            return stacks;
        }

        public List<ItemStack> getInputs(){
            if(this.inputStack != null){
                return Collections.singletonList(this.inputStack.copy());
            }

            if(this.input == null || this.input.isEmpty()){
                return null;
            }

            List<ItemStack> stacks = OreDictionary.getOres(this.input, false);
            for(ItemStack stack : stacks){
                stack.stackSize = 1;
            }
            return stacks;
        }
    }

}
