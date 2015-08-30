/*
 * This file ("RenderPlayerEventAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.gadget.RenderSpecial;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.UUID;

public class RenderPlayerEventAA{

    private static RenderSpecial ellpeckRender = new RenderSpecial(InitBlocks.blockPhantomLiquiface, 0);
    private static RenderSpecial hoseRender = new RenderSpecial(Blocks.torch, 0);
    private static RenderSpecial paktoRender = new RenderSpecial(Blocks.wool, 6);
    private static RenderSpecial glenRender = new RenderSpecial(InitBlocks.blockHeatCollector, 0);
    private static RenderSpecial lordiRender = new RenderSpecial(InitBlocks.blockBreaker, 0);
    public static RenderSpecial lariRender = new RenderSpecial(null, 0);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RenderPlayerEvent(RenderPlayerEvent.Specials.Pre event){
        //Ellpeck
        if(event.entityPlayer.getUniqueID().equals(UUID.fromString("3f9f4a94-95e3-40fe-8895-e8e3e84d1468"))){
            ellpeckRender.render(event.entityPlayer, 0.3F, 0);
        }
        //Paktosan
        else if(event.entityPlayer.getUniqueID().equals(UUID.fromString("0bac71ad-9156-487e-9ade-9c5b57274b23"))){
            paktoRender.render(event.entityPlayer, 0.3F, 0);
        }
        //TwoOfEight
        else if(event.entityPlayer.getUniqueID().equals(UUID.fromString("a57d2829-9711-4552-a7de-ee800802f643"))){
            glenRender.render(event.entityPlayer, 0.3F, 0);
        }
        //dqmhose
        else if(event.entityPlayer.getUniqueID().equals(UUID.fromString("cb7b293a-5031-484e-b5be-b4f2f4e92726"))){
            hoseRender.render(event.entityPlayer, 0.5F, -0.075F);
        }
        //Lordi
        else if(event.entityPlayer.getUniqueID().equals(UUID.fromString("990ecf6d-15dd-442c-b91b-323a6420c78e"))){
            lordiRender.render(event.entityPlayer, 0.3F, 0);
        }
        //Lari
        else if(event.entityPlayer.getUniqueID().equals(UUID.fromString("ac275e30-c468-42af-b5d4-b26c1c705b70"))){
            lariRender.render(event.entityPlayer, 0.15F, -0.125F);
        }
    }
}
