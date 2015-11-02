/*
 * This file ("RenderPlayerEventAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc.special;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.UUID;

public class RenderPlayerEventAA{

    public static RenderSpecial lariRender = new RenderSpecial(new ItemStack(Items.dye));
    private static RenderSpecial ellpeckRender = new RenderSpecial(new ItemStack(InitItems.itemPhantomConnector));
    private static RenderSpecial hoseRender = new RenderSpecial(new ItemStack(Blocks.torch));
    private static RenderSpecial paktoRender = new RenderSpecial(new ItemStack(Blocks.wool, 1, 6));
    private static RenderSpecial glenRender = new RenderSpecial(new ItemStack(InitBlocks.blockHeatCollector));

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RenderPlayerEvent(RenderPlayerEvent.Specials.Pre event){
        //Ellpeck
        if(event.entityPlayer.getUniqueID().equals(UUID.fromString("3f9f4a94-95e3-40fe-8895-e8e3e84d1468"))){
            ellpeckRender.render(event.entityPlayer, 0.4F, 0.2F);
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
        //Lari
        else if(event.entityPlayer.getUniqueID().equals(UUID.fromString("ac275e30-c468-42af-b5d4-b26c1c705b70"))){
            lariRender.render(event.entityPlayer, 0.15F, -0.125F);
        }
    }
}
