package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BreakEvent{

    @SubscribeEvent
    public void onBlockBreakEvent(BlockEvent.HarvestDropsEvent event){
        IBlockState state = event.getState();
        if(state != null && state.getBlock() == Blocks.MOB_SPAWNER){
            event.getDrops().add(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.SPAWNER_SHARD.ordinal()));
        }
    }

}
