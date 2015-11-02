/*
 * This file ("FuelHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.recipe;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

@SuppressWarnings("unused")
public class FuelHandler implements IFuelHandler{

    private static HashMap<Pair<Item, Integer>, Integer> fuelList = new HashMap<Pair<Item, Integer>, Integer>();

    public static void init(){
        ModUtil.LOGGER.info("Initializing Fuelstuffs...");

        GameRegistry.registerFuelHandler(new FuelHandler());
        setFuelValues();
    }

    public static void setFuelValues(){
        addFuel(InitItems.itemMisc, TheMiscItems.TINY_CHAR.ordinal(), 200);
        addFuel(InitItems.itemMisc, TheMiscItems.TINY_COAL.ordinal(), 200);
        addFuel(InitBlocks.blockMisc, TheMiscBlocks.CHARCOAL_BLOCK.ordinal(), 16000);
    }

    private static void addFuel(Item item, int metadata, int value){
        fuelList.put(Pair.of(item, metadata), value);
    }

    private static void addFuel(Block block, int metadata, int value){
        addFuel(Item.getItemFromBlock(block), metadata, value);
    }

    @Override
    public int getBurnTime(ItemStack fuel){
        return getFuelValue(fuel);
    }

    private static int getFuelValue(ItemStack stack){
        if(stack != null && stack.getItem() != null){
            Pair<Item, Integer> pair = Pair.of(stack.getItem(), stack.getItemDamage());

            if(fuelList.containsKey(pair)){
                return fuelList.get(pair);
            }
            else{
                pair = Pair.of(stack.getItem(), 0);
                if(fuelList.containsKey(pair)){
                    return fuelList.get(pair);
                }
            }
        }
        return 0;
    }
}