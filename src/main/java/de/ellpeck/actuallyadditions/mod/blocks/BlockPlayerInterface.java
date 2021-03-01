/*
 * This file ("BlockPlayerInterface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlayerInterface;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class BlockPlayerInterface extends BlockContainerBase implements IHudDisplay {
    public BlockPlayerInterface() {
        super(ActuallyBlocks.defaultPickProps(0, 4.5F, 10.0F));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityPlayerInterface();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity player, ItemStack stack) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityPlayerInterface) {
            TileEntityPlayerInterface face = (TileEntityPlayerInterface) tile;
            if (face.connectedPlayer == null) {
                face.connectedPlayer = player.getUniqueID();
                face.playerName = player.getName().getString();
                face.markDirty();
                face.sendUpdate();
            }
        }

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        if (!(rayCast instanceof BlockRayTraceResult)) {
            return;
        }

        TileEntity tile = minecraft.world.getTileEntity(((BlockRayTraceResult) rayCast).getPos());
        if (tile != null) {
            if (tile instanceof TileEntityPlayerInterface) {
                TileEntityPlayerInterface face = (TileEntityPlayerInterface) tile;
                String name = face.playerName == null
                    ? "Unknown"
                    : face.playerName;
                minecraft.fontRenderer.drawStringWithShadow(matrices, "Bound to: " + TextFormatting.RED + name, resolution.getScaledWidth() / 2f + 5, resolution.getScaledHeight() / 2f + 5, StringUtil.DECIMAL_COLOR_WHITE);
                minecraft.fontRenderer.drawStringWithShadow(matrices, "UUID: " + TextFormatting.DARK_GREEN + face.connectedPlayer, resolution.getScaledWidth() / 2f + 5, resolution.getScaledHeight() / 2f + 15, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.PLAYER_INTERFACE_SHAPE;
    }
}
