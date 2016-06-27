/*
 * This file ("CompatUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util.compat;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public final class CompatUtil{

    public static void registerMFRPlant(Block block){
        FMLInterModComms.sendMessage("MineFactoryReloaded", "registerHarvestable_Crop", new ItemStack(block, 1, 7));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("plant", block.getRegistryName().toString());
        FMLInterModComms.sendMessage("MineFactoryReloaded", "registerFertilizable_Crop", compound);
    }

    public static void registerMFRSeed(Item item, Block plant){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("seed", item.getRegistryName().toString());
        compound.setString("crop", plant.getRegistryName().toString());
        FMLInterModComms.sendMessage("MineFactoryReloaded", "registerPlantable_Crop", compound);
    }

}
