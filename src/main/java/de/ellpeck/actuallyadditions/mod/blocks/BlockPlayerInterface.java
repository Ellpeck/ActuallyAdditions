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

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.IBlockHud;
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.PlayerInterfaceHud;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlayerInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;


public class BlockPlayerInterface extends BlockContainerBase implements IHudDisplay {
    private static final IBlockHud HUD = new PlayerInterfaceHud();
    public BlockPlayerInterface() {
        super(ActuallyBlocks.defaultPickProps(4.5F, 10.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityPlayerInterface(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityPlayerInterface::clientTick : TileEntityPlayerInterface::serverTick;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity player, ItemStack stack) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityPlayerInterface face) {
            if (face.connectedPlayer == null) {
                face.connectedPlayer = player.getUUID();
                face.playerName = player.getName().getString();
                face.setChanged();
                face.sendUpdate();
            }
        }

        super.setPlacedBy(world, pos, state, player, stack);
    }

    @Override
    public IBlockHud getHud() {
        return HUD;
    }
}
