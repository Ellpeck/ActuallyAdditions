package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemTeleStaff extends ItemEnergy implements INameableItem{

    public ItemTeleStaff(){
        super(500000, 10000, 2);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public String getName(){
        return "itemTeleStaff";
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        int time = this.getWaitTime(stack);
        if(time > 0){
            this.setWaitTime(stack, time-1);
        }
    }

    private void setWaitTime(ItemStack stack, int time){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) compound = new NBTTagCompound();

        compound.setInteger("waitTime", time);

        stack.setTagCompound(compound);
    }

    private int getWaitTime(ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) return 0;
        else return compound.getInteger("waitTime");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote){
            if(this.getWaitTime(stack) <= 0){
                MovingObjectPosition pos = WorldUtil.getNearestPositionWithAir(world, player, ConfigIntValues.TELE_STAFF_REACH.getValue());
                if(pos != null && (pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK || player.rotationPitch >= -5)){
                    int side = pos.sideHit;
                    if(side != -1){
                        ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
                        if(forgeSide != ForgeDirection.UNKNOWN){
                            double x = pos.hitVec.xCoord-(side == 4 ? 0.5 : 0)+(side == 5 ? 0.5 : 0);
                            double y = pos.hitVec.yCoord-(side == 0 ? 2.0 : 0)+(side == 1 ? 0.5 : 0);
                            double z = pos.hitVec.zCoord-(side == 2 ? 0.5 : 0)+(side == 3 ? 0.5 : 0);
                            int use = ConfigIntValues.TELE_STAFF_ENERGY_USE.getValue()+(int)(ConfigIntValues.TELE_STAFF_ENERGY_USE.getValue()*pos.hitVec.distanceTo(Vec3.createVectorHelper(player.posX, player.posY+(player.getEyeHeight()-player.getDefaultEyeHeight()), player.posZ)));
                            if(this.getEnergyStored(stack) >= use){
                                ((EntityPlayerMP)player).playerNetServerHandler.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
                                player.mountEntity(null);
                                world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
                                if(!player.capabilities.isCreativeMode){
                                    this.extractEnergy(stack, use, false);
                                    this.setWaitTime(stack, ConfigIntValues.TELE_STAFF_WAIT_TIME.getValue());
                                }
                            }
                        }
                    }
                }
            }
        }
        player.swingItem();
        return stack;
    }
}
