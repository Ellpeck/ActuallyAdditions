/*
 * This file ("ItemTeleStaff.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemTeleStaff extends ItemEnergy{

    public ItemTeleStaff(String name){
        super(250000, 1000, name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        ItemStack stack = player.getHeldItem(hand);
        if(!world.isRemote){
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
                        ((EntityPlayerMP)player).connection.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
                        player.dismountRidingEntity();
                        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        if(!player.capabilities.isCreativeMode){
                            this.extractEnergyInternal(stack, use, false);
                            player.getCooldownTracker().setCooldown(this, 50);
                        }
                        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                    }
                }
            }
        }
        player.swingArm(hand);
        return ActionResult.newResult(EnumActionResult.FAIL, stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
