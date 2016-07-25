/*
 * This file ("ItemMagnetRing.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ItemMagnetRing extends ItemEnergy{

    public ItemMagnetRing(String name){
        super(3000000, 5000, name);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        int energyUse = 10;
        if(!entity.isSneaking()){
            //Get all the Items in the area
            int range = 5;
            ArrayList<EntityItem> items = (ArrayList<EntityItem>)world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(entity.posX-range, entity.posY-range, entity.posZ-range, entity.posX+range, entity.posY+range, entity.posZ+range));
            if(!items.isEmpty()){
                for(EntityItem item : items){
                    if(this.getEnergyStored(stack) >= energyUse){
                        double x = entity.posX+0.5D-item.posX;
                        double y = entity.posY+1D-item.posY;
                        double z = entity.posZ+0.5D-item.posZ;

                        double distance = x*x+y*y+z*z;
                        if(distance <= 1.5){
                            if(entity instanceof EntityPlayer){
                                item.onCollideWithPlayer((EntityPlayer)entity);
                            }
                        }
                        else{
                            double speed = 0.035/distance;

                            item.motionX += x*speed;
                            if(y >= 0){
                                item.motionY = 0.125;
                            }
                            else{
                                item.motionY += y*speed;
                            }
                            item.motionZ += z*speed;
                        }

                        if(!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).capabilities.isCreativeMode){
                            this.extractEnergy(stack, energyUse, false);
                        }
                    }
                    else{
                        break;
                    }
                }
            }
        }
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
