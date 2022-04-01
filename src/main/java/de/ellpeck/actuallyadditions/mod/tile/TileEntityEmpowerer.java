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
import de.ellpeck.actuallyadditions.mod.crafting.SolidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class TileEntityEmpowerer extends TileEntityInventoryBase {

    public int processTime;
    private EmpowererRecipe currentRecipe = null;
    private EmpowererRecipe lastRecipe = null;

    public EmpowererRecipe getCurrentRecipe(){
        return this.currentRecipe;
    }

    public TileEntityEmpowerer() {
        super(ActuallyBlocks.EMPOWERER.getTileEntityType(), 1);
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

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.level.isClientSide) {
            TileEntityDisplayStand[] stands = this.getNearbyStands();
            if (stands != null) {
                EmpowererRecipe recipe = findMatchingRecipe(this.inv.getStackInSlot(0), stands[0].getStack(), stands[1].getStack(), stands[2].getStack(), stands[3].getStack());
                if (recipe != null) {
                    currentRecipe = recipe;

                    boolean hasPower = true;

                    for (TileEntityDisplayStand stand : stands) {
                        if (stand.storage.getEnergyStored() < recipe.getEnergyPerStand() / recipe.getTime()) {
                            hasPower = false;
                        }
                    }

                    if (hasPower) {

                        this.processTime++;
                        boolean done = this.processTime >= recipe.getTime();

                        for (TileEntityDisplayStand stand : stands) {
                            stand.storage.extractEnergyInternal(recipe.getEnergyPerStand() / recipe.getTime(), false);

                            if (done) {
                                stand.inv.getStackInSlot(0).shrink(1);
                                stand.setChanged();
                            }
                        }

                        if (this.processTime % 5 == 0 && this.level instanceof ServerWorld) {
                            ((ServerWorld) this.level).sendParticles(ParticleTypes.FIREWORK, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.1, this.worldPosition.getZ() + 0.5, 2, 0, 0, 0, 0.1D);
                        }

                        if (done) {
                            ((ServerWorld) this.level).sendParticles(ParticleTypes.END_ROD, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.1, this.worldPosition.getZ() + 0.5, 100, 0, 0, 0, 0.25D);

                            this.inv.setStackInSlot(0, recipe.getOutput().copy());
                            this.setChanged();

                            this.processTime = 0;
                            this.currentRecipe = null;
                        }
                    }
                } else {
                    this.processTime = 0;
                    this.currentRecipe = null;
                }

                if (this.lastRecipe != this.currentRecipe) {
                    this.lastRecipe = this.currentRecipe;
                    this.sendUpdate();
                }
            }
        }
    }

    private TileEntityDisplayStand[] getNearbyStands() {
        TileEntityDisplayStand[] stands = new TileEntityDisplayStand[4];

        for (int i = 0; i <= 3; i++) {
            Direction facing = Direction.from2DDataValue(i);
            BlockPos offset = this.worldPosition.relative(facing, 3);
            TileEntity tile = this.level.getBlockEntity(offset);
            if (tile instanceof TileEntityDisplayStand) {
                stands[i] = (TileEntityDisplayStand) tile;
            } else {
                return null;
            }
        }

        return stands;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
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
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE) {
            this.processTime = compound.getInt("ProcessTime");
        }
        if (type == NBTType.SYNC && compound.contains("CurrentRecipe")) {
            if (!compound.getString("CurrentRecipe").equals("")) {
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
