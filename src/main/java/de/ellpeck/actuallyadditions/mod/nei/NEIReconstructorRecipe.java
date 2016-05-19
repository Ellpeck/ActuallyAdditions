/*
 * This file ("NEIReconstructorRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

public class NEIReconstructorRecipe/* extends TemplateRecipeHandler implements INEIRecipeHandler*/{

    public static final String NAME = "actuallyadditions.reconstructor";

    /*public NEIReconstructorRecipe(){
        super();
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockAtomicReconstructor));
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+NAME+".name");
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(31+32, 18, 22, 16), NAME));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(NAME) && getClass() == NEIReconstructorRecipe.class){
            List<LensNoneRecipe> recipes = ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES;
            //Default Recipes
            for(LensNoneRecipe recipe : recipes){
                arecipes.add(new CachedReconstructorRecipe(recipe, false));
            }
            //Color Recipes
            for(Object o : LensColor.CONVERTABLE_BLOCKS){
                ItemStack stack;
                if(o instanceof Block){
                    stack = new ItemStack((Block)o);
                }
                else{
                    stack = new ItemStack((Item)o);
                }
                for(int i = 0; i < 16; i++){
                    ItemStack stackCopy = stack.copy();
                    stackCopy.setItemDamage(i >= 15 ? 0 : i+1);
                    stack.setItemDamage(i);
                    arecipes.add(new CachedReconstructorRecipe(new LensNoneRecipe(stack, stackCopy, LensColor.ENERGY_USE), true));
                }
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        List<LensNoneRecipe> recipes = ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES;
        //Default Recipes
        for(LensNoneRecipe recipe : recipes){
            if(ItemUtil.contains(recipe.getOutputs(), result, true)){
                arecipes.add(new CachedReconstructorRecipe(recipe, false));
            }
        }
        //Color Recipes
        if(result.getItem() != null && (Util.arrayContains(LensColor.CONVERTABLE_BLOCKS, result.getItem()) >= 0 || Util.arrayContains(LensColor.CONVERTABLE_BLOCKS, Block.getBlockFromItem(result.getItem())) >= 0)){
            int meta = result.getItemDamage();
            ItemStack input = result.copy();
            input.setItemDamage(meta <= 0 ? 15 : meta-1);
            arecipes.add(new CachedReconstructorRecipe(new LensNoneRecipe(input, result, LensColor.ENERGY_USE), true));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        List<LensNoneRecipe> recipes = ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES;
        //Default Recipes
        for(LensNoneRecipe recipe : recipes){
            if(ItemUtil.contains(recipe.getInputs(), ingredient, true)){
                CachedReconstructorRecipe theRecipe = new CachedReconstructorRecipe(recipe, false);
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.input), ingredient);
                arecipes.add(theRecipe);
            }
        }
        //Color Recipes
        if(ingredient.getItem() != null && (Util.arrayContains(LensColor.CONVERTABLE_BLOCKS, ingredient.getItem()) >= 0 || Util.arrayContains(LensColor.CONVERTABLE_BLOCKS, Block.getBlockFromItem(ingredient.getItem())) >= 0)){
            int meta = ingredient.getItemDamage();
            ItemStack output = ingredient.copy();
            output.setItemDamage(meta >= 15 ? 0 : meta+1);
            arecipes.add(new CachedReconstructorRecipe(new LensNoneRecipe(ingredient, output, LensColor.ENERGY_USE), true));
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID+":textures/gui/guiNEIAtomicReconstructor.png";
    }

    @Override
    public String getOverlayIdentifier(){
        return NAME;
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return null;
    }

    @Override
    public void drawBackground(int recipeIndex){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(32, 0, 0, 0, 96, 60);
    }

    @Override
    public void drawForeground(int recipe){
        if(Minecraft.getMinecraft().currentScreen != null){
            AssetUtil.renderStackToGui(new ItemStack(InitBlocks.blockAtomicReconstructor), 32+34, 19, 1.0F);
        }
        if(((CachedReconstructorRecipe)this.arecipes.get(recipe)).showColorLens){
            String text = InitItems.itemColorLens.getItemStackDisplayName(new ItemStack(InitItems.itemColorLens));
            GuiDraw.drawString(text, 0, 44, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
    }

    @Override
    public int recipiesPerPage(){
        return 2;
    }

    public class CachedReconstructorRecipe extends CachedRecipe{

        public PositionedStack result;
        public PositionedStack input;
        public boolean showColorLens;

        public CachedReconstructorRecipe(LensNoneRecipe recipe, boolean showColorLens){
            this.result = new PositionedStack(recipe.getOutputs(), 67+32, 19);
            this.input = new PositionedStack(recipe.getInputs(), 5+32, 19);
            this.showColorLens = showColorLens;
        }

        @Override
        public PositionedStack getResult(){
            return null;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.input));
        }

        @Override
        public java.util.List<PositionedStack> getOtherStacks(){
            ArrayList<PositionedStack> list = new ArrayList<PositionedStack>();
            list.addAll(this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.result)));
            return list;
        }
    }*/
}