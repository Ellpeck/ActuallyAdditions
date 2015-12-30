/*
 * This file ("ItemMagnetRing.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ItemMagnetRing extends ItemEnergy{

    public ItemMagnetRing(String name){
        super(30000000, 5000, name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        int energyUse = 5;
        if(this.getEnergyStored(stack) >= energyUse && !entity.isSneaking()){
            //Get all the Items in the area
            int range = 5;
            ArrayList<EntityItem> items = (ArrayList<EntityItem>)world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(entity.posX-range, entity.posY-range, entity.posZ-range, entity.posX+range, entity.posY+range, entity.posZ+range));
            if(!items.isEmpty()){
                for(EntityItem item : items){
                    //If the Item is near enough to get picked up
                    //(So it doesn't bounce around until it notices itself..)
                    if(Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ).distanceTo(Vec3.createVectorHelper(item.posX, item.posY, item.posZ)) <= 1.5){
                        item.onCollideWithPlayer((EntityPlayer)entity);
                    }
                    else{
                        double speed = 0.02;
                        //Move the Item closer to the Player
                        item.motionX += (entity.posX+0.5-item.posX)*speed;
                        item.motionY += (entity.posY+1.0-item.posY)*speed;
                        item.motionZ += (entity.posZ+0.5-item.posZ)*speed;
                    }
                }
            }

            //Use Energy per tick
            if(!((EntityPlayer)entity).capabilities.isCreativeMode){
                this.extractEnergy(stack, energyUse, false);
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }
}
