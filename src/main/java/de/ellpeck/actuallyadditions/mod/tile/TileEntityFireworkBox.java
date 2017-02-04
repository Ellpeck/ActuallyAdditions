/*
 * This file ("TileEntityFireworkBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.network.gui.INumberReactor;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileEntityFireworkBox extends TileEntityBase implements IEnergyDisplay, INumberReactor{

    public static final int USE_PER_SHOT = 500;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(20000, 200, 0);
    public int intValuePlay = 2;
    public int chargeAmount = 2;
    public int flightTime = 2;
    public float trailOrFlickerChance = 0.65F;
    public float flickerChance = 0.25F;
    public int colorAmount = 3;
    public float typeChance0 = 1F;
    public float typeChance1 = 0F;
    public float typeChance2 = 0F;
    public float typeChance3 = 0F;
    public float typeChance4 = 0F;
    public int areaOfEffect = 2;
    private int timeUntilNextFirework;
    private int oldEnergy;

    public TileEntityFireworkBox(){
        super("fireworkBox");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);

        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("Play", this.intValuePlay);
            compound.setInteger("ChargeAmount", this.chargeAmount);
            compound.setInteger("FlightTime", this.flightTime);
            compound.setFloat("TrailFlickerChance", this.trailOrFlickerChance);
            compound.setFloat("FlickerChance", this.flickerChance);
            compound.setInteger("ColorAmount", this.colorAmount);
            compound.setFloat("TypeChance0", this.typeChance0);
            compound.setFloat("TypeChance1", this.typeChance1);
            compound.setFloat("TypeChance2", this.typeChance2);
            compound.setFloat("TypeChance3", this.typeChance3);
            compound.setFloat("TypeChance4", this.typeChance4);
            compound.setInteger("Area", this.areaOfEffect);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);

        if(type != NBTType.SAVE_BLOCK){
            this.intValuePlay = compound.getInteger("Play");
            this.chargeAmount = compound.getInteger("ChargeAmount");
            this.flightTime = compound.getInteger("FlightTime");
            this.trailOrFlickerChance = compound.getFloat("TrailFlickerChance");
            this.flickerChance = compound.getFloat("FlickerChance");
            this.colorAmount = compound.getInteger("ColorAmount");
            this.typeChance0 = compound.getFloat("TypeChance0");
            this.typeChance1 = compound.getFloat("TypeChance1");
            this.typeChance2 = compound.getFloat("TypeChance2");
            this.typeChance3 = compound.getFloat("TypeChance3");
            this.typeChance4 = compound.getFloat("TypeChance4");
            this.areaOfEffect = compound.getInteger("Area");
        }
    }

    @Override
    public void onNumberReceived(double number, int id, EntityPlayer player){
        switch(id){
            case 0:
                this.intValuePlay = (int)number;
                break;
            case 1:
                this.chargeAmount = (int)number;
                break;
            case 2:
                this.flightTime = (int)number;
                break;
            case 3:
                this.trailOrFlickerChance = (float)number;
                break;
            case 4:
                this.flickerChance = (float)number;
                break;
            case 5:
                this.colorAmount = (int)number;
                break;
            case 6:
                this.typeChance0 = (float)number;
                break;
            case 7:
                this.typeChance1 = (float)number;
                break;
            case 8:
                this.typeChance2 = (float)number;
                break;
            case 9:
                this.typeChance3 = (float)number;
                break;
            case 10:
                this.typeChance4 = (float)number;
                break;
            case 11:
                this.areaOfEffect = (int)number;
                break;
        }

        this.sendUpdate();
    }

    public void spawnFireworks(World world, double x, double y, double z){
        ItemStack firework = this.makeFirework();

        double newX = x+this.getRandomAoe();
        double newZ = z+this.getRandomAoe();

        if(world.isBlockLoaded(new BlockPos(newX, y, newZ))){
            EntityFireworkRocket rocket = new EntityFireworkRocket(world, newX, y+1, newZ, firework);
            world.spawnEntity(rocket);
        }
    }

    private double getRandomAoe(){
        if(this.areaOfEffect <= 0){
            return 0.5;
        }
        else{
            return MathHelper.nextDouble(this.world.rand, 0, this.areaOfEffect*2)-this.areaOfEffect;
        }
    }

    private ItemStack makeFirework(){
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < this.getRandomWithPlay(this.chargeAmount); i++){
            list.appendTag(this.makeFireworkCharge());
        }

        NBTTagCompound compound1 = new NBTTagCompound();
        compound1.setTag("Explosions", list);
        compound1.setByte("Flight", (byte)this.getRandomWithPlay(this.flightTime));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("Fireworks", compound1);

        ItemStack firework = new ItemStack(Items.FIREWORKS);
        firework.setTagCompound(compound);

        return firework;
    }

    private NBTTagCompound makeFireworkCharge(){
        NBTTagCompound compound = new NBTTagCompound();

        if(this.world.rand.nextFloat() <= this.trailOrFlickerChance){
            if(this.world.rand.nextFloat() <= this.flickerChance){
                compound.setBoolean("Flicker", true);
            }
            else{
                compound.setBoolean("Trail", true);
            }
        }

        int[] colors = new int[this.getRandomWithPlay(this.colorAmount)];
        for(int i = 0; i < colors.length; i++){
            colors[i] = ItemDye.DYE_COLORS[this.world.rand.nextInt(ItemDye.DYE_COLORS.length)];
        }
        compound.setIntArray("Colors", colors);

        compound.setByte("Type", (byte)this.getRandomType());

        return compound;
    }

    private int getRandomWithPlay(int value){
        return MathHelper.clamp(MathHelper.getInt(this.world.rand, value-this.intValuePlay, value+this.intValuePlay), 1, 6);
    }

    private int getRandomType(){
        List<WeightedFireworkType> possible = new ArrayList<WeightedFireworkType>();

        possible.add(new WeightedFireworkType(0, this.typeChance0));
        possible.add(new WeightedFireworkType(1, this.typeChance1));
        possible.add(new WeightedFireworkType(2, this.typeChance2));
        possible.add(new WeightedFireworkType(3, this.typeChance3));
        possible.add(new WeightedFireworkType(4, this.typeChance4));

        int weight = WeightedRandom.getTotalWeight(possible);
        if(weight <= 0){
            return 0;
        }
        else{
            return WeightedRandom.getRandomItem(this.world.rand, possible, weight).type;
        }
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

    private static class WeightedFireworkType extends WeightedRandom.Item{

        public final int type;

        public WeightedFireworkType(int type, float chance){
            super((int)(chance*100F));
            this.type = type;
        }
    }
}
