// FIXME: [port] EXU is not for 1.16
///*
// * This file ("EnderlillyFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.exu;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.Blocks;
//import net.minecraftforge.common.Tags;
//
//public class EnderlillyFarmerBehavior extends ExUPlantFarmerBehavior {
//
//    @Override
//    protected String getPlantName() {
//        return "extrautils2:enderlilly";
//    }
//
//    @Override
//    protected boolean canPlaceOn(Block block) {
//        return block == Blocks.END_STONE || Tags.Blocks.DIRT.contains(block) || Tags.Blocks.GLASS.contains(block);
//    }
//
//    @Override
//    protected int getMaxStage() {
//        return 7;
//    }
//}
