package ellpeck.actuallyadditions.manual;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.inventory.gui.GuiInputter;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent;

public class InventoryEvents{

    private static final int MANUAL_BUTTON_ID = 123782;

    public static class InitGuiEvent{
        @SubscribeEvent
        @SuppressWarnings("unchecked")
        public void onInitGuiEvent(GuiScreenEvent.InitGuiEvent.Post event){
            if(event.gui instanceof GuiInventory && !Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode){
                int k = (event.gui.width-176)/2;
                int l = (event.gui.height-166)/2;

                ManualButton button = new ManualButton(MANUAL_BUTTON_ID, k+160, l+166, "!");
                event.buttonList.add(button);
            }
        }
    }

    public static class ButtonPressedEvent{
        @SubscribeEvent
        public void onButtonPressedEvent(GuiScreenEvent.ActionPerformedEvent.Post event){
            if(event.gui instanceof GuiInventory && event.button.id == MANUAL_BUTTON_ID){
                PacketHandler.theNetwork.sendToServer(new PacketOpenManual());
            }
        }
    }

    public static class ManualButton extends GuiInputter.SmallerButton{

        public ManualButton(int id, int x, int y, String display){
            super(id, x, y, display);
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y){
            super.drawButton(mc, x, y);
            boolean hovering = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            if(this.getHoverState(hovering) == 2){
                String manual = StatCollector.translateToLocal("container." + ModUtil.MOD_ID_LOWER + ".manual.name");
                mc.fontRenderer.drawString(manual, this.xPosition+this.width/2-mc.fontRenderer.getStringWidth(manual)/2 , this.yPosition+this.height+5, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }
    }
}
