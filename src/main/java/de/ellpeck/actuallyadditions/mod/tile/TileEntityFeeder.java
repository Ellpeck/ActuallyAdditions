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

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFeeder;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class TileEntityFeeder extends TileEntityInventoryBase implements INamedContainerProvider {

    public static final int THRESHOLD = 30;
    private static final int TIME = 100;
    public int currentTimer;
    public int currentAnimalAmount;
    private int lastAnimalAmount;
    private int lastTimer;

    public TileEntityFeeder() {
        super(ActuallyBlocks.FEEDER.getTileEntityType(), 1);
    }

    public int getCurrentTimerToScale(int i) {
        return this.currentTimer * i / TIME;
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        compound.putInt("Timer", this.currentTimer);
        if (type == NBTType.SYNC) {
            compound.putInt("Animals", this.currentAnimalAmount);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.currentTimer = compound.getInt("Timer");
        if (type == NBTType.SYNC) {
            this.currentAnimalAmount = compound.getInt("Animals");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        this.currentTimer = MathHelper.clamp(++this.currentTimer, 0, 100);
        if (this.level.isClientSide) {
            return;
        }
        int range = 5;
        ItemStack stack = this.inv.getStackInSlot(0);
        if (!stack.isEmpty() && this.currentTimer >= TIME) {
            List<AnimalEntity> animals = this.level.getEntitiesOfClass(AnimalEntity.class, new AxisAlignedBB(this.worldPosition.getX() - range, this.worldPosition.getY() - range, this.worldPosition.getZ() - range, this.worldPosition.getX() + range, this.worldPosition.getY() + range, this.worldPosition.getZ() + range));
            this.currentAnimalAmount = animals.size();
            if (this.currentAnimalAmount >= 2 && this.currentAnimalAmount < THRESHOLD) {
                Optional<AnimalEntity> opt = animals.stream().filter((e) -> canBeFed(stack, e)).findAny();
                if (opt.isPresent()) {
                    feedAnimal(opt.get());
                    stack.shrink(1);
                    this.currentTimer = 0;
                    this.setChanged();
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

    private static void feedAnimal(AnimalEntity animal) {
        animal.setInLove(null);
        for (int i = 0; i < 7; i++) {
            double d = animal.level.random.nextGaussian() * 0.02D;
            double d1 = animal.level.random.nextGaussian() * 0.02D;
            double d2 = animal.level.random.nextGaussian() * 0.02D;
            animal.level.addParticle(ParticleTypes.HEART, animal.getX() + animal.level.random.nextFloat() * animal.getBbWidth() * 2.0F - animal.getBbWidth(), animal.getY() + 0.5D + animal.level.random.nextFloat() * animal.getBbHeight(), animal.getZ() + animal.level.random.nextFloat() * animal.getBbWidth() * 2.0F - animal.getBbWidth(), d, d1, d2);
        }
    }

    private static boolean canBeFed(ItemStack stack, AnimalEntity animal) {
        if (animal instanceof HorseEntity && ((HorseEntity) animal).isTamed()) {
            Item item = stack.getItem();
            return animal.getAge() == 0 && !animal.isInLove() && (item == Items.GOLDEN_APPLE || item == Items.GOLDEN_CARROT);
        }
        return animal.getAge() == 0 && !animal.isInLove() && animal.isFood(stack);
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
        return new ContainerFeeder(windowId, playerInventory, this);
    }
}
