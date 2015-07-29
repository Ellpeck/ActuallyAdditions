package ellpeck.actuallyadditions.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ellpeck.actuallyadditions.inventory.gui.GuiGrinder;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrusherRecipeHandler extends TemplateRecipeHandler{

    public static class CrusherDoubleRecipeHandler extends CrusherRecipeHandler{

        @Override
        public Class<? extends GuiContainer> getGuiClass(){
            return GuiGrinder.GuiGrinderDouble.class;
        }

        @Override
        public void loadTransferRects(){
            transferRects.add(new RecipeTransferRect(new Rectangle(51, 40, 24, 22), this.getName()));
            transferRects.add(new RecipeTransferRect(new Rectangle(101, 40, 24, 22), this.getName()));
        }
    }

    public CrusherRecipeHandler(){
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    public class CachedCrush extends CachedRecipe{

        public PositionedStack ingredient;
        public PositionedStack resultOne;
        public PositionedStack resultTwo;
        public int secondChance;

        public CachedCrush(ItemStack in, ItemStack resultOne, ItemStack resultTwo, int secondChance){
            in.stackSize = 1;
            this.ingredient = new PositionedStack(in, 80, 21);
            this.resultOne = new PositionedStack(resultOne, 66, 69);
            if(resultTwo != null) this.resultTwo = new PositionedStack(resultTwo, 94, 69);
            this.secondChance = secondChance;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return getCycledIngredients(cycleticks/48, Collections.singletonList(ingredient));
        }

        @Override
        public PositionedStack getResult(){
            return resultOne;
        }

        @Override
        public List<PositionedStack> getOtherStacks(){
            ArrayList<PositionedStack> list = new ArrayList<PositionedStack>();
            if(this.resultTwo != null) list.add(this.resultTwo);
            return list;
        }
    }

    @Override
    public int recipiesPerPage(){
        return 1;
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(80, 40, 24, 22), this.getName()));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiGrinder.class;
    }

    @Override
    public String getRecipeName(){
        return StatCollector.translateToLocal("container.nei."+this.getName()+".name");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(this.getName()) && (getClass() == CrusherRecipeHandler.class || getClass() == CrusherDoubleRecipeHandler.class)){
            ArrayList<CrusherRecipeManualRegistry.CrusherRecipe> recipes = CrusherRecipeManualRegistry.recipes;
            for(CrusherRecipeManualRegistry.CrusherRecipe recipe : recipes){
                arecipes.add(new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance));
            }
        }
        else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        ArrayList<CrusherRecipeManualRegistry.CrusherRecipe> recipes = CrusherRecipeManualRegistry.recipes;
        for(CrusherRecipeManualRegistry.CrusherRecipe recipe : recipes){
            if(NEIServerUtils.areStacksSameType(recipe.firstOutput, result) || NEIServerUtils.areStacksSameType(recipe.secondOutput, result))
                arecipes.add(new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        ArrayList<CrusherRecipeManualRegistry.CrusherRecipe> recipes = CrusherRecipeManualRegistry.recipes;
        for(CrusherRecipeManualRegistry.CrusherRecipe recipe : recipes){
            if(NEIServerUtils.areStacksSameTypeCrafting(recipe.input, ingredient)){
                CachedCrush theRecipe = new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance);
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.ingredient), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID_LOWER+":textures/gui/guiGrinder.png";
    }

    @Override
    public void drawBackground(int recipeIndex){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(60, 13, 60, 13, 56, 79);
    }

    @Override
    public void drawExtras(int recipe){
        drawProgressBar(80, 40, 176, 0, 24, 23, 48, 1);

        CachedCrush crush = (CachedCrush)this.arecipes.get(recipe);
        if(crush.resultTwo != null){
            int secondChance = crush.secondChance;
            String secondString = secondChance+"%";
            GuiDraw.drawString(secondString, 118, 73, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
    }

    @Override
    public String getOverlayIdentifier(){
        return this.getName();
    }

    protected String getName(){
        return "actuallyadditions."+(this instanceof CrusherDoubleRecipeHandler ? "crushingDouble" : "crushing");
    }
}