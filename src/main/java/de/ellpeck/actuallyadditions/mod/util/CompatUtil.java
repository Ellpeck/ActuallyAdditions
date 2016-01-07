/*
 * This file ("CompatUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.items.ItemSeed;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CompatUtil{

    public static void registerMFRPlant(Block block){
        FMLInterModComms.sendMessage("MineFactoryReloaded", "registerHarvestable_Crop", new ItemStack(block, 1, 7));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("plant", Block.blockRegistry.getNameForObject(block));
        FMLInterModComms.sendMessage("MineFactoryReloaded", "registerFertilizable_Crop", compound);
    }

    public static void registerMFRSeed(Item item){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("seed", Item.itemRegistry.getNameForObject(item));
        compound.setString("crop", Block.blockRegistry.getNameForObject(((ItemSeed)item).plant));
        FMLInterModComms.sendMessage("MineFactoryReloaded", "registerPlantable_Crop", compound);
    }

}
