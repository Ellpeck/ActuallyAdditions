package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("unused")
public class Util{

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;

    public static void registerEvent(Object o){
        MinecraftForge.EVENT_BUS.register(o);
        FMLCommonHandler.instance().bus().register(o);
    }

    public static void registerDispenserHandler(Item item, BehaviorDefaultDispenseItem handler){
        if(BlockDispenser.dispenseBehaviorRegistry.getObject(item) == null){
            BlockDispenser.dispenseBehaviorRegistry.putObject(item, handler);
        }
    }
}