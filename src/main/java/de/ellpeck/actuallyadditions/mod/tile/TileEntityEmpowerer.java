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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class TileEntityEmpowerer extends TileEntityInventoryBase {

    public int processTime;
    public int recipeForRenderIndex = -1;
    private int lastRecipe;

    public TileEntityEmpowerer() {
        super(1, "empowerer");
    }

    @Deprecated //Use findMatchingRecipe
    public static List<EmpowererRecipe> getRecipesForInput(ItemStack input) {
        List<EmpowererRecipe> recipesThatWork = new ArrayList<EmpowererRecipe>();
        if (StackUtil.isValid(input)) {
            for (EmpowererRecipe recipe : ActuallyAdditionsAPI.EMPOWERER_RECIPES) {
                if (recipe.getInput().apply(input)) {
                    recipesThatWork.add(recipe);
                }
            }
        }
        return recipesThatWork;
    }
    
    public static boolean isPossibleInput(ItemStack stack) {
        for(EmpowererRecipe r : ActuallyAdditionsAPI.EMPOWERER_RECIPES) if(r.getInput().apply(stack)) return true;
        return false;
    }

    @Nullable
    public static EmpowererRecipe findMatchingRecipe(ItemStack base, ItemStack stand1, ItemStack stand2, ItemStack stand3, ItemStack stand4) {
        for (EmpowererRecipe r : ActuallyAdditionsAPI.EMPOWERER_RECIPES) {
            if (r.matches(base, stand1, stand2, stand3, stand4)) return r;
        }
        return null;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.world.isRemote) {
            TileEntityDisplayStand[] stands = this.getNearbyStands();
            if (stands != null) {
                EmpowererRecipe recipe = findMatchingRecipe(this.inv.getStackInSlot(0), stands[0].getStack(), stands[1].getStack(), stands[2].getStack(), stands[3].getStack());
                if (recipe != null) {
                    this.recipeForRenderIndex = ActuallyAdditionsAPI.EMPOWERER_RECIPES.indexOf(recipe);

                    this.processTime++;
                    boolean done = this.processTime >= recipe.getTime();

                    for (TileEntityDisplayStand stand : stands) {
                        stand.storage.extractEnergyInternal(recipe.getEnergyPerStand() / recipe.getTime(), false);

                        if (done) {
                            stand.inv.getStackInSlot(0).shrink(1);
                            stand.markDirty();
                        }
                    }

                    if (this.processTime % 5 == 0 && this.world instanceof WorldServer) {
                        ((WorldServer) this.world).spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, false, this.pos.getX() + 0.5, this.pos.getY() + 1.1, this.pos.getZ() + 0.5, 2, 0, 0, 0, 0.1D);
                    }

                    if (done) {
                        ((WorldServer) this.world).spawnParticle(EnumParticleTypes.END_ROD, false, this.pos.getX() + 0.5, this.pos.getY() + 1.1, this.pos.getZ() + 0.5, 100, 0, 0, 0, 0.25D);

                        this.inv.setStackInSlot(0, recipe.getOutput().copy());
                        this.markDirty();

                        this.processTime = 0;
                        this.recipeForRenderIndex = -1;
                    }
                } else {
                    this.processTime = 0;
                    this.recipeForRenderIndex = -1;
                }

                if (this.lastRecipe != this.recipeForRenderIndex) {
                    this.lastRecipe = this.recipeForRenderIndex;
                    this.sendUpdate();
                }
            }
        }
    }

    private TileEntityDisplayStand[] getNearbyStands() {
        TileEntityDisplayStand[] stands = new TileEntityDisplayStand[4];

        for (int i = 0; i < EnumFacing.HORIZONTALS.length; i++) {
            EnumFacing facing = EnumFacing.HORIZONTALS[i];
            BlockPos offset = this.pos.offset(facing, 3);
            TileEntity tile = this.world.getTileEntity(offset);
            if (tile instanceof TileEntityDisplayStand) stands[i] = (TileEntityDisplayStand) tile;
            else return null;
        }

        return stands;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE) {
            compound.setInteger("ProcessTime", this.processTime);
        }
        if (type == NBTType.SYNC) {
            compound.setInteger("RenderIndex", this.recipeForRenderIndex);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE) {
            this.processTime = compound.getInteger("ProcessTime");
        }
        if (type == NBTType.SYNC) {
            this.recipeForRenderIndex = compound.getInteger("RenderIndex");
        }
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public boolean canInsert(int index, ItemStack stack, boolean automation) {
        return !automation || !getRecipesForInput(stack).isEmpty();
    }

    @Override
    public boolean canExtract(int index, ItemStack stack, boolean automation) {
        return !automation || getRecipesForInput(stack).isEmpty();
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 1;
    }
}
