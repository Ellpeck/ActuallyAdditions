/*
 * This file ("EntityFireworkBoxMinecart.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.entity;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFireworkBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFireworkBoxMinecart extends EntityRFMinecart{

    private int cooldownTimer;
    private boolean isPowered;

    public EntityFireworkBoxMinecart(World world){
        super(world, 20000, 1000);
    }

    @Override
    public void onActivatorRailPass(int x, int y, int z, boolean receivingPower){
        if(receivingPower != this.isPowered){
            this.isPowered = receivingPower;
        }
    }

    @Override
    public void onUpdate(){
        super.onUpdate();

        if(!this.worldObj.isRemote){
            if(this.cooldownTimer > 0 && this.isPowered){
                this.cooldownTimer--;
                if(this.cooldownTimer <= 0){
                    //TODO Check for power level here + make charging possible
                    TileEntityFireworkBox.spawnFireworks(this.worldObj, this.posX, this.posY, this.posZ);
                    this.storage.extractEnergy(TileEntityFireworkBox.USE_PER_SHOT, false);
                }
            }
            else{
                this.cooldownTimer = 100;
            }
        }
    }

    @Override
    public IBlockState getDisplayTile(){
        return InitBlocks.blockFireworkBox.getDefaultState();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);

        compound.setInteger("Cooldown", this.cooldownTimer);
        compound.setBoolean("Powered", this.isPowered);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);

        this.cooldownTimer = compound.getInteger("Cooldown");
        this.isPowered = compound.getBoolean("Powered");
    }
}
