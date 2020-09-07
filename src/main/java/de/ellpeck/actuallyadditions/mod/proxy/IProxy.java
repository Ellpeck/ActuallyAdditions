package de.ellpeck.actuallyadditions.mod.proxy;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void addRenderRegister(ItemStack stack, ResourceLocation location, String variant);

    void addColoredItem(Item item);

    void addColoredBlock(Block block);

    EntityPlayer getCurrentPlayer();

    default void sendBreakPacket(BlockPos pos) {
    };

}
