/*
 * This file ("TileEntityMiner.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerMiner;
import de.ellpeck.actuallyadditions.mod.items.DrillItem;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.IFluidBlock;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityVerticalDigger extends TileEntityInventoryBase implements IButtonReactor, IEnergyDisplay, INamedContainerProvider {

    public static final int ENERGY_USE_PER_BLOCK = 650;
    public static final int DEFAULT_RANGE = 2;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(200000, 2000, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    public boolean onlyMineOres;
    public int checkX;
    public int checkY = -1;
    public int checkZ;
    private int oldEnergy;
    private int oldCheckX;
    private int oldCheckY;
    private int oldCheckZ;

    public TileEntityVerticalDigger() {
        super(ActuallyBlocks.VERTICAL_DIGGER.getTileEntityType(), 9);
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CheckX", this.checkX);
            compound.putInt("CheckY", this.checkY);
            compound.putInt("CheckZ", this.checkZ);
        }
        if (type != NBTType.SAVE_BLOCK || this.onlyMineOres) {
            compound.putBoolean("OnlyOres", this.onlyMineOres);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            this.checkX = compound.getInt("CheckX");
            this.checkY = compound.getInt("CheckY");
            this.checkZ = compound.getInt("CheckZ");
        }
        this.onlyMineOres = compound.getBoolean("OnlyOres");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {

            if (!this.isRedstonePowered && this.ticksElapsed % 5 == 0) {
                if (this.checkY != 0) {
                    int range = TileEntityPhantomface.upgradeRange(DEFAULT_RANGE, this.level, this.worldPosition);
                    if (this.checkY < 0) {
                        this.checkY = this.worldPosition.getY() - 1;
                        this.checkX = -range;
                        this.checkZ = -range;
                    }

                    if (this.checkY > 0) {
                        if (this.mine()) {
                            this.checkX++;
                            if (this.checkX > range) {
                                this.checkX = -range;
                                this.checkZ++;
                                if (this.checkZ > range) {
                                    this.checkZ = -range;
                                    this.checkY--;
                                }
                            }
                        }
                    }
                }
            }

            if ((this.oldEnergy != this.storage.getEnergyStored() || this.oldCheckX != this.checkX || this.oldCheckY != this.checkY || this.oldCheckZ != this.checkZ) && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
                this.oldCheckX = this.checkX;
                this.oldCheckY = this.checkY;
                this.oldCheckZ = this.checkZ;
            }
        }
    }

    private boolean mine() {
        int actualUse = ENERGY_USE_PER_BLOCK * (this.onlyMineOres
            ? 3
            : 1);
        if (this.storage.getEnergyStored() >= actualUse) {
            BlockPos pos = new BlockPos(this.worldPosition.getX() + this.checkX, this.checkY, this.worldPosition.getZ() + this.checkZ);

            BlockState state = this.level.getBlockState(pos);
            Block block = state.getBlock();
            ItemStack stack = block.getPickBlock(state, new BlockRayTraceResult(new Vector3d(0, 0, 0), Direction.DOWN, pos, false), this.level, pos, FakePlayerFactory.getMinecraft((ServerWorld) this.level));
            if (!block.isAir(this.level.getBlockState(pos), this.level, pos)) {
                if (block.getHarvestLevel(this.level.getBlockState(pos)) <= DrillItem.HARVEST_LEVEL && state.getDestroySpeed(this.level, pos) >= 0F && !(block instanceof IFluidBlock) && this.isMinable(block, stack)) {
                    List<ItemStack> drops = Block.getDrops(state, (ServerWorld) this.level, pos, this.level.getBlockEntity(pos));
                    float chance = WorldUtil.fireFakeHarvestEventsForDropChance(this, drops, this.level, pos);

                    if (chance > 0 && this.level.random.nextFloat() <= chance) {
                        if (StackUtil.canAddAll(this.inv, drops, false)) {
                            this.level.levelEvent(2001, pos, Block.getId(this.level.getBlockState(pos)));
                            this.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

                            StackUtil.addAll(this.inv, drops, false);
                            this.setChanged();

                            this.storage.extractEnergyInternal(actualUse, false);
                            this.shootParticles(pos.getX(), pos.getY(), pos.getZ());
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean isMinable(Block block, ItemStack stack) {
        if (block != null) {
            if (!this.isBlacklisted(block)) {
                if (!this.onlyMineOres) {
                    return true;
                } else {
                    if (StackUtil.isValid(stack)) {
                        // TODO: [port] come back and see if there is a tag for this

                        //                        int[] ids = OreDictionary.getOreIDs(stack);
                        //                        for (int id : ids) {
                        //                            String name = OreDictionary.getOreName(id);
                        //                            if (name.startsWith("ore") || name.startsWith("denseore")) {
                        //                                return true;
                        //                            }
                        //                        }

                        String reg = block.getRegistryName().toString();
                        if (!reg.isEmpty()) {
                            for (String string : ConfigStringListValues.MINER_EXTRA_WHITELIST.getValue()) {
                                if (reg.equals(string)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private void shootParticles(int endX, int endY, int endZ) {
        AssetUtil.spawnLaserWithTimeServer(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), endX, endY, endZ, new float[]{65F / 255F, 150F / 255F, 2F / 255F}, 10, 120, 0.1F, 0.8F);
    }

    private boolean isBlacklisted(Block block) {
        String reg = block.getRegistryName().toString();
        if (!reg.isEmpty()) {
            for (String string : ConfigStringListValues.MINER_BLACKLIST.getValue()) {
                if (reg.equals(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (stack, slot, automation) -> !automation;
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        if (buttonID == 0) {
            this.onlyMineOres = !this.onlyMineOres;
            this.sendUpdate();
        } else if (buttonID == 1) {
            this.checkX = 0;
            this.checkY = -1;
            this.checkZ = 0;
        }
    }

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerMiner(windowId, playerInventory, this);
    }
}
