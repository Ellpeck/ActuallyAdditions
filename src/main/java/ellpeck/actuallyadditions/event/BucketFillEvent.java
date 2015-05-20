package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.blocks.BlockFluidFlowing;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketFillEvent{

    @SubscribeEvent
    public void onBucketFilled(FillBucketEvent event){
        Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);

        if(block instanceof BlockFluidFlowing){
            if(block == InitBlocks.blockCanolaOil){
                event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
                event.result = new ItemStack(InitItems.itemBucketCanolaOil);
            }
            if(block == InitBlocks.blockOil){
                event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
                event.result = new ItemStack(InitItems.itemBucketOil);
            }
            event.setResult(Event.Result.ALLOW);
        }
    }

}
