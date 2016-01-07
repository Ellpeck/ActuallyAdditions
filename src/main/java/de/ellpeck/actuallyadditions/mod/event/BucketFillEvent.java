/*
 * This file ("BucketFillEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BucketFillEvent{

    @SubscribeEvent
    public void onBucketFilled(FillBucketEvent event){
        this.fillBucket(event, InitItems.itemBucketOil, InitBlocks.blockOil);
        this.fillBucket(event, InitItems.itemBucketCanolaOil, InitBlocks.blockCanolaOil);
    }

    private void fillBucket(FillBucketEvent event, Item item, Block fluid){
        Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
        if(block == fluid){
            event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
            event.result = new ItemStack(item);
            event.setResult(Event.Result.ALLOW);
        }
    }
}
