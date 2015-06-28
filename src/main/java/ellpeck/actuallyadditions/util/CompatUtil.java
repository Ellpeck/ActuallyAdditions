package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.event.FMLInterModComms;
import ellpeck.actuallyadditions.items.ItemSeed;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
