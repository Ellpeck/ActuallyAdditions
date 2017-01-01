/*
 * This file ("FuelHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class FuelHandler implements IFuelHandler{

    private static final List<Fuel> FUEL_LIST = new ArrayList<Fuel>();

    public static void init(){
        ModUtil.LOGGER.info("Initializing Fuelstuffs...");

        GameRegistry.registerFuelHandler(new FuelHandler());

        addFuel(InitItems.itemMisc, TheMiscItems.TINY_CHAR.ordinal(), 200);
        addFuel(InitItems.itemMisc, TheMiscItems.TINY_COAL.ordinal(), 200);
        addFuel(InitBlocks.blockMisc, TheMiscBlocks.CHARCOAL_BLOCK.ordinal(), 16000);
        addFuel(InitItems.itemMisc, TheMiscItems.BIOCOAL.ordinal(), 80);
    }

    private static void addFuel(Item item, int meta, int value){
        FUEL_LIST.add(new Fuel(new ItemStack(item, 1, meta), value));
    }

    private static void addFuel(Block block, int meta, int value){
        addFuel(Item.getItemFromBlock(block), meta, value);
    }

    @Override
    public int getBurnTime(ItemStack stack){
        if(StackUtil.isValid(stack)){
            for(Fuel fuel : FUEL_LIST){
                if(stack.isItemEqual(fuel.fuel)){
                    return fuel.burnTime;
                }
            }
        }
        return 0;
    }

    private static class Fuel{

        public ItemStack fuel;
        public int burnTime;

        public Fuel(ItemStack fuel, int burnTime){
            this.fuel = fuel;
            this.burnTime = burnTime;
        }

    }
}