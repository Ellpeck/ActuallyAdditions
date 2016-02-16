/*
 * This file ("TileEntityFireworkBox.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFireworkBox extends TileEntityBase implements IEnergyReceiver, IRedstoneToggle, IEnergyDisplay, IEnergySaver{

    public static final int USE_PER_SHOT = 300;
    public EnergyStorage storage = new EnergyStorage(20000);
    private int timeUntilNextFirework;
    private boolean activateOnceWithSignal;
    private int oldEnergy;

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        if(!this.worldObj.isRemote){
            if(!this.isRedstonePowered && !this.activateOnceWithSignal){
                if(this.timeUntilNextFirework > 0){
                    this.timeUntilNextFirework--;
                    if(this.timeUntilNextFirework <= 0){
                        this.doWork();
                    }
                }
                else{
                    this.timeUntilNextFirework = 100;
                }
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    private void doWork(){
        if(this.storage.getEnergyStored() >= USE_PER_SHOT){
            int range = 4;
            int amount = Util.RANDOM.nextInt(5)+1;
            for(int i = 0; i < amount; i++){
                ItemStack firework = this.makeFirework();

                double x = this.pos.getX()+MathHelper.getRandomDoubleInRange(Util.RANDOM, 0, range*2)-range;
                double z = this.pos.getZ()+MathHelper.getRandomDoubleInRange(Util.RANDOM, 0, range*2)-range;
                EntityFireworkRocket rocket = new EntityFireworkRocket(this.worldObj, x, this.pos.getY()+0.5, z, firework);
                this.worldObj.spawnEntityInWorld(rocket);
            }

            this.storage.extractEnergy(USE_PER_SHOT, false);
        }
    }

    private ItemStack makeFirework(){
        NBTTagList list = new NBTTagList();
        int chargesAmount = Util.RANDOM.nextInt(2)+1;
        for(int i = 0; i < chargesAmount; i++){
            list.appendTag(this.makeFireworkCharge());
        }

        NBTTagCompound compound1 = new NBTTagCompound();
        compound1.setTag("Explosions", list);
        compound1.setByte("Flight", (byte)(Util.RANDOM.nextInt(3)+1));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("Fireworks", compound1);

        ItemStack firework = new ItemStack(Items.fireworks);
        firework.setTagCompound(compound);

        return firework;
    }

    private NBTTagCompound makeFireworkCharge(){
        NBTTagCompound compound = new NBTTagCompound();

        if(Util.RANDOM.nextFloat() >= 0.65F){
            if(Util.RANDOM.nextFloat() >= 0.5F){
                compound.setBoolean("Flicker", true);
            }
            else{
                compound.setBoolean("Trail", true);
            }
        }

        int[] colors = new int[MathHelper.getRandomIntegerInRange(Util.RANDOM, 1, 6)];
        for(int i = 0; i < colors.length; i++){
            colors[i] = ItemDye.dyeColors[Util.RANDOM.nextInt(ItemDye.dyeColors.length)];
        }
        compound.setIntArray("Colors", colors);

        compound.setByte("Type", (byte)Util.RANDOM.nextInt(5));

        return compound;
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return true;
    }

    @Override
    public void toggle(boolean to){
        this.activateOnceWithSignal = to;
    }

    @Override
    public boolean isPulseMode(){
        return this.activateOnceWithSignal;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getMaxEnergy(){
        return this.storage.getMaxEnergyStored();
    }
}
