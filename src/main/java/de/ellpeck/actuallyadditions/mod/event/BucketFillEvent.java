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


import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BucketFillEvent{

    @SubscribeEvent
    public void onBucketFilled(FillBucketEvent event){
        this.fillBucket(event, InitItems.itemBucketOil, InitFluids.blockOil);
        this.fillBucket(event, InitItems.itemBucketCanolaOil, InitFluids.blockCanolaOil);
    }

    private void fillBucket(FillBucketEvent event, Item item, Block fluid){
        if(event.getTarget() != null && event.getTarget().getBlockPos() != null){
            Block block = PosUtil.getBlock(event.getTarget().getBlockPos(), event.getWorld());
            if(block == fluid){
                event.getWorld().setBlockToAir(event.getTarget().getBlockPos());
                event.setFilledBucket(new ItemStack(item));
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
