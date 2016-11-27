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

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityFireworkBox extends TileEntityBase implements IEnergyDisplay{

    public static final int USE_PER_SHOT = 300;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(20000, 200, 0);
    private int timeUntilNextFirework;
    private int oldEnergy;

    public TileEntityFireworkBox(){
        super("fireworkBox");
    }

    public void spawnFireworks(World world, double x, double y, double z){
        int range = 4;
        int amount = world.rand.nextInt(5)+1;
        for(int i = 0; i < amount; i++){
            ItemStack firework = this.makeFirework();

            double newX = x+MathHelper.nextDouble(this.world.rand, 0, range*2)-range;
            double newZ = z+MathHelper.nextDouble(this.world.rand, 0, range*2)-range;

            if(world.isBlockLoaded(new BlockPos(newX, y, newZ))){
                EntityFireworkRocket rocket = new EntityFireworkRocket(world, newX, y+0.5, newZ, firework);
                world.spawnEntity(rocket);
            }
        }
    }

    private ItemStack makeFirework(){
        NBTTagList list = new NBTTagList();
        int chargesAmount = this.world.rand.nextInt(2)+1;
        for(int i = 0; i < chargesAmount; i++){
            list.appendTag(this.makeFireworkCharge());
        }

        NBTTagCompound compound1 = new NBTTagCompound();
        compound1.setTag("Explosions", list);
        compound1.setByte("Flight", (byte)(this.world.rand.nextInt(3)+1));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("Fireworks", compound1);

        ItemStack firework = new ItemStack(Items.FIREWORKS);
        firework.setTagCompound(compound);

        return firework;
    }

    private NBTTagCompound makeFireworkCharge(){
        NBTTagCompound compound = new NBTTagCompound();

        if(this.world.rand.nextFloat() >= 0.65F){
            if(this.world.rand.nextFloat() >= 0.5F){
                compound.setBoolean("Flicker", true);
            }
            else{
                compound.setBoolean("Trail", true);
            }
        }

        int[] colors = new int[MathHelper.getInt(this.world.rand, 1, 6)];
        for(int i = 0; i < colors.length; i++){
            colors[i] = ItemDye.DYE_COLORS[this.world.rand.nextInt(ItemDye.DYE_COLORS.length)];
        }
        compound.setIntArray("Colors", colors);

        compound.setByte("Type", (byte)this.world.rand.nextInt(5));

        return compound;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.world.isRemote){
            if(!this.isRedstonePowered && !this.isPulseMode){
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
            this.spawnFireworks(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ());

            this.storage.extractEnergyInternal(USE_PER_SHOT, false);
        }
    }

    @Override
    public boolean isRedstoneToggle(){
        return true;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }

    @Override
    public CustomEnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
