/*
 * This file ("TileEntityAtomicReconstructor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.actuallyadditions.items.IReconstructorLens;
import ellpeck.actuallyadditions.misc.DamageSources;
import ellpeck.actuallyadditions.network.PacketAtomicReconstructor;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.recipe.ReconstructorRecipeHandler;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityAtomicReconstructor extends TileEntityInventoryBase implements IEnergyReceiver{

    public EnergyStorage storage = new EnergyStorage(3000000);

    private int currentTime;

    public static final int ENERGY_USE = 200;

    public TileEntityAtomicReconstructor(){
        super(1, "reconstructor");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && this.storage.getEnergyStored() >= ENERGY_USE){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        ForgeDirection sideToManipulate = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
                        //Extract energy for shooting the laser itself too!
                        this.storage.extractEnergy(ENERGY_USE, false);
                        if(this.storage.getEnergyStored() >= ENERGY_USE){

                            //The Lens the Reconstructor currently has installed
                            ReconstructorRecipeHandler.LensType currentLens = this.getCurrentLens();
                            int distance = currentLens.getDistance();
                            for(int i = 0; i < distance; i++){
                                WorldPos hitBlock = WorldUtil.getCoordsFromSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, i);

                                float damage = currentLens == ReconstructorRecipeHandler.LensType.JUST_DAMAGE ? 20F : 5F;
                                this.damagePlayer(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), damage);

                                if(hitBlock != null){
                                    if(!hitBlock.getBlock().isAir(hitBlock.getWorld(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ())){
                                        this.shootLaser(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), currentLens);

                                        //Detonation
                                        if(currentLens == ReconstructorRecipeHandler.LensType.DETONATION){
                                            int use = ENERGY_USE+500000;
                                            if(this.storage.getEnergyStored() >= use){
                                                this.worldObj.newExplosion(null, hitBlock.getX()+0.5, hitBlock.getY()+0.5, hitBlock.getZ()+0.5, 10F, true, true);
                                                this.storage.extractEnergy(use, false);
                                            }
                                        }
                                        //Conversion Recipes
                                        else if(currentLens.hasRecipes){
                                            int range = 2;

                                            //Converting the Blocks
                                            for(int reachX = -range; reachX < range+1; reachX++){
                                                for(int reachZ = -range; reachZ < range+1; reachZ++){
                                                    for(int reachY = -range; reachY < range+1; reachY++){
                                                        if(this.storage.getEnergyStored() >= ENERGY_USE){
                                                            WorldPos pos = new WorldPos(worldObj, hitBlock.getX()+reachX, hitBlock.getY()+reachY, hitBlock.getZ()+reachZ);
                                                            ArrayList<ReconstructorRecipeHandler.Recipe> recipes = ReconstructorRecipeHandler.getRecipes(new ItemStack(pos.getBlock(), 1, pos.getMetadata()));
                                                            for(ReconstructorRecipeHandler.Recipe recipe : recipes){
                                                                if(recipe != null && this.storage.getEnergyStored() >= ENERGY_USE+recipe.energyUse && recipe.type == currentLens){
                                                                    ItemStack output = recipe.getFirstOutput();
                                                                    if(output != null){
                                                                        if(output.getItem() instanceof ItemBlock){
                                                                            this.worldObj.playAuxSFX(2001, pos.getX(), pos.getY(), pos.getZ(), Block.getIdFromBlock(pos.getBlock())+(pos.getMetadata() << 12));
                                                                            pos.setBlock(Block.getBlockFromItem(output.getItem()), output.getItemDamage(), 2);
                                                                        }
                                                                        else{
                                                                            EntityItem item = new EntityItem(worldObj, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, output.copy());
                                                                            worldObj.spawnEntityInWorld(item);
                                                                        }
                                                                        this.storage.extractEnergy(ENERGY_USE+recipe.energyUse, false);
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            //Converting the Items
                                            ArrayList<EntityItem> items = (ArrayList<EntityItem>)worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(hitBlock.getX()-range, hitBlock.getY()-range, hitBlock.getZ()-range, hitBlock.getX()+range, hitBlock.getY()+range, hitBlock.getZ()+range));
                                            for(EntityItem item : items){
                                                if(this.storage.getEnergyStored() >= ENERGY_USE){
                                                    ItemStack stack = item.getEntityItem();
                                                    if(stack != null){
                                                        ArrayList<ReconstructorRecipeHandler.Recipe> recipes = ReconstructorRecipeHandler.getRecipes(stack);
                                                        for(ReconstructorRecipeHandler.Recipe recipe : recipes){
                                                            if(recipe != null && this.storage.getEnergyStored() >= ENERGY_USE+recipe.energyUse && recipe.type == currentLens){
                                                                ItemStack output = recipe.getFirstOutput();
                                                                if(output != null){
                                                                    ItemStack outputCopy = output.copy();
                                                                    outputCopy.stackSize = stack.stackSize;
                                                                    item.setEntityItemStack(outputCopy);

                                                                    this.storage.extractEnergy(ENERGY_USE+recipe.energyUse, false);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    else if(i >= distance-1){
                                        this.shootLaser(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), currentLens);
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    this.currentTime = 100;
                }
            }
        }
    }

    private void shootLaser(int endX, int endY, int endZ, ReconstructorRecipeHandler.LensType currentLens){
        PacketHandler.theNetwork.sendToAllAround(new PacketAtomicReconstructor(xCoord, yCoord, zCoord, endX, endY, endZ, currentLens), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));
    }

    public ReconstructorRecipeHandler.LensType getCurrentLens(){
        if(this.slots[0] != null){
            if(this.slots[0].getItem() instanceof IReconstructorLens){
                return ((IReconstructorLens)this.slots[0].getItem()).getLensType();
            }
        }
        return ReconstructorRecipeHandler.LensType.NONE;
    }

    @SuppressWarnings("unchecked")
    public void damagePlayer(int x, int y, int z, float damage){
        ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>)worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1));
        for(EntityLivingBase entity : entities){
            entity.attackEntityFrom(DamageSources.DAMAGE_ATOMIC_RECONSTRUCTOR, damage);
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("CurrentTime", this.currentTime);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTime = compound.getInteger("CurrentTime");
        this.storage.readFromNBT(compound);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return stack != null && stack.getItem() instanceof IReconstructorLens;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return true;
    }
}
