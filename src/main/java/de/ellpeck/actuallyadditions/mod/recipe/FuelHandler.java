/*
 * This file ("FuelHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public class FuelHandler implements IFuelHandler{

    private static final HashMap<Pair<Item, Integer>, Integer> FUEL_LIST = new HashMap<Pair<Item, Integer>, Integer>();

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
        FUEL_LIST.put(Pair.of(item, metadata), value);
    }

    private static void addFuel(Block block, int metadata, int value){
        addFuel(Item.getItemFromBlock(block), metadata, value);
    }

    private static int getFuelValue(ItemStack stack){
        if(stack != null && stack.getItem() != null){
            Pair<Item, Integer> pair = Pair.of(stack.getItem(), stack.getItemDamage());

            if(FUEL_LIST.containsKey(pair)){
                return FUEL_LIST.get(pair);
            }
            else{
                pair = Pair.of(stack.getItem(), 0);
                if(FUEL_LIST.containsKey(pair)){
                    return FUEL_LIST.get(pair);
                }
            }
        }
        return 0;
    }

    @Override
    public int getBurnTime(ItemStack fuel){
        return getFuelValue(fuel);
    }
}