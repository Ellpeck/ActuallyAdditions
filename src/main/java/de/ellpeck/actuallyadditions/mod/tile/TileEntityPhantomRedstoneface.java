package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

public class TileEntityPhantomRedstoneface extends TileEntityPhantomface{

    public int[] providesStrong = new int[EnumFacing.values().length];
    public int[] providesWeak = new int[EnumFacing.values().length];

    private int[] lastProvidesStrong = new int[this.providesStrong.length];
    private int[] lastProvidesWeak = new int[this.providesWeak.length];

    public TileEntityPhantomRedstoneface(){
        super("redstoneface");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            if(this.isBoundThingInRange()){
                IBlockState boundState = this.worldObj.getBlockState(this.boundPosition);
                if(boundState != null){
                    Block boundBlock = boundState.getBlock();
                    if(boundBlock != null){
                        for(int i = 0; i < EnumFacing.values().length; i++){
                            EnumFacing facing = EnumFacing.values()[i];
                            this.providesWeak[i] = boundBlock.getWeakPower(boundState, this.worldObj, this.boundPosition, facing);
                            this.providesStrong[i] = boundBlock.getStrongPower(boundState, this.worldObj, this.boundPosition, facing);
                        }
                    }
                }
            }

            if(!Arrays.equals(this.providesStrong, this.lastProvidesStrong) || !Arrays.equals(this.providesWeak, this.lastProvidesWeak)){
                System.arraycopy(this.providesWeak, 0, this.lastProvidesWeak, 0, this.providesWeak.length);
                System.arraycopy(this.providesStrong, 0, this.lastProvidesStrong, 0, this.providesStrong.length);

                this.worldObj.notifyNeighborsOfStateChange(this.pos, PosUtil.getBlock(this.pos, this.worldObj));
            }
        }
    }
}
