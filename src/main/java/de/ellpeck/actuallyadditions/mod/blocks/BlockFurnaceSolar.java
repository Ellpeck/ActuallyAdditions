// TODO: [port][note] not used
///*
// * This file ("BlockFurnaceSolar.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.blocks;
//
//import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
//import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceSolar;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.SoundType;
//import net.minecraft.block.material.Material;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.IBlockReader;
//
//public class BlockFurnaceSolar extends BlockContainerBase {
//    public BlockFurnaceSolar() {
//        super(Material.ROCK, this.name);
//        this.setHarvestLevel("pickaxe", 0);
//        this.setHardness(1.5F);
//        this.setResistance(10.0F);
//        this.setSoundType(SoundType.STONE);
//    }
//
//
//    @Override
//    public TileEntity createNewTileEntity(IBlockReader worldIn) {
//        return new TileEntityFurnaceSolar();
//    }
//}
