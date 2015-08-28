package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

@SuppressWarnings("unused")
public class Util{

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;

    public static void registerEvent(Object o){
        MinecraftForge.EVENT_BUS.register(o);
        FMLCommonHandler.instance().bus().register(o);
    }

    public static void registerDispenserHandler(Item item, BehaviorDefaultDispenseItem handler){
        BlockDispenser.dispenseBehaviorRegistry.putObject(item, handler);
    }

    public static IRecipe latestIRecipe(){
        List list = CraftingManager.getInstance().getRecipeList();
        Object recipe = list.get(list.size()-1);
        return recipe instanceof IRecipe ? (IRecipe)recipe : null;
    }
}