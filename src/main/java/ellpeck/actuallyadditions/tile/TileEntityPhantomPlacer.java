/*
 * This file ("TileEntityPhantomPlacer.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.network.PacketParticle;
import ellpeck.actuallyadditions.util.Util;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityPhantomPlacer extends TileEntityInventoryBase implements IPhantomTile, IRedstoneToggle{

    public static final int RANGE = 3;
    public WorldPos boundPosition;
    public int currentTime;
    public int range;
    public boolean isBreaker;

    public TileEntityPhantomPlacer(int slots, String name){
        super(slots, name);
    }

    public TileEntityPhantomPlacer(){
        super(9, "phantomPlacer");
        this.isBreaker = false;
    }

    private void doWork(){
        if(this.isBreaker){
            Block blockToBreak = boundPosition.getWorld().getBlock(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
            if(blockToBreak != null && blockToBreak.getBlockHardness(boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) > -1.0F){
                ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                int meta = boundPosition.getWorld().getBlockMetadata(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                drops.addAll(blockToBreak.getDrops(boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), meta, 0));

                if(WorldUtil.addToInventory(this, drops, false)){
                    boundPosition.getWorld().playAuxSFX(2001, boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), Block.getIdFromBlock(blockToBreak)+(meta << 12));
                    WorldUtil.breakBlockAtSide(ForgeDirection.UNKNOWN, boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                    WorldUtil.addToInventory(this, drops, true);
                    this.markDirty();
                }
            }
        }
        else{
            if(boundPosition.getWorld().getBlock(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()).isReplaceable(boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ())){
                int theSlot = WorldUtil.findFirstFilledSlot(this.slots);
                this.setInventorySlotContents(theSlot, WorldUtil.placeBlockAtSide(ForgeDirection.UNKNOWN, boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), this.slots[theSlot]));
                if(this.slots[theSlot] != null && this.slots[theSlot].stackSize <= 0){
                    this.slots[theSlot] = null;
                }
            }
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            this.range = TileEntityPhantomface.upgradeRange(RANGE, worldObj, xCoord, yCoord, zCoord);

            if(!this.hasBoundPosition()){
                this.boundPosition = null;
            }

            if(this.isBoundThingInRange()){
                if(!this.isRedstonePowered && !this.activateOnceWithSignal){
                    if(this.currentTime > 0){
                        this.currentTime--;
                        if(this.currentTime <= 0){
                            this.doWork();
                        }
                    }
                    else{
                        this.currentTime = 30;
                    }
                }
            }
        }
        else{
            if(this.boundPosition != null){
                this.renderParticles();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(Util.RANDOM.nextInt(2) == 0){
            double d1 = (double)((float)this.boundPosition.getY()+Util.RANDOM.nextFloat());
            int i1 = Util.RANDOM.nextInt(2)*2-1;
            int j1 = Util.RANDOM.nextInt(2)*2-1;
            double d4 = ((double)Util.RANDOM.nextFloat()-0.5D)*0.125D;
            double d2 = (double)this.boundPosition.getZ()+0.5D+0.25D*(double)j1;
            double d5 = (double)(Util.RANDOM.nextFloat()*1.0F*(float)j1);
            double d0 = (double)this.boundPosition.getX()+0.5D+0.25D*(double)i1;
            double d3 = (double)(Util.RANDOM.nextFloat()*1.0F*(float)i1);
            worldObj.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
        }

        if(this.ticksElapsed%80 == 0){
            PacketParticle.renderParticlesFromAToB(xCoord, yCoord, zCoord, boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), 2, 0.35F, TileEntityPhantomface.COLORS, 3);
        }
    }

    @Override
    public boolean hasBoundPosition(){
        if(this.boundPosition != null && this.boundPosition.getWorld() != null){
            if(this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IPhantomTile || (this.xCoord == this.boundPosition.getX() && this.yCoord == this.boundPosition.getY() && this.zCoord == this.boundPosition.getZ() && this.worldObj == this.boundPosition.getWorld())){
                this.boundPosition = null;
                return false;
            }
            return this.boundPosition.getWorld() == this.worldObj;
        }
        return false;
    }

    @Override
    public boolean isBoundThingInRange(){
        return this.hasBoundPosition() && this.boundPosition.toVec().distanceTo(Vec3.createVectorHelper(xCoord, yCoord, zCoord)) <= this.range;
    }

    @Override
    public WorldPos getBoundPosition(){
        return this.boundPosition;
    }

    @Override
    public void setBoundPosition(WorldPos pos){
        this.boundPosition = pos == null ? null : pos.copy();
    }

    @Override
    public int getGuiID(){
        return GuiHandler.GuiTypes.PHANTOM_PLACER.ordinal();
    }

    @Override
    public int getRange(){
        return this.range;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        if(this.boundPosition != null){
            compound.setInteger("XCoordOfTileStored", boundPosition.getX());
            compound.setInteger("YCoordOfTileStored", boundPosition.getY());
            compound.setInteger("ZCoordOfTileStored", boundPosition.getZ());
            compound.setInteger("WorldOfTileStored", boundPosition.getWorld().provider.dimensionId);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        int x = compound.getInteger("XCoordOfTileStored");
        int y = compound.getInteger("YCoordOfTileStored");
        int z = compound.getInteger("ZCoordOfTileStored");
        int world = compound.getInteger("WorldOfTileStored");
        if(!(x == 0 && y == 0 && z == 0)){
            this.boundPosition = new WorldPos(world, x, y, z);
            this.markDirty();
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return !this.isBreaker;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return this.isBreaker;
    }

    private boolean activateOnceWithSignal;

    @Override
    public boolean toggle(){
        return this.activateOnceWithSignal = !this.activateOnceWithSignal;
    }

    @Override
    public boolean isRightMode(){
        return this.activateOnceWithSignal;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }

    public static class TileEntityPhantomBreaker extends TileEntityPhantomPlacer{

        public TileEntityPhantomBreaker(){
            super(9, "phantomBreaker");
            this.isBreaker = true;
        }

    }
}
