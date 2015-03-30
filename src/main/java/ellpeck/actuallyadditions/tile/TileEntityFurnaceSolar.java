package ellpeck.actuallyadditions.tile;

import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityFurnaceSolar extends TileEntityBase{

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isDaytime()){
                TileEntity tileBelow = TileEntityInputter.getTileEntityFromSide(1, worldObj, xCoord, yCoord, zCoord);

                if(tileBelow instanceof TileEntityFurnace){
                    TileEntityFurnace furnaceBelow = (TileEntityFurnace)tileBelow;
                    int burnTimeBefore = furnaceBelow.furnaceBurnTime;
                    furnaceBelow.furnaceBurnTime = 42;
                    furnaceBelow.currentItemBurnTime = 42;
                    if(burnTimeBefore == 0){
                        BlockFurnace.updateFurnaceBlockState(true, this.worldObj, furnaceBelow.xCoord, furnaceBelow.yCoord, furnaceBelow.zCoord);
                    }
                    return;
                }

                if(tileBelow instanceof TileEntityFurnaceDouble){
                    TileEntityFurnaceDouble doubleBelow = (TileEntityFurnaceDouble)tileBelow;
                    int coalTimeBefore = doubleBelow.coalTime;
                    doubleBelow.coalTime = 42;
                    doubleBelow.coalTimeLeft = 42;
                    if(coalTimeBefore == 0){
                        int metaBefore = worldObj.getBlockMetadata(doubleBelow.xCoord, doubleBelow.yCoord, doubleBelow.zCoord);
                        worldObj.setBlockMetadataWithNotify(doubleBelow.xCoord, doubleBelow.yCoord, doubleBelow.zCoord, metaBefore+4, 2);
                    }
                    return;
                }

                if(tileBelow instanceof TileEntityGrinder){
                    TileEntityGrinder grinderBelow = (TileEntityGrinder)tileBelow;
                    int coalTimeBefore = grinderBelow.coalTime;
                    grinderBelow.coalTime = 42;
                    grinderBelow.coalTimeLeft = 42;
                    if(coalTimeBefore == 0){
                        worldObj.setBlockMetadataWithNotify(grinderBelow.xCoord, grinderBelow.yCoord, grinderBelow.zCoord, 1, 2);
                    }
                }
            }
        }
    }
}
