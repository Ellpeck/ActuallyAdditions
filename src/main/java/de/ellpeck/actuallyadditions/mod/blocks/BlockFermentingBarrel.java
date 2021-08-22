/*
 * This file ("BlockFermentingBarrel.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFermentingBarrel;
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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.AbstractBlock.Properties;

public class BlockFermentingBarrel extends BlockContainerBase {

    public BlockFermentingBarrel() {
        super(Properties.of(Material.WOOD).harvestTool(ToolType.AXE).harvestLevel(0).strength(0.5F, 5.0F).sound(SoundType.WOOD));
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityFermentingBarrel();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide) {
            TileEntityFermentingBarrel press = (TileEntityFermentingBarrel) world.getBlockEntity(pos);
            if (press != null) {
                if (!this.tryUseItemOnTank(player, hand, press.canolaTank) && !this.tryUseItemOnTank(player, hand, press.oilTank)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, press, pos);
                }
            }
            return ActionResultType.PASS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.BARREL_SHAPE;
    }
}
