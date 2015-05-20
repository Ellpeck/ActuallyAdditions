package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityFeeder;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class GuiFeeder extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiFeeder");
    private TileEntityFeeder tileFeeder;

    public int loveCounter;

    public GuiFeeder(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFeeder(inventory, tile));
        this.tileFeeder = (TileEntityFeeder)tile;
        this.xSize = 176;
        this.ySize = 70+86;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.tileFeeder.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+70, 0, 0, 176, 86);
        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 70);

        if(this.tileFeeder.currentTimer > 0){
            int i = this.tileFeeder.getCurrentTimerToScale(22);
            this.drawTexturedModalRect(guiLeft + 80, guiTop + 42 - i, 176, 16 + 22 - i, 16, 22);
        }

        if(this.tileFeeder.currentAnimalAmount >= 2 && this.tileFeeder.currentAnimalAmount < this.tileFeeder.animalThreshold) this.drawTexturedModalRect(guiLeft + 70, guiTop + 31, 192, 16, 8, 8);

        if(this.tileFeeder.currentAnimalAmount >= this.tileFeeder.animalThreshold) this.drawTexturedModalRect(guiLeft + 70, guiTop + 31, 192, 24, 8, 8);

        if(this.loveCounter > 0){
            this.loveCounter++;
            if(this.loveCounter >= 15){
                this.loveCounter = 0;
            }
            this.drawTexturedModalRect(guiLeft + 76, guiTop + 4, 176, 0, 25, 16);
        }
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        if(x >= guiLeft+69 && y >= guiTop+30 && x <= guiLeft+69+10 && y <= guiTop+30+10){
            String[] array = new String[]{(this.tileFeeder.currentAnimalAmount + " " + StatCollector.translateToLocal("info." + ModUtil.MOD_ID_LOWER + ".gui.animals")), ((this.tileFeeder.currentAnimalAmount >= 2 && this.tileFeeder.currentAnimalAmount < this.tileFeeder.animalThreshold) ? StatCollector.translateToLocal("info." + ModUtil.MOD_ID_LOWER + ".gui.enoughToBreed") : (this.tileFeeder.currentAnimalAmount >= this.tileFeeder.animalThreshold ? StatCollector.translateToLocal("info." + ModUtil.MOD_ID_LOWER + ".gui.tooMany") : StatCollector.translateToLocal("info." + ModUtil.MOD_ID_LOWER + ".gui.notEnough")))};
            this.func_146283_a(Arrays.asList(array), x, y);
        }
    }
}