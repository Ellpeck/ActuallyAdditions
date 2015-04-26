package ellpeck.actuallyadditions.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ellpeck.actuallyadditions.inventory.GuiGrinder;
import ellpeck.actuallyadditions.recipe.GrinderRecipes;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CrusherRecipeHandler extends TemplateRecipeHandler{

    public static final String NAME = "crushing";
    public static final String FUEL = "fuel";

    public static ArrayList<Fuel> fuels;

    public class CachedCrush extends CachedRecipe{

        public PositionedStack ingredient;
        public PositionedStack resultOne;
        public PositionedStack resultTwo;
        public int secondChance;

        public CachedCrush(ItemStack in, ItemStack resultOne, ItemStack resultTwo, int secondChance){
            in.stackSize = 1;
            this.ingredient = new PositionedStack(in, 7, 37);
            this.resultOne = new PositionedStack(resultOne, 60, 39);
            if(resultTwo != null) this.resultTwo = new PositionedStack(resultTwo, 86, 39);
            this.secondChance = secondChance;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return getCycledIngredients(cycleticks / 48, Collections.singletonList(ingredient));
        }

        @Override
        public PositionedStack getResult(){
            return resultOne;
        }

        @Override
        public PositionedStack getOtherStack(){
            return fuels.get((cycleticks / 48) % fuels.size()).stack;
        }

        @Override
        public List<PositionedStack> getOtherStacks(){
            ArrayList<PositionedStack> list = new ArrayList<PositionedStack>();
            list.add(this.getOtherStack());
            if(this.resultTwo != null) list.add(this.resultTwo);
            return list;
        }
    }

    public static class Fuel{

        public Fuel(ItemStack in, int burnTime){
            this.stack = new PositionedStack(in, 7, 3, false);
            this.burnTime = burnTime;
        }

        public PositionedStack stack;
        public int burnTime;
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(29, 3, 16, 16), FUEL));
        transferRects.add(new RecipeTransferRect(new Rectangle(29, 32, 22, 22), NAME));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiGrinder.class;
    }

    @Override
    public String getRecipeName(){
        return StatCollector.translateToLocal("container." + ModUtil.MOD_ID_LOWER + ".nei." + NAME + ".name");
    }

    @Override
    public TemplateRecipeHandler newInstance(){
        if (fuels == null || fuels.isEmpty()) findFuels();
        return super.newInstance();
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(NAME) && getClass() == CrusherRecipeHandler.class){
            ArrayList<GrinderRecipes.GrinderRecipe> recipes = GrinderRecipes.instance().recipes;
            for(GrinderRecipes.GrinderRecipe recipe : recipes){
                arecipes.add(new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance));
            }
        }
        else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        ArrayList<GrinderRecipes.GrinderRecipe> recipes = GrinderRecipes.instance().recipes;
        for(GrinderRecipes.GrinderRecipe recipe : recipes){
            if(NEIServerUtils.areStacksSameType(recipe.firstOutput, result) || NEIServerUtils.areStacksSameType(recipe.secondOutput, result)) arecipes.add(new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance));
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients){
        if (inputId.equals(FUEL) && getClass() == CrusherRecipeHandler.class) loadCraftingRecipes(NAME);
        else super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        ArrayList<GrinderRecipes.GrinderRecipe> recipes = GrinderRecipes.instance().recipes;
        for(GrinderRecipes.GrinderRecipe recipe : recipes){
            if(NEIServerUtils.areStacksSameTypeCrafting(recipe.input, ingredient)){
                CachedCrush theRecipe = new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance);
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.ingredient), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID_LOWER + ":textures/gui/nei/grinder.png";
    }

    @Override
    public void drawBackground(int recipeIndex){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 113, 66);
    }

    @Override
    public void drawExtras(int recipe){
        drawProgressBar(29, 4, 113, 44, 14, 14, 48, 7);
        drawProgressBar(29, 32, 113, 0, 22, 22, 48, 0);

        CachedCrush crush = (CachedCrush)this.arecipes.get(recipe);
        if(crush.resultTwo != null){
            int secondChance = crush.secondChance;
            String secondString = secondChance + "%";
            GuiDraw.drawString(secondString, 87, 24, StringUtil.DECIMAL_COLOR_WHITE, false);
        }
    }

    private static Set<Item> excludedFuels(){
        Set<Item> theFuels = new HashSet<Item>();
        theFuels.add(Item.getItemFromBlock(Blocks.brown_mushroom));
        theFuels.add(Item.getItemFromBlock(Blocks.red_mushroom));
        theFuels.add(Item.getItemFromBlock(Blocks.standing_sign));
        theFuels.add(Item.getItemFromBlock(Blocks.wall_sign));
        theFuels.add(Item.getItemFromBlock(Blocks.wooden_door));
        theFuels.add(Item.getItemFromBlock(Blocks.trapped_chest));
        return theFuels;
    }

    private static void findFuels(){
        fuels = new ArrayList<Fuel>();
        Set<Item> theFuels = excludedFuels();
        for(ItemStack item : ItemList.items){
            if(!theFuels.contains(item.getItem())){
                int burnTime = TileEntityFurnace.getItemBurnTime(item);
                if(burnTime > 0) fuels.add(new Fuel(item.copy(), burnTime));
            }
        }
    }

    @Override
    public String getOverlayIdentifier(){
        return NAME;
    }
}