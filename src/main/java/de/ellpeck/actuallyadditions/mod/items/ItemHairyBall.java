/*
 * This file ("ItemHairyBall.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.BallOfFurReturn;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public class ItemHairyBall extends ItemBase{

    public ItemHairyBall(String name){
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        if(!world.isRemote){
            ItemStack returnItem = this.getRandomReturnItem();
            if(!player.inventory.addItemStackToInventory(returnItem)){
                EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, returnItem);
                entityItem.setPickupDelay(0);
                player.worldObj.spawnEntityInWorld(entityItem);
            }
            stack.stackSize--;

            //TODO Sound
            //world.playSoundAtEntity(player, "random.pop", 0.2F, Util.RANDOM.nextFloat()*0.1F+0.9F);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public ItemStack getRandomReturnItem(){
        return WeightedRandom.getRandomItem(Util.RANDOM, ActuallyAdditionsAPI.ballOfFurReturnItems).returnItem.copy();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
