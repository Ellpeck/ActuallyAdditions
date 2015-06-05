package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.gadget.ModelStandardBlock;
import ellpeck.actuallyadditions.gadget.ModelTorch;
import ellpeck.actuallyadditions.gadget.RenderSpecial;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.UUID;

public class RenderPlayerEventAA{

    private RenderSpecial ellpeckRender = new RenderSpecial(new ModelStandardBlock("Ellpeck"));
    private RenderSpecial hoseRender = new RenderSpecial(new ModelTorch());
    //private RenderSpecial paktoRender = new RenderSpecial(new ModelStandardBlock("Pakto"));
    private RenderSpecial glenRender = new RenderSpecial(new ModelStandardBlock("Glenthor"));

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RenderPlayerEvent(RenderPlayerEvent.Pre event){
        if(!event.entityPlayer.isInvisible()){
            //Ellpeck
            if(event.entityPlayer.getUniqueID().equals(UUID.fromString("3f9f4a94-95e3-40fe-8895-e8e3e84d1468"))){
                ellpeckRender.render(event.entityPlayer, event.partialRenderTick, 0.3F, 1F);
                return;
            }

            //Paktosan
            /*if(event.entityPlayer.getUniqueID().equals(UUID.fromString("0bac71ad-9156-487e-9ade-9c5b57274b23"))){
                paktoRender.render(event.entityPlayer, event.partialRenderTick, 0.3F, 1F);
                return;
            }*/

            //TwoOfEight
            if(event.entityPlayer.getUniqueID().equals(UUID.fromString("a57d2829-9711-4552-a7de-ee800802f643"))){
                glenRender.render(event.entityPlayer, event.partialRenderTick, 0.3F, 1F);
                return;
            }

            //dqmhose
            if(event.entityPlayer.getUniqueID().equals(UUID.fromString("cb7b293a-5031-484e-b5be-b4f2f4e92726"))){
                hoseRender.render(event.entityPlayer, event.partialRenderTick, 0.5F, 1.3F);
            }
        }
    }
}
