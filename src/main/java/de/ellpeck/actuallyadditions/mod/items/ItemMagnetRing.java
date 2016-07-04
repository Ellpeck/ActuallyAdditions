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
                        //If the Item is near enough to get picked up
                        //(So it doesn't bounce around until it notices itself..)
                        if(new Vec3d(entity.posX, entity.posY, entity.posZ).distanceTo(new Vec3d(item.posX, item.posY, item.posZ)) <= 1.5){
                            item.onCollideWithPlayer((EntityPlayer)entity);
                        }
                        else{
                            double speed = 0.02;
                            //Move the Item closer to the Player
                            item.motionX += (entity.posX+0.5-item.posX)*speed;
                            item.motionY += (entity.posY+1.0-item.posY)*speed;
                            item.motionZ += (entity.posZ+0.5-item.posZ)*speed;

                            if(!((EntityPlayer)entity).capabilities.isCreativeMode){
                                this.extractEnergy(stack, energyUse, false);
                            }
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
