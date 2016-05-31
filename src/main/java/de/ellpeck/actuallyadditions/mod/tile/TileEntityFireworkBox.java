/*
 * This file ("TileEntityFireworkBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFireworkBox extends TileEntityBase implements IEnergyReceiver, IRedstoneToggle, IEnergyDisplay, IEnergySaver{

    public static final int USE_PER_SHOT = 300;
    public final EnergyStorage storage = new EnergyStorage(20000);
    private int timeUntilNextFirework;
    private boolean activateOnceWithSignal;
    private int oldEnergy;

    public TileEntityFireworkBox(){
        super("fireworkBox");
    }

    public static void spawnFireworks(World world, double x, double y, double z){
        int range = 4;
        int amount = Util.RANDOM.nextInt(5)+1;
        for(int i = 0; i < amount; i++){
            ItemStack firework = makeFirework();

            double newX = x+MathHelper.getRandomDoubleInRange(Util.RANDOM, 0, range*2)-range;
            double newZ = z+MathHelper.getRandomDoubleInRange(Util.RANDOM, 0, range*2)-range;
            EntityFireworkRocket rocket = new EntityFireworkRocket(world, newX, y+0.5, newZ, firework);
            world.spawnEntityInWorld(rocket);
        }
    }

    private static ItemStack makeFirework(){
        NBTTagList list = new NBTTagList();
        int chargesAmount = Util.RANDOM.nextInt(2)+1;
        for(int i = 0; i < chargesAmount; i++){
            list.appendTag(makeFireworkCharge());
        }

        NBTTagCompound compound1 = new NBTTagCompound();
        compound1.setTag("Explosions", list);
        compound1.setByte("Flight", (byte)(Util.RANDOM.nextInt(3)+1));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("Fireworks", compound1);

        ItemStack firework = new ItemStack(Items.FIREWORKS);
        firework.setTagCompound(compound);

        return firework;
    }

    private static NBTTagCompound makeFireworkCharge(){
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
            colors[i] = ItemDye.DYE_COLORS[Util.RANDOM.nextInt(ItemDye.DYE_COLORS.length)];
        }
        compound.setIntArray("Colors", colors);

        compound.setByte("Type", (byte)Util.RANDOM.nextInt(5));

        return compound;
    }

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
            spawnFireworks(this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ());

            this.storage.extractEnergy(USE_PER_SHOT, false);
        }
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
