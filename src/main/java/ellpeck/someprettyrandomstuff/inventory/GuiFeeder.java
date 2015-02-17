package ellpeck.someprettyrandomstuff.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.tile.TileEntityFeeder;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class GuiFeeder extends GuiContainer{

    private static final ResourceLocation resLoc = Util.getGuiLocation("guiFeeder");
    private TileEntityFeeder tileFeeder;

    public GuiFeeder(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFeeder(inventory, tile));
        this.tileFeeder = (TileEntityFeeder)tile;

        this.xSize = 176;
        this.ySize = 156;
    }

    public void drawGuiContainerForegroundLayer(int x, int y){

    }

    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if(this.tileFeeder.currentTimer > 0){
            int i = this.tileFeeder.getCurrentTimerToScale(22);
            this.drawTexturedModalRect(guiLeft+80, guiTop+42-i, 176, 16+22-i, 16, 22);
        }
        if(this.tileFeeder.isBred == 1) this.drawTexturedModalRect(guiLeft+76, guiTop+4, 176, 0, 25, 16);

        if(this.tileFeeder.currentAnimalAmount >= 2 && this.tileFeeder.currentAnimalAmount < this.tileFeeder.animalThreshold) this.drawTexturedModalRect(guiLeft + 70, guiTop + 31, 192, 16, 8, 8);

        if(this.tileFeeder.currentAnimalAmount >= this.tileFeeder.animalThreshold) this.drawTexturedModalRect(guiLeft + 70, guiTop + 31, 192, 24, 8, 8);
    }

    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        if(x >= guiLeft+69 && y >= guiTop+30 && x <= guiLeft+69+10 && y <= guiTop+30+10){
            String[] array = new String[]{(this.tileFeeder.currentAnimalAmount + " " + StatCollector.translateToLocal("feeder.animal.desc") + (this.tileFeeder.currentAnimalAmount == 1 ? "" : StatCollector.translateToLocal("feeder.animalsSuffix.desc"))), ((this.tileFeeder.currentAnimalAmount >= 2 && this.tileFeeder.currentAnimalAmount < this.tileFeeder.animalThreshold) ? StatCollector.translateToLocal("feeder.enoughToBreed.desc") : (this.tileFeeder.currentAnimalAmount >= this.tileFeeder.animalThreshold ? StatCollector.translateToLocal("feeder.tooMany.desc") : StatCollector.translateToLocal("feeder.notEnough.desc")))};
            this.func_146283_a(Arrays.asList(array), x, y);
        }
    }
}