/*
 * This file ("TileEntityFeeder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Optional;

public class TileEntityFeeder extends TileEntityInventoryBase {

    public static final int THRESHOLD = 30;
    private static final int TIME = 100;
    public int currentTimer;
    public int currentAnimalAmount;
    private int lastAnimalAmount;
    private int lastTimer;

    public TileEntityFeeder() {
        super(ActuallyTiles.FEEDER_TILE.get(), 1);
    }

    public int getCurrentTimerToScale(int i) {
        return this.currentTimer * i / TIME;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        compound.setInteger("Timer", this.currentTimer);
        if (type == NBTType.SYNC) {
            compound.setInteger("Animals", this.currentAnimalAmount);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.currentTimer = compound.getInteger("Timer");
        if (type == NBTType.SYNC) {
            this.currentAnimalAmount = compound.getInteger("Animals");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        this.currentTimer = MathHelper.clamp(++this.currentTimer, 0, 100);
        if (this.world.isRemote) {
            return;
        }
        int range = 5;
        ItemStack stack = this.inv.getStackInSlot(0);
        if (!stack.isEmpty() && this.currentTimer >= TIME) {
            List<EntityAnimal> animals = this.world.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(this.pos.getX() - range, this.pos.getY() - range, this.pos.getZ() - range, this.pos.getX() + range, this.pos.getY() + range, this.pos.getZ() + range));
            this.currentAnimalAmount = animals.size();
            if (this.currentAnimalAmount >= 2 && this.currentAnimalAmount < THRESHOLD) {
                Optional<EntityAnimal> opt = animals.stream().filter((e) -> canBeFed(stack, e)).findAny();
                if (opt.isPresent()) {
                    feedAnimal(opt.get());
                    stack.shrink(1);
                    this.currentTimer = 0;
                    this.markDirty();
                }
            }
        }
        if ((this.lastAnimalAmount != this.currentAnimalAmount || this.lastTimer != this.currentTimer) && this.sendUpdateWithInterval()) {
            this.lastAnimalAmount = this.currentAnimalAmount;
            this.lastTimer = this.currentTimer;
        }
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation;
    }

    private static void feedAnimal(EntityAnimal animal) {
        animal.setInLove(null);
        for (int i = 0; i < 7; i++) {
            double d = animal.world.rand.nextGaussian() * 0.02D;
            double d1 = animal.world.rand.nextGaussian() * 0.02D;
            double d2 = animal.world.rand.nextGaussian() * 0.02D;
            animal.world.spawnParticle(EnumParticleTypes.HEART, animal.posX + animal.world.rand.nextFloat() * animal.width * 2.0F - animal.width, animal.posY + 0.5D + animal.world.rand.nextFloat() * animal.height, animal.posZ + animal.world.rand.nextFloat() * animal.width * 2.0F - animal.width, d, d1, d2);
        }
    }

    private static boolean canBeFed(ItemStack stack, EntityAnimal animal) {
        if (animal instanceof EntityHorse && ((EntityHorse) animal).isTame()) {
            Item item = stack.getItem();
            return animal.getGrowingAge() == 0 && !animal.isInLove() && (item == Items.GOLDEN_APPLE || item == Items.GOLDEN_CARROT);
        }
        return animal.getGrowingAge() == 0 && !animal.isInLove() && animal.isBreedingItem(stack);
    }
}
