package de.ellpeck.actuallyadditions.common.blocks.base;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.config.ConfigValues;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class BlockContainerBase extends BlockBase {

    public BlockContainerBase(Properties properties) {
        super(properties);

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

    // todo: replace with native implementation
    private void dropSlotFromInventory(int i, TileEntityInventoryBase tile, World world, BlockPos pos) {
        ItemStack stack = tile.inv.getStackInSlot(i);
        if (StackUtil.isValid(stack)) {
            float dX = world.rand.nextFloat() * 0.8F + 0.1F;
            float dY = world.rand.nextFloat() * 0.8F + 0.1F;
            float dZ = world.rand.nextFloat() * 0.8F + 0.1F;
            ItemEntity entityItem = new ItemEntity(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, stack.copy());
            float factor = 0.05F;

            entityItem.move(MoverType.SELF, new Vec3d(
                    world.rand.nextGaussian() * factor,
                    world.rand.nextGaussian() * factor + 0.2F,
                    world.rand.nextGaussian() * factor
            ));

            world.addEntity(entityItem);
        }
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
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
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
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, world, pos, neighbor);
        if (world instanceof World) {
            this.neighborsChangedCustom((World) world, pos);
        }
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        this.neighborsChangedCustom(worldIn, pos);
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
                        world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
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
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        this.updateRedstoneState(world, pos);
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
                player.sendMessage(new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".machineBroke").setStyle(new Style().setColor(TextFormatting.RED)));
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
        if (tile instanceof TileEntityBase) { return ((TileEntityBase) tile).getComparatorStrength(); }
        return 0;
    }

// todo: come back to this. It think it's goal is to keep data on the dropped item... I know
//       how to do this but I need to make sure I do it right.

//    @Override
//    public void spawnAdditionalDrops(BlockState state, World world, BlockPos pos, ItemStack stack) {
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
//                    data.remove(key);
//                }
//
//                ItemStack stack = new ItemStack(this.getItemDropped(state, tile.getWorld().rand, fortune), 1, this.damageDropped(state));
//                if (!data.isEmpty()) {
//                    stack.setTagCompound(new NBTTagCompound());
//                    stack.getTagCompound().setTag("Data", data);
//                }
//
//                drops.add(stack);
//            }
//        }
//    }


    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false, fluid);
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        // todo: this might not work with this sepecific state check
        if (state != newState && this.shouldDropInventory(world, pos)) {
            this.dropInventory(world, pos);
        }

        super.onReplaced(state, world, pos, newState, isMoving);
    }

    public boolean shouldDropInventory(World world, BlockPos pos) {
        return true;
    }
}