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
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileEntityEmpowerer extends TileEntityInventoryBase{

    public int processTime;
    public int recipeForRenderIndex = -1;
    private int lastRecipe;

    public TileEntityEmpowerer(){
        super(1, "empowerer");
    }

    public static List<EmpowererRecipe> getRecipesForInput(ItemStack input){
        List<EmpowererRecipe> recipesThatWork = new ArrayList<EmpowererRecipe>();
        if(StackUtil.isValid(input)){
            for(EmpowererRecipe recipe : ActuallyAdditionsAPI.EMPOWERER_RECIPES){
                if(StackUtil.isValid(recipe.input) && recipe.input.isItemEqual(input)){
                    recipesThatWork.add(recipe);
                }
            }
        }
        return recipesThatWork;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.world.isRemote){
            List<EmpowererRecipe> recipes = getRecipesForInput(this.slots.getStackInSlot(0));
            if(!recipes.isEmpty()){
                for(EmpowererRecipe recipe : recipes){
                    TileEntityDisplayStand[] modifierStands = this.getFittingModifiers(recipe, recipe.time);
                    if(modifierStands != null){ //Meaning the display stands around match all the criteria
                        this.recipeForRenderIndex = ActuallyAdditionsAPI.EMPOWERER_RECIPES.indexOf(recipe);

                        this.processTime++;
                        boolean done = this.processTime >= recipe.time;

                        for(TileEntityDisplayStand stand : modifierStands){
                            stand.storage.extractEnergyInternal(recipe.energyPerStand/recipe.time, false);

                            if(done){
                                stand.slots.decrStackSize(0, 1);
                            }
                        }

                        if(this.processTime%5 == 0 && this.world instanceof WorldServer){
                            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, false, this.pos.getX()+0.5, this.pos.getY()+1.1, this.pos.getZ()+0.5, 2, 0, 0, 0, 0.1D);
                        }

                        if(done){
                            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.END_ROD, false, this.pos.getX()+0.5, this.pos.getY()+1.1, this.pos.getZ()+0.5, 100, 0, 0, 0, 0.25D);

                            this.slots.setStackInSlot(0, recipe.output.copy());
                            this.markDirty();

                            this.processTime = 0;
                            this.recipeForRenderIndex = -1;
                        }

                        break;
                    }
                }
            }
            else{
                this.processTime = 0;
                this.recipeForRenderIndex = -1;
            }

            if(this.lastRecipe != this.recipeForRenderIndex){
                this.lastRecipe = this.recipeForRenderIndex;
                this.sendUpdate();
            }
        }
    }

    private TileEntityDisplayStand[] getFittingModifiers(EmpowererRecipe recipe, int powerDivider){
        TileEntityDisplayStand[] modifierStands = new TileEntityDisplayStand[4];
        List<ItemStack> itemsStillNeeded = new ArrayList<ItemStack>(Arrays.asList(recipe.modifier1, recipe.modifier2, recipe.modifier3, recipe.modifier4));

        for(int i = 0; i < EnumFacing.HORIZONTALS.length; i++){
            EnumFacing facing = EnumFacing.HORIZONTALS[i];
            BlockPos offset = this.pos.offset(facing, 3);
            TileEntity tile = this.world.getTileEntity(offset);

            if(tile instanceof TileEntityDisplayStand){
                TileEntityDisplayStand stand = (TileEntityDisplayStand)tile;
                ItemStack standItem = stand.slots.getStackInSlot(0);
                int containPlace = ItemUtil.getPlaceAt(itemsStillNeeded, standItem, true);
                if(stand.storage.getEnergyStored() >= recipe.energyPerStand/powerDivider && containPlace != -1){
                    modifierStands[i] = stand;
                    itemsStillNeeded.remove(containPlace);
                }
                else{
                    return null;
                }
            }
            else{
                return null;
            }
        }

        return modifierStands;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type == NBTType.SAVE_TILE){
            compound.setInteger("ProcessTime", this.processTime);
        }
        if(type == NBTType.SYNC){
            compound.setInteger("RenderIndex", this.recipeForRenderIndex);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type == NBTType.SAVE_TILE){
            this.processTime = compound.getInteger("ProcessTime");
        }
        if(type == NBTType.SYNC){
            this.recipeForRenderIndex = compound.getInteger("RenderIndex");
        }
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return !getRecipesForInput(stack).isEmpty();
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack){
        return getRecipesForInput(stack).isEmpty();
    }

    @Override
    public int getMaxStackSizePerSlot(int slot){
        return 1;
    }
}
