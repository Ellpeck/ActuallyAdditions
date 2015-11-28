/*
 * This file ("ItemTeleStaff.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

public class ItemTeleStaff extends ItemEnergy{

    public ItemTeleStaff(){
        super(500000, 10000);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote){
            if(this.getWaitTime(stack) <= 0){
                MovingObjectPosition pos = WorldUtil.getNearestPositionWithAir(world, player, 100);
                if(pos != null && (pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK || player.rotationPitch >= -5)){
                    int side = pos.sideHit;
                    if(side != -1){
                        ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
                        if(forgeSide != ForgeDirection.UNKNOWN){
                            double x = pos.hitVec.xCoord-(side == 4 ? 0.5 : 0)+(side == 5 ? 0.5 : 0);
                            double y = pos.hitVec.yCoord-(side == 0 ? 2.0 : 0)+(side == 1 ? 0.5 : 0);
                            double z = pos.hitVec.zCoord-(side == 2 ? 0.5 : 0)+(side == 3 ? 0.5 : 0);
                            int baseUse = 200;
                            int use = baseUse+(int)(baseUse*pos.hitVec.distanceTo(Vec3.createVectorHelper(player.posX, player.posY+(player.getEyeHeight()-player.getDefaultEyeHeight()), player.posZ)));
                            if(this.getEnergyStored(stack) >= use){
                                ((EntityPlayerMP)player).playerNetServerHandler.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
                                player.mountEntity(null);
                                world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
                                if(!player.capabilities.isCreativeMode){
                                    this.extractEnergy(stack, use, false);
                                    this.setWaitTime(stack, 50);
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

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        int time = this.getWaitTime(stack);
        if(time > 0){
            this.setWaitTime(stack, time-1);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public String getName(){
        return "itemTeleStaff";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    private int getWaitTime(ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null){
            return 0;
        }
        else{
            return compound.getInteger("waitTime");
        }
    }

    private void setWaitTime(ItemStack stack, int time){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null){
            compound = new NBTTagCompound();
        }

        compound.setInteger("waitTime", time);

        stack.setTagCompound(compound);
    }
}
