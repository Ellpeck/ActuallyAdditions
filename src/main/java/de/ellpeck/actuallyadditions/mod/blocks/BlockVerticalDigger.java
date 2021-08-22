/*
 * This file ("BlockMiner.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityVerticalDigger;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class BlockVerticalDigger extends DirectionalBlock.Container implements IHudDisplay {

    public BlockVerticalDigger() {
        super(ActuallyBlocks.defaultPickProps(0, 8F, 30F));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return this.openGui(worldIn, player, pos, TileEntityMiner.class);
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new TileEntityMiner();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        if (!(rayCast instanceof BlockRayTraceResult)) {
            return;
        }
        TileEntity tile = minecraft.level.getBlockEntity(((BlockRayTraceResult) rayCast).getBlockPos());
        if (tile instanceof TileEntityMiner) {
            TileEntityMiner miner = (TileEntityMiner) tile;
            String info = miner.checkY == 0
                ? "Done Mining!"
                : miner.checkY == -1
                    ? "Calculating positions..."
                    : "Mining at " + (miner.getBlockPos().getX() + miner.checkX) + ", " + miner.checkY + ", " + (miner.getBlockPos().getZ() + miner.checkZ) + ".";
            minecraft.font.drawShadow(matrices, info, resolution.getGuiScaledWidth() / 2f + 5, resolution.getGuiScaledHeight() / 2f - 20, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return Shapes.MinerShapes.SHAPE_N;
            case EAST:
                return Shapes.MinerShapes.SHAPE_E;
            case SOUTH:
                return Shapes.MinerShapes.SHAPE_S;
            case WEST:
                return Shapes.MinerShapes.SHAPE_W;
            default:
                return Shapes.MinerShapes.SHAPE_N;
        }
    }
}
