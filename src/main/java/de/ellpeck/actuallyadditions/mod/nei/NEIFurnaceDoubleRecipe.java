/*
 * This file ("NEIFurnaceDoubleRecipe.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

public class NEIFurnaceDoubleRecipe /*extends TemplateRecipeHandler implements INEIRecipeHandler*/{

    public static final String NAME = "actuallyadditions.furnaceDouble";

    /*public NEIFurnaceDoubleRecipe(){
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockFurnaceDouble));
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(51, 40, 24, 22), NAME));
        transferRects.add(new RecipeTransferRect(new Rectangle(101, 40, 24, 22), NAME));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(NAME) && getClass() == NEIFurnaceDoubleRecipe.class){
            Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
            for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()){
                arecipes.add(new CachedFurn(recipe.getKey(), recipe.getValue()));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(ItemStack result){
        Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
        for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()){
            if(NEIServerUtils.areStacksSameType(recipe.getValue(), result)){
                arecipes.add(new CachedFurn(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
        for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()){
            if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)){
                CachedFurn theRecipe = new CachedFurn(recipe.getKey(), recipe.getValue());
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.ingredient), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID+":textures/gui/guiFurnaceDouble.png";
    }

    @Override
    public String getOverlayIdentifier(){
        return NAME;
    }

    @Override
    public void drawExtras(int recipe){
        drawProgressBar(51, 40, 176, 0, 24, 23, 48, 1);
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiFurnaceDouble.class;
    }

    @Override
    public void drawBackground(int recipeIndex){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(46, 20, 46, 20, 84, 70);
    }

    @Override
    public int recipiesPerPage(){
        return 1;
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+NAME+".name");
    }

    public class CachedFurn extends CachedRecipe{

        public PositionedStack ingredient;
        public PositionedStack resultOne;

        public CachedFurn(ItemStack in, ItemStack resultOne){
            in.stackSize = 1;
            this.ingredient = new PositionedStack(in, 51, 21);
            this.resultOne = new PositionedStack(resultOne, 50, 69);
        }

        @Override
        public PositionedStack getResult(){
            return resultOne;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return getCycledIngredients(cycleticks/48, Collections.singletonList(ingredient));
        }
    }*/
}