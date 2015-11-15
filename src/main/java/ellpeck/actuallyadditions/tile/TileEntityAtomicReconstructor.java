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
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
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

public class TileEntityAtomicReconstructor extends TileEntityBase implements IEnergyReceiver{

    public EnergyStorage storage = new EnergyStorage(300000);

    private int currentTime;

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!this.worldObj.isRemote){
            int usePerBlock = ConfigIntValues.RECONSTRUCTOR_USE_PER_BLOCK.getValue();
            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && this.storage.getEnergyStored() >= usePerBlock){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        ForgeDirection sideToManipulate = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
                        //Extract energy for shooting the laser itself too!
                        this.storage.extractEnergy(usePerBlock*2, false);

                        int distance = ConfigIntValues.RECONSTRUCTOR_DISTANCE.getValue();
                        for(int i = 0; i < distance; i++){
                            WorldPos coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, i);
                            this.damagePlayer(coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ());

                            if(coordsBlock != null){
                                if(!coordsBlock.getBlock().isAir(coordsBlock.getWorld(), coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ())){
                                    PacketHandler.theNetwork.sendToAllAround(new PacketAtomicReconstructor(xCoord, yCoord, zCoord, coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ()), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));

                                    int range = ConfigIntValues.RECONSTRCUTOR_RANGE.getValue();

                                    //Converting the Blocks
                                    for(int reachX = -range; reachX < range+1; reachX++){
                                        for(int reachZ = -range; reachZ < range+1; reachZ++){
                                            for(int reachY = -range; reachY < range+1; reachY++){
                                                if(this.storage.getEnergyStored() >= usePerBlock){
                                                    WorldPos pos = new WorldPos(worldObj, coordsBlock.getX()+reachX, coordsBlock.getY()+reachY, coordsBlock.getZ()+reachZ);
                                                    ReconstructorRecipeHandler.Recipe recipe = ReconstructorRecipeHandler.getRecipe(new ItemStack(pos.getBlock(), pos.getMetadata()));
                                                    if(recipe != null){
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
                                                            this.storage.extractEnergy(usePerBlock, false);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    //Converting the Items
                                    ArrayList<EntityItem> items = (ArrayList<EntityItem>)worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(coordsBlock.getX()-range, coordsBlock.getY()-range, coordsBlock.getZ()-range, coordsBlock.getX()+range, coordsBlock.getY()+range, coordsBlock.getZ()+range));
                                    for(EntityItem item : items){
                                        if(this.storage.getEnergyStored() >= usePerBlock){
                                            ItemStack stack = item.getEntityItem();
                                            if(stack != null){
                                                ReconstructorRecipeHandler.Recipe recipe = ReconstructorRecipeHandler.getRecipe(stack);
                                                if(recipe != null){
                                                    ItemStack output = recipe.getFirstOutput();
                                                    if(output != null){
                                                        ItemStack outputCopy = output.copy();
                                                        outputCopy.stackSize = stack.stackSize;
                                                        item.setEntityItemStack(outputCopy);

                                                        this.storage.extractEnergy(usePerBlock, false);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                }
                                if(i >= distance-1){
                                    PacketHandler.theNetwork.sendToAllAround(new PacketAtomicReconstructor(xCoord, yCoord, zCoord, coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ()), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));
                                }
                            }
                        }
                    }
                }
                else{
                    this.currentTime = ConfigIntValues.RECONSTRUCTOR_COOLDOWN_TIMER.getValue();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void damagePlayer(int x, int y, int z){
        ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>)worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1));
        for(EntityLivingBase entity : entities){
            entity.attackEntityFrom(DamageSources.DAMAGE_ATOMIC_RECONSTRUCTOR, 16F);
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
        this.storage.writeToNBT(compound);
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
}
