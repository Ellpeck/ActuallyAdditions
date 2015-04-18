package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityFurnaceSolar extends TileEntityBase{

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isDaytime()){
                TileEntity tileBelow = WorldUtil.getTileEntityFromSide(1, worldObj, xCoord, yCoord, zCoord);

                givePowerTo(tileBelow);
            }
        }
    }

    public static void givePowerTo(TileEntity tile){
        if(tile instanceof IPowerAcceptor){
            IPowerAcceptor acceptor = (IPowerAcceptor)tile;
            int coalTimeBefore = acceptor.getItemPower();
            acceptor.setItemPower(42);
            acceptor.setPower(42);
            if(coalTimeBefore == 0){
                acceptor.setBlockMetadataToOn();
            }
            return;
        }
        if(tile instanceof TileEntityFurnace){
            TileEntityFurnace furnaceBelow = (TileEntityFurnace)tile;
            int burnTimeBefore = furnaceBelow.furnaceBurnTime;
            furnaceBelow.furnaceBurnTime = 42;
            furnaceBelow.currentItemBurnTime = 42;
            if(burnTimeBefore == 0){
                BlockFurnace.updateFurnaceBlockState(true, tile.getWorldObj(), furnaceBelow.xCoord, furnaceBelow.yCoord, furnaceBelow.zCoord);
            }
        }
    }
}
