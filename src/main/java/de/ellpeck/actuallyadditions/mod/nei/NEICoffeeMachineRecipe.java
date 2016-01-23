/*
 * This file ("NEICoffeeMachineRecipe.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

public class NEICoffeeMachineRecipe/* extends TemplateRecipeHandler implements INEIRecipeHandler*/{

    public static final String NAME = "actuallyadditions.coffee";

    /*public NEICoffeeMachineRecipe(){
        super();
        RecipeInfo.setGuiOffset(this.getGuiClass(), 35, 3);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockCoffeeMachine));
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(20, 39, 20, 16), NAME));
        transferRects.add(new RecipeTransferRect(new Rectangle(64, 42, 23, 10), NAME));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(NAME) && getClass() == NEICoffeeMachineRecipe.class){
            List<CoffeeIngredient> ingredients = ActuallyAdditionsAPI.coffeeMachineIngredients;
            for(CoffeeIngredient ingredient : ingredients){
                arecipes.add(new CachedCoffee(ingredient));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        List<CoffeeIngredient> ingredients = ActuallyAdditionsAPI.coffeeMachineIngredients;
        for(CoffeeIngredient ingredient : ingredients){
            if(result.getItem() instanceof ItemCoffee){
                arecipes.add(new CachedCoffee(ingredient));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){

        List<CoffeeIngredient> ingredients = ActuallyAdditionsAPI.coffeeMachineIngredients;
        for(CoffeeIngredient ingr : ingredients){
            if(NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()), ingredient) || NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(InitItems.itemCoffeeBean), ingredient) || NEIServerUtils.areStacksSameTypeCrafting(ingr.ingredient.copy(), ingredient)){
                CachedCoffee theRecipe = new CachedCoffee(ingr);
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.ingredientStack), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID_LOWER+":textures/gui/guiNEICoffeeMachine.png";
    }

    @Override
    public String getOverlayIdentifier(){
        return NAME;
    }

    @Override
    public void drawExtras(int recipe){
        drawProgressBar(20, 39, 126, 0, 21, 16, 48, 0);
        drawProgressBar(63, 42, 125, 16, 24, 12, 48, 2);

        CachedCoffee cache = (CachedCoffee)this.arecipes.get(recipe);
        if(cache.extraText != null){
            GuiDraw.drawString(StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".coffee.special")+":", 2, 4, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
            GuiDraw.drawString(cache.extraText, 2, 16, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }

        if(cache.maxAmp > 0){
            GuiDraw.drawString(StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".coffee.maxAmount")+": "+cache.maxAmp, 2, 28, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiCoffeeMachine.class;
    }

    @Override
    public void drawBackground(int recipeIndex){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 126, 88);
    }

    @Override
    public int recipiesPerPage(){
        return 1;
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+NAME+".name");
    }

    public class CachedCoffee extends CachedRecipe{

        public PositionedStack cup;
        public PositionedStack coffeeBeans;
        public PositionedStack result;
        public PositionedStack ingredientStack;
        public String extraText;
        public int maxAmp;

        public CachedCoffee(CoffeeIngredient ingredient){
            this.cup = new PositionedStack(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()), 45, 39);
            this.coffeeBeans = new PositionedStack(new ItemStack(InitItems.itemCoffeeBean, TileEntityCoffeeMachine.CACHE_USE), 2, 39);
            this.ingredientStack = new PositionedStack(ingredient.ingredient.copy(), 90, 21);
            this.setupResult(ingredient);
            this.extraText = ingredient.getExtraText();
            this.maxAmp = ingredient.maxAmplifier;
        }

        public void setupResult(CoffeeIngredient ingredient){
            ItemStack result = new ItemStack(InitItems.itemCoffee);
            CoffeeBrewing.addEffectToStack(result, ingredient);
            this.result = new PositionedStack(result.copy(), 45, 70);
        }

        @Override
        public PositionedStack getResult(){
            return result;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            ArrayList<PositionedStack> list = new ArrayList<PositionedStack>();
            list.add(this.ingredientStack);
            list.add(this.cup);
            list.add(this.coffeeBeans);
            return list;
        }
    }*/
}