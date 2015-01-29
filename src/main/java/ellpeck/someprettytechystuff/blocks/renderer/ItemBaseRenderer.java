package ellpeck.someprettytechystuff.blocks.renderer;

import ellpeck.someprettytechystuff.blocks.models.ModelBaseSPTS;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemBaseRenderer implements IItemRenderer{

    private final ModelBaseSPTS model;
    private final ResourceLocation resLoc;

    public ItemBaseRenderer(ModelBaseSPTS model, ResourceLocation resLoc){
        this.model = model;
        this.resLoc = resLoc;
    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type){
        return true;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data){
        switch(type){
            case INVENTORY:{
                GL11.glPushMatrix();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-0.4F, -1.27F, 0.5F);
                Minecraft.getMinecraft().renderEngine.bindTexture(resLoc);
                model.render(0.0625F);
                GL11.glPopMatrix();
                break;
            }
            case EQUIPPED:{
                GL11.glPushMatrix();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(180F, -2.3F, 0.0F, 1.0F);
                GL11.glTranslatef(0.1F, -0.8F, 0.68F);
                Minecraft.getMinecraft().renderEngine.bindTexture(resLoc);
                model.render(0.0625F);
                GL11.glPopMatrix();
                break;
            }

            case EQUIPPED_FIRST_PERSON:{
                GL11.glPushMatrix();
                GL11.glRotatef(180, 1F, -0F, 0.5F);
                GL11.glTranslatef(0.3F, -1.7F, -0.3F);
                Minecraft.getMinecraft().renderEngine.bindTexture(resLoc);
                model.render(0.0625F);
                GL11.glPopMatrix();
                break;
            }

            default:{
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0F, 0.5F, 0.0F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(0.0F, -1.27F, 0.0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(resLoc);
                model.render(0.0625F);
                GL11.glPopMatrix();
                break;
            }
        }
    }

}