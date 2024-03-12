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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class TileEntityFeeder extends TileEntityInventoryBase implements MenuProvider {

    public static final int THRESHOLD = 30;
    private static final int TIME = 100;
    public int currentTimer;
    public int currentAnimalAmount;
    private int lastAnimalAmount;
    private int lastTimer;

    public TileEntityFeeder(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.FEEDER.getTileEntityType(), pos, state,1);
    }

    public int getCurrentTimerToScale(int i) {
        return this.currentTimer * i / TIME;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        compound.putInt("Timer", this.currentTimer);
        if (type == NBTType.SYNC) {
            compound.putInt("Animals", this.currentAnimalAmount);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.currentTimer = compound.getInt("Timer");
        if (type == NBTType.SYNC) {
            this.currentAnimalAmount = compound.getInt("Animals");
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFeeder tile) {
            tile.clientTick();
            tile.currentTimer = Mth.clamp(++tile.currentTimer, 0, 100);
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFeeder tile) {
            tile.serverTick();
            tile.currentTimer = Mth.clamp(++tile.currentTimer, 0, 100);

            int range = 5;
            ItemStack stack = tile.inv.getStackInSlot(0);
            if (!stack.isEmpty() && tile.currentTimer >= TIME) {
                List<Animal> animals = level.getEntitiesOfClass(Animal.class, new AABB(pos).inflate(range));
                tile.currentAnimalAmount = animals.size();
                if (tile.currentAnimalAmount >= 2 && tile.currentAnimalAmount < THRESHOLD) {
                    Optional<Animal> opt = animals.stream().filter((e) -> tile.canBeFed(stack, e)).findAny();
                    if (opt.isPresent()) {
                        tile.feedAnimal(opt.get());
                        stack.shrink(1);
                        tile.currentTimer = 0;
                        tile.setChanged();
                    }
                }
            }
            if ((tile.lastAnimalAmount != tile.currentAnimalAmount || tile.lastTimer != tile.currentTimer) && tile.sendUpdateWithInterval()) {
                tile.lastAnimalAmount = tile.currentAnimalAmount;
                tile.lastTimer = tile.currentTimer;
            }
        }
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation;
    }

    private void feedAnimal(Animal animal) {
        animal.setInLove(null);
        for (int i = 0; i < 7; i++) {
            double d = animal.level().random.nextGaussian() * 0.02D;
            double d1 = animal.level().random.nextGaussian() * 0.02D;
            double d2 = animal.level().random.nextGaussian() * 0.02D;
            animal.level().addParticle(ParticleTypes.HEART, animal.getX() + animal.level().random.nextFloat() * animal.getBbWidth() * 2.0F - animal.getBbWidth(), animal.getY() + 0.5D + animal.level().random.nextFloat() * animal.getBbHeight(), animal.getZ() + animal.level().random.nextFloat() * animal.getBbWidth() * 2.0F - animal.getBbWidth(), d, d1, d2);
        }
    }

    private boolean canBeFed(ItemStack stack, Animal animal) {
        if (animal instanceof Horse && ((Horse) animal).isTamed()) {
            Item item = stack.getItem();
            return animal.getAge() == 0 && !animal.isInLove() && (item == Items.GOLDEN_APPLE || item == Items.GOLDEN_CARROT);
        }
        return animal.getAge() == 0 && !animal.isInLove() && animal.isFood(stack);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player p_createMenu_3_) {
        return new ContainerFeeder(windowId, playerInventory, this);
    }
}
