/*
 * This file ("PageCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.page;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.proxy.ClientProxy;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;


public class PageCrafting extends BookletPage{

    private final IRecipe[] recipes;
    private int recipePos;

    public PageCrafting(int id, ArrayList<IRecipe> recipes){
        this(id, recipes.toArray(new IRecipe[recipes.size()]));
    }

    public PageCrafting(int id, IRecipe... recipes){
        super(id);
        this.recipes = recipes;
        this.addToPagesWithItemStackData();
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        if(this.recipes[this.recipePos] != null){
            gui.mc.getTextureManager().bindTexture(ClientProxy.bulletForMyValentine ? GuiBooklet.resLocValentine : GuiBooklet.resLoc);
            gui.drawTexturedModalRect(gui.guiLeft+27, gui.guiTop+20, 146, 20, 99, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){
        IRecipe recipe = this.recipes[this.recipePos];

        if(recipe == null){
            gui.mc.fontRenderer.drawSplitString(EnumChatFormatting.DARK_RED+StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.guiLeft+14, gui.guiTop+15, 115, 0);
        }
        else{
            String strg = StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+"."+(recipe instanceof ShapedRecipes ? "shapedRecipe" : (recipe instanceof ShapelessRecipes ? "shapelessRecipe" : (recipe instanceof ShapelessOreRecipe ? "shapelessOreRecipe" : "shapedOreRecipe"))));
            gui.mc.fontRenderer.drawString(strg, gui.guiLeft+gui.xSize/2-gui.mc.fontRenderer.getStringWidth(strg)/2, gui.guiTop+10, 0);
        }

        String text = gui.currentPage.getText();
        if(text != null && !text.isEmpty()){
            gui.mc.fontRenderer.drawSplitString(text, gui.guiLeft+14, gui.guiTop+90, 115, 0);
        }

        if(recipe != null){

            ItemStack[] stacks = new ItemStack[9];
            int width = 3;
            int height = 3;

            if(recipe instanceof ShapedRecipes){
                ShapedRecipes shaped = (ShapedRecipes)recipe;
                width = shaped.recipeWidth;
                height = shaped.recipeHeight;
                stacks = shaped.recipeItems;
            }
            else if(recipe instanceof ShapelessRecipes){
                ShapelessRecipes shapeless = (ShapelessRecipes)recipe;
                for(int i = 0; i < shapeless.recipeItems.size(); i++){
                    stacks[i] = (ItemStack)shapeless.recipeItems.get(i);
                }
            }
            else if(recipe instanceof ShapedOreRecipe){
                ShapedOreRecipe shaped = (ShapedOreRecipe)recipe;
                width = ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shaped, 4);
                height = ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shaped, 5);
                for(int i = 0; i < shaped.getInput().length; i++){
                    Object input = shaped.getInput()[i];
                    if(input != null){
                        stacks[i] = input instanceof ItemStack ? (ItemStack)input : ((ArrayList<ItemStack>)input).get(0);
                    }
                }
            }
            else if(recipe instanceof ShapelessOreRecipe){
                ShapelessOreRecipe shapeless = (ShapelessOreRecipe)recipe;
                for(int i = 0; i < shapeless.getInput().size(); i++){
                    Object input = shapeless.getInput().get(i);
                    stacks[i] = input instanceof ItemStack ? (ItemStack)input : ((ArrayList<ItemStack>)input).get(0);
                }
            }

            int xShowOutput = gui.guiLeft+27+82;
            int yShowOutput = gui.guiTop+20+22;
            renderItem(gui, recipe.getRecipeOutput(), xShowOutput, yShowOutput, 1.0F);
            for(int i = 0; i < 2; i++){
                boolean tooltip = i == 1;
                for(int x = 0; x < width; x++){
                    for(int y = 0; y < height; y++){
                        ItemStack stack = stacks[y*width+x];
                        if(stack != null){
                            stack.stackSize = 1;
                            if(stack.getItemDamage() == Util.WILDCARD){
                                stack.setItemDamage(0);
                            }
                            int xShow = gui.guiLeft+27+4+x*18;
                            int yShow = gui.guiTop+20+4+y*18;
                            if(!tooltip){
                                renderItem(gui, stack, xShow, yShow, 1.0F);
                            }
                            else{
                                if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                                    this.renderTooltipAndTransfer(gui, stack, mouseX, mouseY, true, mousePressed);
                                }
                            }
                        }
                    }
                }
            }
            if(mouseX >= xShowOutput && mouseX <= xShowOutput+16 && mouseY >= yShowOutput && mouseY <= yShowOutput+16){
                this.renderTooltipAndTransfer(gui, recipe.getRecipeOutput(), mouseX, mouseY, false, mousePressed);
            }
        }
    }

    @Override
    public void updateScreen(int ticksElapsed){
        if(ticksElapsed%30 == 0){
            if(this.recipePos+1 >= this.recipes.length){
                this.recipePos = 0;
            }
            else{
                this.recipePos++;
            }
        }
    }

    @Override
    public ItemStack[] getItemStacksForPage(){
        if(this.recipes != null && this.recipes.length > 0 && this.recipes[0] != null){
            return new ItemStack[]{this.recipes[0].getRecipeOutput()};
        }
        return null;
    }
}
