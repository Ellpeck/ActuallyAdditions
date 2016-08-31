/*
 * This file ("TileEntityEmpowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
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

    private int processTime;

    public TileEntityEmpowerer(){
        super(1, "empowerer");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            EmpowererRecipe recipe = getRecipeForInput(this.slots[0]);
            if(recipe != null){
                TileEntityDisplayStand[] modifierStands = this.getFittingModifiers(recipe, recipe.time);
                if(modifierStands != null){ //Meaning the display stands around match all the criteria
                    boolean lessParticles = ConfigBoolValues.LESS_PARTICLES.isEnabled();

                    this.processTime++;
                    boolean done = this.processTime >= recipe.time;

                    for(TileEntityDisplayStand stand : modifierStands){
                        stand.storage.extractEnergy(recipe.energyPerStand/recipe.time, false);

                        if(done){
                            stand.decrStackSize(0, 1);
                        }

                        if(!lessParticles){
                            AssetUtil.shootParticles(this.worldObj, stand.getPos().getX(), stand.getPos().getY()+0.45F, stand.getPos().getZ(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), recipe.particleColor, 8, 0.5F, 1F);
                        }
                    }

                    if(!lessParticles && this.processTime%5 == 0 && this.worldObj instanceof WorldServer){
                        ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, false, this.pos.getX()+0.5, this.pos.getY()+1.1, this.pos.getZ()+0.5, 3, 0, 0, 0, 0.1D);
                    }

                    if(done){
                        if(!lessParticles){
                            ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.END_ROD, false, this.pos.getX()+0.5, this.pos.getY()+1.1, this.pos.getZ()+0.5, 300, 0, 0, 0, 0.25D);
                        }

                        this.slots[0] = recipe.output.copy();
                        this.markDirty();

                        this.processTime = 0;
                    }
                }
            }
            else{
                this.processTime = 0;
            }
        }
    }

    private TileEntityDisplayStand[] getFittingModifiers(EmpowererRecipe recipe, int powerDivider){
        TileEntityDisplayStand[] modifierStands = new TileEntityDisplayStand[4];
        List<ItemStack> itemsStillNeeded = new ArrayList<ItemStack>(Arrays.asList(recipe.modifier1, recipe.modifier2, recipe.modifier3, recipe.modifier4));

        for(int i = 0; i < EnumFacing.HORIZONTALS.length; i++){
            EnumFacing facing = EnumFacing.HORIZONTALS[i];
            BlockPos offset = this.pos.offset(facing, 3);
            TileEntity tile = this.worldObj.getTileEntity(offset);

            if(tile != null && tile instanceof TileEntityDisplayStand){
                TileEntityDisplayStand stand = (TileEntityDisplayStand)tile;
                ItemStack standItem = stand.getStackInSlot(0);
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
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type == NBTType.SAVE_TILE){
            this.processTime = compound.getInteger("ProcessTime");
        }
    }

    public static EmpowererRecipe getRecipeForInput(ItemStack input){
        if(input != null){
            for(EmpowererRecipe recipe : ActuallyAdditionsAPI.EMPOWERER_RECIPES){
                if(recipe.input != null && recipe.input.isItemEqual(input)){
                    return recipe;
                }
            }
        }
        return null;
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public void markDirty(){
        super.markDirty();
        this.sendUpdate();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return getRecipeForInput(stack) != null;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction){
        return this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return getRecipeForInput(stack) == null;
    }

    @Override
    public int getInventoryStackLimit(){
        return 1;
    }
}
