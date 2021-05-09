///*
// * This file ("CompatUtil.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.util.compat;
//
//import net.minecraft.client.gui.inventory.GuiCrafting;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.inventory.ContainerWorkbench;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.common.Loader;
//import net.minecraftforge.fml.common.event.FMLInterModComms;
//
//
//public final class CompatUtil {
//
//    static boolean fb = Loader.isModLoaded("fastbench");
//
//    @OnlyIn(Dist.CLIENT)
//    public static Object getCrafterGuiElement(PlayerEntity player, World world, int x, int y, int z) {
//        if (fb) {
//            return CompatFastBench.getFastBenchGui(player, world);
//        }
//        return new GuiCrafting(player.inventory, world, new BlockPos(x, y, z));
//    }
//
//    public static Object getCrafterContainerElement(PlayerEntity player, World world, int x, int y, int z) {
//        if (fb) {
//            return CompatFastBench.getFastBenchContainer(player, world);
//        }
//        return new ContainerWorkbench(player.inventory, world, new BlockPos(x, y, z)) {
//            @Override
//            public boolean canInteractWith(PlayerEntity playerIn) {
//                return true;
//            }
//        };
//    }
//
//    public static void registerCraftingTweaks() {
//        CompoundNBT t = new CompoundNBT();
//        if (fb) {
//            t.setString("ContainerClass", "de.ellpeck.actuallyadditions.mod.util.compat.CompatFastBench$1");
//        } else {
//            t.setString("ContainerClass", "de.ellpeck.actuallyadditions.mod.util.compat.CompatUtil$1");
//        }
//        FMLInterModComms.sendMessage("craftingtweaks", "RegisterProvider", t);
//    }
//}
