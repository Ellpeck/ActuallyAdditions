/*
 * This file ("EntityWorm.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.entity;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class EntityWorm extends Entity{

    public int timer;

    public EntityWorm(World world){
        super(world);
        this.setEntityBoundingBox(null);
    }

    public static boolean canWormify(World world, BlockPos pos, IBlockState state){
        Block block = state.getBlock();
        boolean rightBlock = block instanceof BlockFarmland || block == Blocks.GRASS || block == Blocks.GRASS_PATH || (block == Blocks.DIRT && state.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT);
        if(rightBlock){
            BlockPos posUp = pos.up();
            IBlockState stateUp = world.getBlockState(posUp);
            Block blockUp = stateUp.getBlock();
            return blockUp instanceof IPlantable || blockUp instanceof BlockBush || blockUp.isReplaceable(world, posUp);
        }
        else{
            return false;
        }
    }

    @Override
    protected void entityInit(){

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound){
        this.timer = compound.getInteger("Timer");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound){
        compound.setInteger("Timer", this.timer);
    }

    @Override
    public void onUpdate(){
        this.onEntityUpdate();
    }

    @Override
    public void onEntityUpdate(){
        if(!this.worldObj.isRemote){
            this.timer++;

            if(this.timer%50 == 0){
                for(int x = -1; x <= 1; x++){
                    for(int z = -1; z <= 1; z++){
                        BlockPos pos = new BlockPos(this.posX+x, this.posY, this.posZ+z);
                        IBlockState state = this.worldObj.getBlockState(pos);
                        Block block = state.getBlock();
                        boolean isMiddlePose = x == 0 && z == 0;

                        if(canWormify(this.worldObj, pos, state)){
                            boolean isFarmland = block instanceof BlockFarmland;

                            if(!isFarmland || state.getValue(BlockFarmland.MOISTURE) < 7){
                                if(isMiddlePose || this.worldObj.rand.nextFloat() >= 0.45F){
                                    IBlockState stateToModify = isFarmland ? state : Blocks.FARMLAND.getDefaultState();
                                    this.worldObj.setBlockState(pos, stateToModify.withProperty(BlockFarmland.MOISTURE, 7), 2);

                                    if(!isFarmland){
                                        this.worldObj.setBlockToAir(pos.up());
                                    }
                                }
                            }

                            if(isFarmland && this.worldObj.rand.nextFloat() >= 0.95F){
                                BlockPos plant = pos.up();
                                if(!this.worldObj.isAirBlock(plant)){
                                    IBlockState plantState = this.worldObj.getBlockState(plant);
                                    Block plantBlock = plantState.getBlock();

                                    if((plantBlock instanceof IGrowable || plantBlock instanceof IPlantable) && !(plantBlock instanceof BlockGrass)){
                                        plantBlock.updateTick(this.worldObj, plant, plantState, this.worldObj.rand);

                                        IBlockState newState = this.worldObj.getBlockState(plant);
                                        if(newState.getBlock().getMetaFromState(newState) != plantBlock.getMetaFromState(plantState)){
                                            this.worldObj.playEvent(2005, plant, 0);
                                        }
                                    }
                                }
                            }
                        }
                        else if(isMiddlePose){
                            this.setDead();
                        }
                    }
                }
            }

            int dieTime = ConfigIntValues.WORMS_DIE_TIME.getValue();
            if(dieTime > 0 && this.timer >= dieTime){
                this.setDead();
            }
        }
    }
}
