package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Abstract class to wrap shared logic and controls for Block Tiles (Tile Entities).
 * Do not use for normal blocks!
 */
public abstract class FunctionalBlock extends ActuallyBlock {
    private final Supplier<TileEntity> createTile;

    public FunctionalBlock(Properties properties, Supplier<TileEntity> tile) {
        super(properties);
        this.createTile = tile;
    }

    /**
     * Not not override without first calling super otherwise right click will not work. This also handles instance checking
     * although, not super pretty. It'll do :D
     */
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        // We should always have a tile
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile == null) {
            return ActionResultType.FAIL;
        }

        return this.onRightClick(new ActivatedContext(tile, state, pos, worldIn, player, handIn,  hit, player.getHeldItem(handIn)));
    }

    public abstract ActionResultType onRightClick(ActivatedContext context);

    /**
     * This is always true. Do not override as this class is only for functional blocks.
     */
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    /**
     * Creates a tile based on our supplier.
     */
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return this.createTile.get();
    }

    /**
     * Controls if the tile drops items when being broken. By default we'll try and drop anything in and inv cap
     * unless told not to.
     */
    public boolean dropsInventory(World world, BlockPos pos) {
        return true;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, worldIn, pos, newState, isMoving);
        System.out.println("Hello");

        if (newState != state && this.dropsInventory(worldIn, pos)) {
            System.out.println("Should have dropped");
        }
    }

    public static class ActivatedContext {
        TileEntity entity;
        BlockState state;
        BlockPos pos;
        World world;
        PlayerEntity player;
        Hand hand;
        BlockRayTraceResult trace;
        ItemStack heldItem;

        public ActivatedContext(TileEntity entity, BlockState state, BlockPos pos, World world, PlayerEntity player, Hand hand, BlockRayTraceResult trace, ItemStack heldItem) {
            this.entity = entity;
            this.state = state;
            this.pos = pos;
            this.world = world;
            this.player = player;
            this.hand = hand;
            this.trace = trace;
            this.heldItem = heldItem;
        }

        public TileEntity getTile() {
            return this.entity;
        }

        public BlockState getState() {
            return this.state;
        }

        public BlockPos getPos() {
            return this.pos;
        }

        public World getWorld() {
            return this.world;
        }

        public PlayerEntity getPlayer() {
            return this.player;
        }

        public Hand getHand() {
            return this.hand;
        }

        public BlockRayTraceResult getTrace() {
            return this.trace;
        }

        public ItemStack getHeldItem() {
            return this.heldItem;
        }

        /**
         * Simple wrapper, does not guarantee an inventory.
         */
        public LazyOptional<IItemHandler> getInv() {
            return this.entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
        }

        /**
         * Simple wrapper, does not guarantee energy
         */
        public LazyOptional<IEnergyStorage> getEnergy() {
            return this.entity.getCapability(CapabilityEnergy.ENERGY);
        }
    }
}
