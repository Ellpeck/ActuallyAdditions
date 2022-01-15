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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCanolaPress;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFermentingBarrel;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockFermentingBarrel extends BlockContainerBase {

    public BlockFermentingBarrel() {
        super(Properties.of(Material.WOOD).harvestTool(ToolType.AXE).harvestLevel(0).strength(0.5F, 5.0F).sound(SoundType.WOOD));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityFermentingBarrel();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        TileEntityFermentingBarrel tile = (TileEntityFermentingBarrel) world.getBlockEntity(pos);
        if (tile == null)
            return ActionResultType.PASS; //TODO this logic all needs to be rechecked...
        if (world.isClientSide)
            return ActionResultType.SUCCESS;
        if (!player.isShiftKeyDown()) {
            if (FluidUtil.interactWithFluidHandler(player, hand, tile.tanks)) {
                ItemStack stack = player.getItemInHand(hand);
                world.playSound(null, pos, stack.getItem() == Items.BUCKET ? SoundEvents.BUCKET_EMPTY:SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            } else
                NetworkHooks.openGui((ServerPlayerEntity) player, tile, pos);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shapes.BARREL_SHAPE;
    }
}
