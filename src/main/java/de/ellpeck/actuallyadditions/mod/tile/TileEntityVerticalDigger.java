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
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityVerticalDigger extends TileEntityInventoryBase implements IButtonReactor, IEnergyDisplay, MenuProvider {

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

    public TileEntityVerticalDigger(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.VERTICAL_DIGGER.getTileEntityType(), pos, state, 9);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
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
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            this.checkX = compound.getInt("CheckX");
            this.checkY = compound.getInt("CheckY");
            this.checkZ = compound.getInt("CheckZ");
        }
        this.onlyMineOres = compound.getBoolean("OnlyOres");
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityVerticalDigger tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityVerticalDigger tile) {
            tile.serverTick();

            if (!tile.isRedstonePowered && tile.ticksElapsed % 5 == 0) {
                if (tile.checkY != 0) {
                    int range = TileEntityPhantomface.upgradeRange(DEFAULT_RANGE, level, pos);
                    if (tile.checkY < 0) {
                        tile.checkY = tile.worldPosition.getY() - 1;
                        tile.checkX = -range;
                        tile.checkZ = -range;
                    }

                    if (tile.checkY > 0) {
                        if (tile.mine()) {
                            tile.checkX++;
                            if (tile.checkX > range) {
                                tile.checkX = -range;
                                tile.checkZ++;
                                if (tile.checkZ > range) {
                                    tile.checkZ = -range;
                                    tile.checkY--;
                                }
                            }
                        }
                    }
                }
            }

            if ((tile.oldEnergy != tile.storage.getEnergyStored() || tile.oldCheckX != tile.checkX || tile.oldCheckY != tile.checkY || tile.oldCheckZ != tile.checkZ) && tile.sendUpdateWithInterval()) {
                tile.oldEnergy = tile.storage.getEnergyStored();
                tile.oldCheckX = tile.checkX;
                tile.oldCheckY = tile.checkY;
                tile.oldCheckZ = tile.checkZ;
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
            ItemStack stack = block.getCloneItemStack(state, new BlockHitResult(new Vec3(0, 0, 0), Direction.DOWN, pos, false), this.level, pos, FakePlayerFactory.getMinecraft((ServerLevel) this.level));
            if (!state.isAir()) {
                //block.getHarvestLevel(state) <= DrillItem.HARVEST_LEVEL
                if (TierSortingRegistry.isCorrectTierForDrops(Tiers.NETHERITE, state) && state.getDestroySpeed(this.level, pos) >= 0F && !(block instanceof IFluidBlock) && this.isMinable(block, stack)) {
                    List<ItemStack> drops = Block.getDrops(state, (ServerLevel) this.level, pos, this.level.getBlockEntity(pos));
                    float chance = WorldUtil.fireFakeHarvestEventsForDropChance(this, drops, this.level, pos);

                    if (chance > 0 && this.level.random.nextFloat() <= chance) {
                        if (StackUtil.canAddAll(this.inv, drops, false)) {
                            this.level.levelEvent(2001, pos, Block.getId(state));
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

                        String reg = ForgeRegistries.BLOCKS.getKey(block).toString();
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
        AssetUtil.spawnLaserWithTimeServer(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), endX, endY, endZ, 0x429602, 10, 120, 0.1F, 0.8F);
    }

    private boolean isBlacklisted(Block block) {
        String reg =  ForgeRegistries.BLOCKS.getKey(block).toString();
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
    public void onButtonPressed(int buttonID, Player player) {
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
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerMiner(windowId, playerInventory, this);
    }
}
