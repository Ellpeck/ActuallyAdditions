package de.ellpeck.actuallyadditions.common.tiles;

import de.ellpeck.actuallyadditions.common.container.FeederContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FeederTileEntity extends ActuallyTile implements INamedContainerProvider, ITickableTileEntity {
    public static final int THRESHOLD = 30;
    private static final int TIME = 100;
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            FeederTileEntity.this.markDirty();
        }
    };
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemStackHandler);
    public int currentTimer;
    public int currentAnimalAmount;

    public FeederTileEntity() {
        super(ActuallyTiles.FEEDER_TILE.get());
    }

    @Override
    public void tick() {
        int last = this.currentTimer;
        this.currentTimer = MathHelper.clamp(this.currentTimer + 1, 0, 100);

        if (this.world == null || this.world.isRemote) {
            return;
        }

        ItemStack stack = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(e -> e.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        if (stack.isEmpty()) {
            return;
        }

        List<AnimalEntity> animals = this.world.getEntitiesWithinAABB(AnimalEntity.class, new AxisAlignedBB(pos).grow(5));
        this.currentAnimalAmount = animals.size();

        if (last != this.currentTimer) {
            System.out.println(this.currentAnimalAmount);
            this.sendTileEntityToClients();
        }

        if (this.currentAnimalAmount < 2 || this.currentAnimalAmount > THRESHOLD || this.currentTimer < TIME) {
            return;
        }

        for (AnimalEntity animal : animals) {
            if (animal.getGrowingAge() != 0 || animal.isInLove() || !animal.isBreedingItem(stack)) {
                continue;
            }

            animal.setInLove(null);
            for (int i = 0; i < 7; i++) {
                double d = animal.world.rand.nextGaussian() * 0.02D;
                double d1 = animal.world.rand.nextGaussian() * 0.02D;
                double d2 = animal.world.rand.nextGaussian() * 0.02D;
                animal.world.addParticle(ParticleTypes.HEART, animal.getPosX() + animal.world.rand.nextFloat() * animal.getWidth() * 2.0F - animal.getWidth(), animal.getPosY() + 0.5D + animal.world.rand.nextFloat() * animal.getHealth(), animal.getPosZ() + animal.world.rand.nextFloat() * animal.getWidth() * 2.0F - animal.getWidth(), d, d1, d2);
            }

            stack.shrink(1);
            this.currentTimer = 0;
            this.markDirty();
            this.sendTileEntityToClients();

            break;
        }
    }

    public int getCurrentTimerToScale(int i) {
        return this.currentTimer * i / TIME;
    }

    @Override
    public ITextComponent getDisplayName() {
        return StringTextComponent.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FeederContainer(windowId, playerInventory, this);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);

        this.currentTimer = nbt.getInt("timer");
        this.currentAnimalAmount = nbt.getInt("animals");
        if (nbt.contains("items")) {
            itemStackHandler.deserializeNBT(nbt.getCompound("items"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("items", itemStackHandler.serializeNBT());
        compound.putInt("timer", this.currentTimer);
        compound.putInt("animals", this.currentAnimalAmount);
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
    }
}
