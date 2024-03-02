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

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFireworkBox;
import de.ellpeck.actuallyadditions.mod.network.gui.INumberReactor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityFireworkBox extends TileEntityBase implements IEnergyDisplay, INumberReactor, MenuProvider {

    public static final int USE_PER_SHOT = 500;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(20000, 200, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
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

    public TileEntityFireworkBox(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.FIREWORK_BOX.getTileEntityType(), pos, state);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);

        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("Play", this.intValuePlay);
            compound.putInt("ChargeAmount", this.chargeAmount);
            compound.putInt("FlightTime", this.flightTime);
            compound.putFloat("TrailFlickerChance", this.trailOrFlickerChance);
            compound.putFloat("FlickerChance", this.flickerChance);
            compound.putInt("ColorAmount", this.colorAmount);
            compound.putFloat("TypeChance0", this.typeChance0);
            compound.putFloat("TypeChance1", this.typeChance1);
            compound.putFloat("TypeChance2", this.typeChance2);
            compound.putFloat("TypeChance3", this.typeChance3);
            compound.putFloat("TypeChance4", this.typeChance4);
            compound.putInt("Area", this.areaOfEffect);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);

        if (type != NBTType.SAVE_BLOCK) {
            this.intValuePlay = compound.getInt("Play");
            this.chargeAmount = compound.getInt("ChargeAmount");
            this.flightTime = compound.getInt("FlightTime");
            this.trailOrFlickerChance = compound.getFloat("TrailFlickerChance");
            this.flickerChance = compound.getFloat("FlickerChance");
            this.colorAmount = compound.getInt("ColorAmount");
            this.typeChance0 = compound.getFloat("TypeChance0");
            this.typeChance1 = compound.getFloat("TypeChance1");
            this.typeChance2 = compound.getFloat("TypeChance2");
            this.typeChance3 = compound.getFloat("TypeChance3");
            this.typeChance4 = compound.getFloat("TypeChance4");
            this.areaOfEffect = compound.getInt("Area");
        }
    }

    @Override
    public void onNumberReceived(double number, int id, Player player) {
        switch (id) {
            case 0:
                this.intValuePlay = (int) number;
                break;
            case 1:
                this.chargeAmount = (int) number;
                break;
            case 2:
                this.flightTime = (int) number;
                break;
            case 3:
                this.trailOrFlickerChance = (float) number;
                break;
            case 4:
                this.flickerChance = (float) number;
                break;
            case 5:
                this.colorAmount = (int) number;
                break;
            case 6:
                this.typeChance0 = (float) number;
                break;
            case 7:
                this.typeChance1 = (float) number;
                break;
            case 8:
                this.typeChance2 = (float) number;
                break;
            case 9:
                this.typeChance3 = (float) number;
                break;
            case 10:
                this.typeChance4 = (float) number;
                break;
            case 11:
                this.areaOfEffect = (int) number;
                break;
        }

        this.sendUpdate();
    }

    public void spawnFireworks(Level world, double x, double y, double z) {
        ItemStack firework = this.makeFirework();

        double newX = x + this.getRandomAoe();
        double newZ = z + this.getRandomAoe();

        if (world.hasChunkAt(new BlockPos(newX, y, newZ))) {
            FireworkRocketEntity rocket = new FireworkRocketEntity(world, newX, y + 1, newZ, firework);
            world.addFreshEntity(rocket);
        }
    }

    private double getRandomAoe() {
        if (this.areaOfEffect <= 0) {
            return 0.5;
        } else {
            return Mth.nextDouble(this.level.random, 0, this.areaOfEffect * 2) - this.areaOfEffect;
        }
    }

    private ItemStack makeFirework() {
        ListTag list = new ListTag();
        for (int i = 0; i < this.getRandomWithPlay(this.chargeAmount); i++) {
            list.add(this.makeFireworkCharge());
        }

        CompoundTag compound1 = new CompoundTag();
        compound1.put("Explosions", list);
        compound1.putByte("Flight", (byte) this.getRandomWithPlay(this.flightTime));

        CompoundTag compound = new CompoundTag();
        compound.put("Fireworks", compound1);

        ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        firework.setTag(compound);

        return firework;
    }

    private CompoundTag makeFireworkCharge() {
        CompoundTag compound = new CompoundTag();

        if (this.level.random.nextFloat() <= this.trailOrFlickerChance) {
            if (this.level.random.nextFloat() <= this.flickerChance) {
                compound.putBoolean("Flicker", true);
            } else {
                compound.putBoolean("Trail", true);
            }
        }

        // TODO: [port] Validate this is the correct way to get colors
        int[] colors = new int[this.getRandomWithPlay(this.colorAmount)];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = DyeColor.values()[this.level.random.nextInt(DyeColor.values().length)].getFireworkColor();
        }
        compound.putIntArray("Colors", colors);

        compound.putByte("Type", (byte) this.getRandomType());

        return compound;
    }

    private int getRandomWithPlay(int value) {
        return Mth.clamp(Mth.nextInt(this.level.random, value - this.intValuePlay, value + this.intValuePlay), 1, 6);
    }

    private int getRandomType() {
        List<WeightedFireworkType> possible = new ArrayList<>();

        possible.add(new WeightedFireworkType(0, this.typeChance0));
        possible.add(new WeightedFireworkType(1, this.typeChance1));
        possible.add(new WeightedFireworkType(2, this.typeChance2));
        possible.add(new WeightedFireworkType(3, this.typeChance3));
        possible.add(new WeightedFireworkType(4, this.typeChance4));

        int weight = WeightedRandom.getTotalWeight(possible);
        if (weight <= 0) {
            return 0;
        } else {
            return WeightedRandom.getRandomItem(this.level.random, possible, weight).map(weightedFireworkType -> weightedFireworkType.type).orElse(0);
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFireworkBox tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFireworkBox tile) {
            tile.serverTick();

            if (!tile.isRedstonePowered && !tile.isPulseMode) {
                if (tile.timeUntilNextFirework > 0) {
                    tile.timeUntilNextFirework--;
                    if (tile.timeUntilNextFirework <= 0) {
                        tile.doWork();
                    }
                } else {
                    tile.timeUntilNextFirework = 100;
                }
            }

            if (tile.oldEnergy != tile.storage.getEnergyStored() && tile.sendUpdateWithInterval()) {
                tile.oldEnergy = tile.storage.getEnergyStored();
            }
        }
    }

    private void doWork() {
        if (this.storage.getEnergyStored() >= USE_PER_SHOT) {
            this.spawnFireworks(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ());

            this.storage.extractEnergyInternal(USE_PER_SHOT, false);
        }
    }

    @Override
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        this.doWork();
    }

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public Component getDisplayName() {
        return TextComponent.EMPTY;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player p_createMenu_3_) {
        return new ContainerFireworkBox(windowId, playerInventory);
    }

    private static class WeightedFireworkType implements WeightedEntry {

        public final int type;
        public final Weight chance;

        public WeightedFireworkType(int type, float chance) {
            this.type = type;
            this.chance = Weight.of((int) (chance * 100F));
        }

        @Override
        public Weight getWeight() {
            return this.chance;
        }
    }
}
