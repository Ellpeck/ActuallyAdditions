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

import ellpeck.actuallyadditions.blocks.metalists.TheColoredLampColors;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReconstructorRecipeHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public static ArrayList<Recipe> mainPageRecipes = new ArrayList<Recipe>();
    public static Recipe recipeColorLens;
    public static Recipe recipeSoulSand;
    public static Recipe recipeGreenWall;
    public static Recipe recipeWhiteWall;
    public static Recipe recipeExplosionLens;
    public static Recipe recipeDamageLens;
    public static ArrayList<Recipe> colorConversionRecipes = new ArrayList<Recipe>();

    public static void init(){
        //Crystal Blocks
        addRecipe("blockRedstone", "blockCrystalRed", 400);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("blockLapis", "blockCrystalBlue", 400);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("blockDiamond", "blockCrystalLightBlue", 6000);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("blockEmerald", "blockCrystalGreen", 10000);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("blockCoal", "blockCrystalBlack", 600);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("blockIron", "blockCrystalWhite", 800);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());

        //Crystal Items
        addRecipe("dustRedstone", "crystalRed", 40);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("gemLapis", "crystalBlue", 40);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("gemDiamond", "crystalLightBlue", 600);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("gemEmerald", "crystalGreen", 1000);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("coal", "crystalBlack", 60);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        addRecipe("ingotIron", "crystalWhite", 80);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());

        //Lenses
        addRecipe("itemLens", "itemColorLens", 5000);
        recipeColorLens = Util.GetRecipes.lastReconstructorRecipe();
        addRecipe("itemColorLens", "itemExplosionLens", 5000);
        recipeExplosionLens = Util.GetRecipes.lastReconstructorRecipe();
        addRecipe("itemExplosionLens", "itemDamageLens", 5000);
        recipeDamageLens = Util.GetRecipes.lastReconstructorRecipe();
        addRecipe("itemDamageLens", "itemLens", 5000);

        //Misc
        if(ConfigCrafting.RECONSTRUCTOR_MISC.isEnabled()){
            addRecipe("sand", "soulSand", 20000);
            recipeSoulSand = Util.GetRecipes.lastReconstructorRecipe();
            addRecipe("blockQuartz", "blockWhiteBrick", 10);
            recipeWhiteWall = Util.GetRecipes.lastReconstructorRecipe();
            addRecipe("blockQuartz", "blockGreenBrick", 10, LensType.COLOR);
            recipeGreenWall = Util.GetRecipes.lastReconstructorRecipe();

            //Colors
            for(int i = 0; i < TheColoredLampColors.values().length-1; i++){
                addRecipe("dye"+TheColoredLampColors.values()[i].name, "dye"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
                addRecipe("wool"+TheColoredLampColors.values()[i].name, "wool"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
                addRecipe("clay"+TheColoredLampColors.values()[i].name, "clay"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
                addRecipe("blockGlass"+TheColoredLampColors.values()[i].name, "blockGlass"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
            }
            addRecipe("dye"+TheColoredLampColors.values()[15].name, "dye"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
            addRecipe("wool"+TheColoredLampColors.values()[15].name, "wool"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
            addRecipe("clay"+TheColoredLampColors.values()[15].name, "clay"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
            addRecipe("blockGlass"+TheColoredLampColors.values()[15].name, "blockGlass"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            colorConversionRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        }
    }

    public static void addRecipe(String input, String output, int energyUse){
        addRecipe(input, output, energyUse, LensType.NONE);
    }

    public static void addRecipe(String input, String output, int energyUse, LensType type){
        if(type.hasRecipes){
            recipes.add(new Recipe(input, output, energyUse, type));
        }
    }

    public static ArrayList<Recipe> getRecipes(ItemStack input){
        ArrayList<Recipe> possibleRecipes = new ArrayList<Recipe>();
        for(Recipe recipe : recipes){
            int[] ids = OreDictionary.getOreIDs(input);
            for(int id : ids){
                if(Objects.equals(OreDictionary.getOreName(id), recipe.input)){
                    possibleRecipes.add(recipe);
                }
            }
        }
        return possibleRecipes;
    }

    public static class Recipe{

        public String input;
        public String output;
        public int energyUse;
        public LensType type;

        public Recipe(String input, String output, int energyUse, LensType type){
            this.input = input;
            this.output = output;
            this.energyUse = energyUse;
            this.type = type;
        }

        public ItemStack getFirstOutput(){
            List<ItemStack> stacks = OreDictionary.getOres(this.output, false);
            if(stacks != null && !stacks.isEmpty() && stacks.get(0) != null){
                ItemStack stack = stacks.get(0).copy();
                stack.stackSize = 1;
                return stack;
            }
            return null;
        }
    }

    public enum LensType{

        NONE(true),
        COLOR(true),
        DETONATION(false),
        JUST_DAMAGE(false);

        public ItemStack lens;
        public boolean hasRecipes;

        LensType(boolean hasRecipes){
            this.hasRecipes = hasRecipes;
        }

        public float[] getColor(){
            if(this == COLOR){
                float[] colors = AssetUtil.RGB_WOOL_COLORS[Util.RANDOM.nextInt(AssetUtil.RGB_WOOL_COLORS.length)];
                return new float[]{colors[0]/255F, colors[1]/255F, colors[2]/255F};
            }
            else if(this == DETONATION){
                return new float[]{158F/255F, 43F/255F, 39F/255F};
            }
            else if(this == JUST_DAMAGE){
                return new float[]{188F/255F, 222F/255F, 1F};
            }
            else return new float[]{27F/255F, 109F/255F, 1F};
        }

        public void setLens(Item lens){
            this.lens = new ItemStack(lens);
        }

        public int getDistance(){
            return this == DETONATION ? 30 : (this == JUST_DAMAGE ? 15 : 10);
        }
    }

}
