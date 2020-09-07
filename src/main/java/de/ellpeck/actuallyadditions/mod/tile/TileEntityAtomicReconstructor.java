package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.SoundHandler;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityAtomicReconstructor extends TileEntityInventoryBase implements IEnergyDisplay, IAtomicReconstructor {

    public static final int ENERGY_USE = 1000;
    public final CustomEnergyStorage storage;
    public int counter;
    private int currentTime;
    private int oldEnergy;

    public TileEntityAtomicReconstructor() {
        super(1, "reconstructor");
        int power = ConfigIntValues.RECONSTRUCTOR_POWER.getValue();
        int recieve = MathHelper.ceil(power * 0.016666F);
        this.storage = new CustomEnergyStorage(power, recieve, 0);
    }

    public static void shootLaser(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, Lens currentLens) {
        world.playSound(null, startX, startY, startZ, SoundHandler.reconstructor, SoundCategory.BLOCKS, 0.35F, 1.0F);
        AssetUtil.spawnLaserWithTimeServer(world, startX, startY, startZ, endX, endY, endZ, currentLens.getColor(), 25, 0, 0.2F, 0.8F);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("CurrentTime", this.currentTime);
            compound.setInteger("Counter", this.counter);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInteger("CurrentTime");
            this.counter = compound.getInteger("Counter");
        }
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            if (!this.isRedstonePowered && !this.isPulseMode) {
                if (this.currentTime > 0) {
                    this.currentTime--;
                    if (this.currentTime <= 0) {
                        ActuallyAdditionsAPI.methodHandler.invokeReconstructor(this);
                    }
                } else {
                    this.currentTime = 100;
                }
            }

            if (this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
                this.world.updateComparatorOutputLevel(this.pos, InitBlocks.blockAtomicReconstructor);
            }
        }

    }

    @Override
    public Lens getLens() {
        Item item = this.inv.getStackInSlot(0).getItem();
        if (item instanceof ILensItem) return ((ILensItem) item).getLens();
        return this.counter >= 500 ? ActuallyAdditionsAPI.lensDisruption : ActuallyAdditionsAPI.lensDefaultConversion;
    }

    @Override
    public EnumFacing getOrientation() {
        IBlockState state = this.world.getBlockState(this.pos);
        return WorldUtil.getDirectionByPistonRotation(state);
    }

    @Override
    public BlockPos getPosition() {
        return this.pos;
    }

    @Override
    public int getX() {
        return this.getPos().getX();
    }

    @Override
    public int getY() {
        return this.getPos().getY();
    }

    @Override
    public int getZ() {
        return this.getPos().getZ();
    }

    @Override
    public World getWorldObject() {
        return this.getWorld();
    }

    @Override
    public void extractEnergy(int amount) {
        this.storage.extractEnergyInternal(amount, false);
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> StackUtil.isValid(stack) && stack.getItem() instanceof ILensItem;
    }

    @Override
    public int getEnergy() {
        return this.storage.getEnergyStored();
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
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        ActuallyAdditionsAPI.methodHandler.invokeReconstructor(this);
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }
}
