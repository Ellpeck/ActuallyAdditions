/*
 * This file ("BlockBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBreaker;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockBreaker extends FullyDirectionalBlock.Container {

    private final boolean isPlacer;

    public BlockBreaker(boolean isPlacer) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE));
        this.isPlacer = isPlacer;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader world) {
        return this.isPlacer
            ? new TileEntityPlacer()
            : new TileEntityBreaker();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (this.tryToggleRedstone(world, pos, player)) {
            return ActionResultType.PASS;
        }
        if (!world.isRemote) {
            TileEntityBreaker tile = (TileEntityBreaker) world.getTileEntity(pos);
            if (tile != null) {
                NetworkHooks.openGui((ServerPlayerEntity) player, tile, pos);
            }
            return ActionResultType.PASS;
        }
        return super.onBlockActivated(state, world, pos, player, handIn, hit);
    }
}
