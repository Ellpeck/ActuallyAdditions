/*
 * This file ("ItemSolidifiedExperience.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemSolidifiedExperience extends ItemBase{

    public static final int SOLID_XP_AMOUNT = 8;

    public ItemSolidifiedExperience(String name){
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        if(!world.isRemote){
            if(!player.isSneaking()){
                world.spawnEntityInWorld(new EntityXPOrb(world, player.posX+0.5, player.posY+0.5, player.posZ+0.5, SOLID_XP_AMOUNT));
                if(!player.capabilities.isCreativeMode){
                    stack.stackSize--;
                }
            }
            else{
                world.spawnEntityInWorld(new EntityXPOrb(world, player.posX+0.5, player.posY+0.5, player.posZ+0.5, SOLID_XP_AMOUNT*stack.stackSize));
                if(!player.capabilities.isCreativeMode){
                    stack.stackSize = 0;
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Nonnull
    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }
}
