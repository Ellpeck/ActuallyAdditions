/*
 * This file ("BlockCrystalCluster.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockCrystalCluster extends FullyDirectionalBlock {

    private final TheCrystals crystal;

    public BlockCrystalCluster(TheCrystals crystal) {
        super(Properties.create(Material.GLASS).hardnessAndResistance(0.25F, 1.0F).sound(SoundType.GLASS).setLightLevel(state -> 7));
        this.crystal = crystal;

        //        this.setLightOpacity(1);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.CRYSTAL_CLUSTER_SHAPE;
    }
}
