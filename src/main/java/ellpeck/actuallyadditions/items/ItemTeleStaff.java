package ellpeck.actuallyadditions.items;

import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemTeleStaff extends ItemEnergy implements INameableItem{

    private static final double reach = 250D;
    private static final int energyUsedPerBlock = 250;

    public ItemTeleStaff(){
        super(1000000, 12000, 1);
    }

    @Override
    public String getName(){
        return "itemTeleStaff";
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        MovingObjectPosition pos = WorldUtil.getMovingObjectPosWithReachDistance(world, player, reach, false, true, false);
        if(pos != null){
            int side = pos.sideHit;
            if(side != -1){
                ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
                if(forgeSide != ForgeDirection.UNKNOWN){
                    double x = pos.hitVec.xCoord-(side == 4 ? 0.5 : 0)+(side == 5 ? 0.5 : 0);
                    double y = pos.hitVec.yCoord-(side == 0 ? 2.0 : 0)+(side == 1 ? 0.5 : 0);
                    double z = pos.hitVec.zCoord-(side == 2 ? 0.5 : 0)+(side == 3 ? 0.5 : 0);
                    int use = energyUsedPerBlock+(int)(energyUsedPerBlock*pos.hitVec.distanceTo(player.getPosition(1.0F)));
                    if(this.getEnergyStored(stack) >= use){
                        player.swingItem();
                        if(!world.isRemote){
                            ((EntityPlayerMP)player).playerNetServerHandler.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
                            this.extractEnergy(stack, use, false);
                        }
                    }
                }
            }
        }
        return stack;
    }
}
