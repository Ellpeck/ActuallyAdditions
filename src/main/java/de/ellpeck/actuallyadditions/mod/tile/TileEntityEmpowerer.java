/*
 * This file ("TileEntityEmpowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.crafting.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;

public class TileEntityEmpowerer extends TileEntityInventoryBase {

    public int processTime;
    private EmpowererRecipe currentRecipe = null;
    private EmpowererRecipe lastRecipe = null;

    public EmpowererRecipe getCurrentRecipe(){
        return this.currentRecipe;
    }

    public TileEntityEmpowerer(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.EMPOWERER.getTileEntityType(), pos, state, 1);
    }

    public static boolean isPossibleInput(ItemStack stack) {
        for (EmpowererRecipe r : ServerLifecycleHooks.getCurrentServer().getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.EMPOWERING)) {
            if (r.getInput().test(stack)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static EmpowererRecipe findMatchingRecipe(ItemStack base, ItemStack stand1, ItemStack stand2, ItemStack stand3, ItemStack stand4) {
        for (EmpowererRecipe r : ServerLifecycleHooks.getCurrentServer().getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.EMPOWERING)) {
            if (r.matches(base, stand1, stand2, stand3, stand4)) {
                return r;
            }
        }
        return null;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityEmpowerer tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityEmpowerer tile) {
            tile.serverTick();

            TileEntityDisplayStand[] stands = tile.getNearbyStands();
            if (stands != null) {
                EmpowererRecipe recipe = findMatchingRecipe(tile.inv.getStackInSlot(0), stands[0].getStack(), stands[1].getStack(), stands[2].getStack(), stands[3].getStack());
                if (recipe != null) {
                    tile.currentRecipe = recipe;

                    boolean hasPower = true;

                    for (TileEntityDisplayStand stand : stands) {
                        if (stand.storage.getEnergyStored() < recipe.getEnergyPerStand() / recipe.getTime()) {
                            hasPower = false;
                        }
                    }

                    if (hasPower) {

                        tile.processTime++;
                        boolean done = tile.processTime >= recipe.getTime();

                        for (TileEntityDisplayStand stand : stands) {
                            stand.storage.extractEnergyInternal(recipe.getEnergyPerStand() / recipe.getTime(), false);

                            if (done) {
                                stand.inv.getStackInSlot(0).shrink(1);
                                stand.setChanged();
                            }
                        }

                        if (tile.processTime % 5 == 0 && level instanceof ServerLevel) {
                            ((ServerLevel) level).sendParticles(ParticleTypes.FIREWORK, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 2, 0, 0, 0, 0.1D);
                        }

                        if (done) {
                            ((ServerLevel) level).sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 100, 0, 0, 0, 0.25D);

                            tile.inv.setStackInSlot(0, recipe.getOutput().copy());
                            tile.setChanged();

                            tile.processTime = 0;
                            tile.currentRecipe = null;
                        }
                    }
                } else {
                    tile.processTime = 0;
                    tile.currentRecipe = null;
                }

                if (tile.lastRecipe != tile.currentRecipe) {
                    tile.lastRecipe = tile.currentRecipe;
                    tile.sendUpdate();
                }
            }
        }
    }

    private TileEntityDisplayStand[] getNearbyStands() {
        TileEntityDisplayStand[] stands = new TileEntityDisplayStand[4];

        for (int i = 0; i <= 3; i++) {
            Direction facing = Direction.from2DDataValue(i);
            BlockPos offset = this.worldPosition.relative(facing, 3);
            BlockEntity tile = this.level.getBlockEntity(offset);
            if (tile instanceof TileEntityDisplayStand) {
                stands[i] = (TileEntityDisplayStand) tile;
            } else {
                return null;
            }
        }

        return stands;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE) {
            compound.putInt("ProcessTime", this.processTime);
        }
        if (type == NBTType.SYNC) {
            if (this.currentRecipe != null)
                compound.putString("CurrentRecipe", this.currentRecipe.getId().toString());
            else
                compound.putString("CurrentRecipe", "");
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE) {
            this.processTime = compound.getInt("ProcessTime");
        }
        if (type == NBTType.SYNC && compound.contains("CurrentRecipe")) {
            if (!compound.getString("CurrentRecipe").isEmpty()) {
                ResourceLocation id = new ResourceLocation(compound.getString("CurrentRecipe"));
                for (EmpowererRecipe empowererRecipe : ActuallyAdditionsAPI.EMPOWERER_RECIPES) {
                    if (empowererRecipe.getId().equals(id)) {
                        this.currentRecipe = empowererRecipe;
                        break;
                    }
                }
            }
            else
                this.currentRecipe = null;
        }
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || isPossibleInput(stack);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || !isPossibleInput(this.inv.getStackInSlot(0));
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 1;
    }
}
