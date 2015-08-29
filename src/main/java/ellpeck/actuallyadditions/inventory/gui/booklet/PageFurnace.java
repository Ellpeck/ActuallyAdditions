package ellpeck.actuallyadditions.inventory.gui.booklet;

import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.Map;

public class PageFurnace extends PageText{

    private final ItemStack result;

    public PageFurnace(int id, ItemStack result){
        super(id);
        this.result = result;
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY){
        if(this.getInputForOutput(this.result) != null){
            gui.mc.getTextureManager().bindTexture(GuiBooklet.resLoc);
            gui.drawTexturedModalRect(gui.guiLeft+37, gui.guiTop+20, 0, 180, 60, 60);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY){
        ItemStack input = this.getInputForOutput(this.result);
        if(input == null){
            gui.unicodeRenderer.drawSplitString(StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".recipeDisabled"), gui.guiLeft+14, gui.guiTop+15, 115, 0);
        }

        gui.unicodeRenderer.drawSplitString(gui.currentPage.getText(), gui.guiLeft+14, gui.guiTop+112, 115, 0);

        if(input != null){
            for(int i = 0; i < 2; i++){
                for(int x = 0; x < 2; x++){
                    ItemStack stack = x == 0 ? input : this.result;
                    boolean tooltip = i == 1;

                    int xShow = gui.guiLeft+37+1+x*40;
                    int yShow = gui.guiTop+20+20;
                    if(!tooltip){
                        RenderHelper.disableStandardItemLighting();
                        RenderItem.getInstance().renderItemAndEffectIntoGUI(gui.unicodeRenderer, gui.mc.getTextureManager(), stack, xShow, yShow);
                        RenderHelper.enableStandardItemLighting();
                    }
                    else{
                        if(mouseX >= xShow && mouseX <= xShow+16 && mouseY >= yShow && mouseY <= yShow+16){
                            gui.renderToolTip(stack, mouseX, mouseY);
                        }
                    }
                }
            }
        }
    }

    private ItemStack getInputForOutput(ItemStack output){
        for(Object o : FurnaceRecipes.smelting().getSmeltingList().entrySet()){
            ItemStack stack = (ItemStack)((Map.Entry)o).getValue();
            if(stack.isItemEqual(output)) return (ItemStack)((Map.Entry)o).getKey();
        }
        return null;
    }
}
