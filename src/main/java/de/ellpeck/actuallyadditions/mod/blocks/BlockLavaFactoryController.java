/*
 * This file ("BlockLavaFactoryController.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLavaFactoryController;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class BlockLavaFactoryController extends DirectionalBlock.Container implements IHudDisplay {

    public BlockLavaFactoryController() {
        super(ActuallyBlocks.defaultPickProps(0, 4.5F, 20.0F));
    }

    //@Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityLavaFactoryController();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        if (!(rayCast instanceof BlockRayTraceResult)) {
            return;
        }

        TileEntityLavaFactoryController factory = (TileEntityLavaFactoryController) minecraft.level.getBlockEntity(((BlockRayTraceResult) rayCast).getBlockPos());
        if (factory != null) {
            int state = factory.isMultiblock();
            if (state == TileEntityLavaFactoryController.NOT_MULTI) {
                StringUtil.drawSplitString(minecraft.font, StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".factory.notPart.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 5, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
            } else if (state == TileEntityLavaFactoryController.HAS_AIR || state == TileEntityLavaFactoryController.HAS_LAVA) {
                StringUtil.drawSplitString(minecraft.font, StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".factory.works.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 5, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return Shapes.LavaFactoryShapes.SHAPE_E;
            case SOUTH:
                return Shapes.LavaFactoryShapes.SHAPE_S;
            case WEST:
                return Shapes.LavaFactoryShapes.SHAPE_W;
            default:
                return Shapes.LavaFactoryShapes.SHAPE_N;
        }
    }
}
