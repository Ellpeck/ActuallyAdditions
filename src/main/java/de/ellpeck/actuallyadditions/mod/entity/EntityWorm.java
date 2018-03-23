/*
 * This file ("EntityWorm.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.entity;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.DefaultFarmerBehavior;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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
        boolean rightBlock = block instanceof BlockFarmland || block instanceof BlockDirt || block instanceof BlockGrass;
        if(rightBlock){
            BlockPos posUp = pos.up();
            IBlockState stateUp = world.getBlockState(posUp);
            Block blockUp = stateUp.getBlock();
            return blockUp instanceof IPlantable || blockUp instanceof BlockBush || blockUp.isReplaceable(world, posUp);
        }
        else if(state.getBlock().getRegistryName().equals("biomesoplenty:grass")){
            for(IProperty<?> property : state.getPropertyKeys()){
                if(property.getName().equals("variant")){
                    String grassType = ((PropertyEnum) state.getValue(property)).getName();
                    switch (grassType){
                        case "LOAMY":
                        case "SANDY":
                        case "SILTY":
                        case "ORIGIN":
                        case "DAISY":
                            BlockPos posUp = pos.up();
                            IBlockState stateUp = world.getBlockState(posUp);
                            Block blockUp = stateUp.getBlock();
                            return blockUp instanceof IPlantable || blockUp instanceof BlockBush || blockUp.isReplaceable(world, posUp);
                    }
                }
            }
            return false;
        }
        else{
            switch (block.getRegistryName().toString()){
                case "biomesoplenty:dirt":
                case "biomesoplenty:farmland_0":
                case "biomesoplenty:farmland_1":
                    BlockPos posUp = pos.up();
                    IBlockState stateUp = world.getBlockState(posUp);
                    Block blockUp = stateUp.getBlock();
                    return blockUp instanceof IPlantable || blockUp instanceof BlockBush || blockUp.isReplaceable(world, posUp);
            }
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
        if(!this.world.isRemote){
            this.timer++;

            if(this.timer%50 == 0){
                for(int x = -1; x <= 1; x++){
                    for(int z = -1; z <= 1; z++){
                        BlockPos pos = new BlockPos(this.posX+x, this.posY, this.posZ+z);
                        IBlockState state = this.world.getBlockState(pos);
                        Block block = state.getBlock();
                        boolean isMiddlePose = x == 0 && z == 0;

                        if(canWormify(this.world, pos, state)){
                            boolean isFarmland = block instanceof BlockFarmland;

                            if(!isFarmland || state.getValue(BlockFarmland.MOISTURE) < 7){
                                if(isMiddlePose || this.world.rand.nextFloat() >= 0.45F){

                                	if(!isFarmland) DefaultFarmerBehavior.useHoeAt(world, pos);
                                	state = this.world.getBlockState(pos);
                                	isFarmland = state.getBlock() instanceof BlockFarmland;

                                    if(isFarmland) this.world.setBlockState(pos, state.withProperty(BlockFarmland.MOISTURE, 7), 2);
                                }
                            }

                            if(isFarmland && this.world.rand.nextFloat() >= 0.95F){
                                BlockPos plant = pos.up();
                                if(!this.world.isAirBlock(plant)){
                                    IBlockState plantState = this.world.getBlockState(plant);
                                    Block plantBlock = plantState.getBlock();

                                    if((plantBlock instanceof IGrowable || plantBlock instanceof IPlantable) && !(plantBlock instanceof BlockGrass)){
                                        plantBlock.updateTick(this.world, plant, plantState, this.world.rand);

                                        IBlockState newState = this.world.getBlockState(plant);
                                        if(newState != plantState){
                                            this.world.playEvent(2005, plant, 0);
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
