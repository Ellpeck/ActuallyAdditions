/*
 * This file ("BlockFarmer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.FarmerHud;
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.IBlockHud;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFarmer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockFarmer extends DirectionalBlock.Container implements IHudDisplay {
    private static final IBlockHud HUD = new FarmerHud();

    public BlockFarmer() {
        super(ActuallyBlocks.defaultPickProps().sound(SoundType.METAL));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityFarmer(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityFarmer::clientTick : TileEntityFarmer::serverTick;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level world, BlockPos pos, Player player, BlockHitResult pHitResult) {
        return this.openGui(world, player, pos, TileEntityFarmer.class);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack heldItem = player.getItemInHand(hand);
        BlockEntity tile = level.getBlockEntity(pos);
        if (heldItem.getItem() == CommonConfig.Other.farmerConfigureItem && tile instanceof TileEntityFarmer farmer) {
            farmer.cycleArea();
            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public IBlockHud getHud() {
        return HUD;
    }
}
