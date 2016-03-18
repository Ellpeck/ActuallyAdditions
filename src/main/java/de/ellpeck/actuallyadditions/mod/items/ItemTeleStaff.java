/*
 * This file ("ItemTeleStaff.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemTeleStaff extends ItemEnergy{

    public ItemTeleStaff(String name){
        super(500000, 10000, name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        if(!world.isRemote){
            if(this.getWaitTime(stack) <= 0){
                RayTraceResult pos = WorldUtil.getNearestPositionWithAir(world, player, 100);
                if(pos != null && (pos.typeOfHit == RayTraceResult.Type.BLOCK || player.rotationPitch >= -5)){
                    int side = pos.sideHit.ordinal();
                    if(side != -1){
                        double x = pos.hitVec.xCoord-(side == 4 ? 0.5 : 0)+(side == 5 ? 0.5 : 0);
                        double y = pos.hitVec.yCoord-(side == 0 ? 2.0 : 0)+(side == 1 ? 0.5 : 0);
                        double z = pos.hitVec.zCoord-(side == 2 ? 0.5 : 0)+(side == 3 ? 0.5 : 0);
                        int baseUse = 200;
                        int use = baseUse+(int)(baseUse*pos.hitVec.distanceTo(new Vec3d(player.posX, player.posY+(player.getEyeHeight()-player.getDefaultEyeHeight()), player.posZ)));
                        if(this.getEnergyStored(stack) >= use){
                            ((EntityPlayerMP)player).playerNetServerHandler.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
                            player.dismountRidingEntity();
                            //TODO Fix sound
                            //world.playSound(player, "mob.endermen.portal", 1.0F, 1.0F);
                            if(!player.capabilities.isCreativeMode){
                                this.extractEnergy(stack, use, false);
                                this.setWaitTime(stack, 50);
                            }
                            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                        }
                    }
                }
            }
        }
        player.swingArm(hand);
        return ActionResult.newResult(EnumActionResult.FAIL, stack);
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
        return EnumRarity.EPIC;
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
