package de.ellpeck.actuallyadditions.common.tile;

import de.ellpeck.actuallyadditions.common.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.common.items.ItemDrill;
import de.ellpeck.actuallyadditions.common.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import de.ellpeck.actuallyadditions.common.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMiner extends TileEntityInventoryBase implements IButtonReactor, IEnergyDisplay {

    public static final int ENERGY_USE_PER_BLOCK = 650;
    public static final int DEFAULT_RANGE = 2;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(200000, 2000, 0);

    public boolean onlyMineOres;
    public int checkX;
    public int checkY = -1;
    public int checkZ;
    private int oldEnergy;
    private int oldCheckX;
    private int oldCheckY;
    private int oldCheckZ;

    public TileEntityMiner() {
        super(9, "miner");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("CheckX", this.checkX);
            compound.setInteger("CheckY", this.checkY);
            compound.setInteger("CheckZ", this.checkZ);
        }
        if (type != NBTType.SAVE_BLOCK || this.onlyMineOres) {
            compound.setBoolean("OnlyOres", this.onlyMineOres);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            this.checkX = compound.getInteger("CheckX");
            this.checkY = compound.getInteger("CheckY");
            this.checkZ = compound.getInteger("CheckZ");
        }
        this.onlyMineOres = compound.getBoolean("OnlyOres");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {

            if (!this.isRedstonePowered && this.ticksElapsed % 5 == 0) {
                if (this.checkY != 0) {
                    int range = TileEntityPhantomface.upgradeRange(DEFAULT_RANGE, this.world, this.pos);
                    if (this.checkY < 0) {
                        this.checkY = this.pos.getY() - 1;
                        this.checkX = -range;
                        this.checkZ = -range;
                    }

                    if (this.checkY > 0) {
                        if (this.mine()) {
                            this.checkX++;
                            if (this.checkX > range) {
                                this.checkX = -range;
                                this.checkZ++;
                                if (this.checkZ > range) {
                                    this.checkZ = -range;
                                    this.checkY--;
                                }
                            }
                        }
                    }
                }
            }

            if ((this.oldEnergy != this.storage.getEnergyStored() || this.oldCheckX != this.checkX || this.oldCheckY != this.checkY || this.oldCheckZ != this.checkZ) && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
                this.oldCheckX = this.checkX;
                this.oldCheckY = this.checkY;
                this.oldCheckZ = this.checkZ;
            }
        }
    }

    private boolean mine() {
        int actualUse = ENERGY_USE_PER_BLOCK * (this.onlyMineOres ? 3 : 1);
        if (this.storage.getEnergyStored() >= actualUse) {
            BlockPos pos = new BlockPos(this.pos.getX() + this.checkX, this.checkY, this.pos.getZ() + this.checkZ);

            IBlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();
            ItemStack stack = block.getPickBlock(state, new RayTraceResult(Type.BLOCK, new Vec3d(0, 0, 0), EnumFacing.DOWN, pos), this.world, pos, FakePlayerFactory.getMinecraft((WorldServer) this.world));
            if (!block.isAir(this.world.getBlockState(pos), this.world, pos)) {
                if (block.getHarvestLevel(this.world.getBlockState(pos)) <= ItemDrill.HARVEST_LEVEL && state.getBlockHardness(this.world, pos) >= 0F && !(block instanceof BlockLiquid) && !(block instanceof IFluidBlock) && this.isMinable(block, stack)) {
                    NonNullList<ItemStack> drops = NonNullList.create();
                    block.getDrops(drops, this.world, pos, state, 0);
                    float chance = WorldUtil.fireFakeHarvestEventsForDropChance(this, drops, this.world, pos);

                    if (chance > 0 && this.world.rand.nextFloat() <= chance) {
                        if (StackUtil.canAddAll(this.inv, drops, false)) {
                            this.world.playEvent(2001, pos, Block.getStateId(this.world.getBlockState(pos)));
                            this.world.setBlockToAir(pos);

                            StackUtil.addAll(this.inv, drops, false);
                            this.markDirty();

                            this.storage.extractEnergyInternal(actualUse, false);
                            this.shootParticles(pos.getX(), pos.getY(), pos.getZ());
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean isMinable(Block block, ItemStack stack) {
        if (block != null) {
            if (!this.isBlacklisted(block)) {
                if (!this.onlyMineOres) {
                    return true;
                } else {
                    if (StackUtil.isValid(stack)) {
                        int[] ids = OreDictionary.getOreIDs(stack);
                        for (int id : ids) {
                            String name = OreDictionary.getOreName(id);
                            if (name.startsWith("ore") || name.startsWith("denseore")) { return true; }
                        }

                        String reg = block.getRegistryName().toString();
                        if (!reg.isEmpty()) {
                            for (String string : ConfigStringListValues.MINER_EXTRA_WHITELIST.getValue()) {
                                if (reg.equals(string)) { return true; }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private void shootParticles(int endX, int endY, int endZ) {
        AssetUtil.spawnLaserWithTimeServer(this.world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), endX, endY, endZ, new float[] { 65F / 255F, 150F / 255F, 2F / 255F }, 10, 120, 0.1F, 0.8F);
    }

    private boolean isBlacklisted(Block block) {
        String reg = block.getRegistryName().toString();
        if (!reg.isEmpty()) {
            for (String string : ConfigStringListValues.MINER_BLACKLIST.getValue()) {
                if (reg.equals(string)) { return true; }
            }
        }
        return false;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (stack, slot, automation) -> !automation;
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player) {
        if (buttonID == 0) {
            this.onlyMineOres = !this.onlyMineOres;
            this.sendUpdate();
        } else if (buttonID == 1) {
            this.checkX = 0;
            this.checkY = -1;
            this.checkZ = 0;
        }
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
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }
}
