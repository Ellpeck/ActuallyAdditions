/*
 * This file ("BlockContainerBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockContainerBase extends ContainerBlock {
    public BlockContainerBase(Properties properties) {
        super(properties);
    }

    public ActionResultType openGui(World world, PlayerEntity player, BlockPos pos, Class<? extends INamedContainerProvider> expectedInstance) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null && tile.getClass().isInstance(expectedInstance)) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tile, pos);
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    private void dropInventory(World world, BlockPos position) {
        if (!world.isRemote) {
            TileEntity aTile = world.getTileEntity(position);
            if (aTile instanceof TileEntityInventoryBase) {
                TileEntityInventoryBase tile = (TileEntityInventoryBase) aTile;
                if (tile.inv.getSlots() > 0) {
                    for (int i = 0; i < tile.inv.getSlots(); i++) {
                        this.dropSlotFromInventory(i, tile, world, position);
                    }
                }
            }
        }
    }

    private void dropSlotFromInventory(int i, TileEntityInventoryBase tile, World world, BlockPos pos) {
        ItemStack stack = tile.inv.getStackInSlot(i);
        if (stack.isEmpty()) {
            return;
        }

        float dX = world.rand.nextFloat() * 0.8F + 0.1F;
        float dY = world.rand.nextFloat() * 0.8F + 0.1F;
        float dZ = world.rand.nextFloat() * 0.8F + 0.1F;
        ItemEntity entityItem = new ItemEntity(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, stack.copy());
        float factor = 0.05F;
        entityItem.addVelocity(world.rand.nextGaussian() * factor, world.rand.nextGaussian() * factor + 0.2F, world.rand.nextGaussian() * factor);
        world.addEntity(entityItem);
    }

    public boolean tryToggleRedstone(World world, BlockPos pos, PlayerEntity player) {
        ItemStack stack = player.getHeldItemMainhand();
        if (StackUtil.isValid(stack) && stack.getItem() == ConfigValues.itemRedstoneTorchConfigurator) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityBase) {
                TileEntityBase base = (TileEntityBase) tile;
                if (!world.isRemote && base.isRedstoneToggle()) {
                    base.isPulseMode = !base.isPulseMode;
                    base.markDirty();
                    base.sendUpdate();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityBase) {
                TileEntityBase base = (TileEntityBase) tile;
                if (base.respondsToPulses()) {
                    base.activateOnPulse();
                }
            }
        }
    }

    public void neighborsChangedCustom(World world, BlockPos pos) {
        this.updateRedstoneState(world, pos);

        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityBase) {
            TileEntityBase base = (TileEntityBase) tile;
            if (base.shouldSaveDataOnChangeOrWorldStart()) {
                base.saveDataOnChangeOrWorldStart();
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        this.neighborsChangedCustom(worldIn, pos);
    }

    @Override
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, world, pos, neighbor);
        if (world instanceof World) {
            this.neighborsChangedCustom((World) world, pos);
        }
    }

    public void updateRedstoneState(World world, BlockPos pos) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityBase) {
                TileEntityBase base = (TileEntityBase) tile;
                boolean powered = world.getRedstonePowerFromNeighbors(pos) > 0;
                boolean wasPowered = base.isRedstonePowered;
                if (powered && !wasPowered) {
                    if (base.respondsToPulses()) {
                        // TODO: [port] eval what this does? :D
                        //                        world.scheduleUpdate(pos, this, this.tickRate(world));
                    }
                    base.setRedstonePowered(true);
                } else if (!powered && wasPowered) {
                    base.setRedstonePowered(false);
                }
            }
        }
    }

    protected boolean tryUseItemOnTank(PlayerEntity player, Hand hand, FluidTank tank) {
        ItemStack heldItem = player.getHeldItem(hand);
        return StackUtil.isValid(heldItem) && FluidUtil.interactWithFluidHandler(player, hand, tank);

    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        this.updateRedstoneState(worldIn, pos);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasTag()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityBase) {
                TileEntityBase base = (TileEntityBase) tile;
                CompoundNBT compound = stack.getOrCreateTag().getCompound("Data");
                base.readSyncableNBT(compound, TileEntityBase.NBTType.SAVE_BLOCK);
            }
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.isCreative()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityBase && ((TileEntityBase) tile).stopFromDropping) {
                player.sendStatusMessage(new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".machineBroke").mergeStyle(TextFormatting.RED), false);
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityBase) {
            return ((TileEntityBase) tile).getComparatorStrength();
        }
        return 0;
    }

    // TODO: [port]: come back and fix this

    //    @Override
    //    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
    //        TileEntity tile = world.getTileEntity(pos);
    //        if (tile instanceof TileEntityBase) {
    //            TileEntityBase base = (TileEntityBase) tile;
    //            if (!base.stopFromDropping) {
    //                CompoundNBT data = new CompoundNBT();
    //                base.writeSyncableNBT(data, TileEntityBase.NBTType.SAVE_BLOCK);
    //
    //                //Remove unnecessarily saved default values to avoid unstackability
    //                List<String> keysToRemove = new ArrayList<>();
    //                for (String key : data.getKeySet()) {
    //                    NBTBase tag = data.getTag(key);
    //                    //Remove only ints because they are the most common ones
    //                    //Add else if below here to remove more types
    //                    if (tag instanceof NBTTagInt) {
    //                        if (((NBTTagInt) tag).getInt() == 0) {
    //                            keysToRemove.add(key);
    //                        }
    //                    }
    //                }
    //                for (String key : keysToRemove) {
    //                    data.removeTag(key);
    //                }
    //
    //                ItemStack stack = new ItemStack(this.getItemDropped(state, tile.getWorld().rand, fortune), 1, this.damageDropped(state));
    //                if (!data.isEmpty()) {
    //                    stack.setTagCompound(new CompoundNBT());
    //                    stack.getTagCompound().setTag("Data", data);
    //                }
    //
    //                drops.add(stack);
    //            }
    //        } else {
    //            super.getDrops(drops, world, pos, state, fortune);
    //        }
    //    }


    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false, fluid);
    }

    // TODO: [port]: eval

    //    @Override
    //    public EnumBlockRenderType getRenderType(BlockState state) {
    //        return EnumBlockRenderType.MODEL;
    //    }


    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state != newState) {
            if (this.shouldDropInventory(world, pos)) {
                this.dropInventory(world, pos);
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    public boolean shouldDropInventory(World world, BlockPos pos) {
        return true;
    }
}
