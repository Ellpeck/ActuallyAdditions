/*
 * This file ("ItemHairyBall.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.items.base.ItemBase;
import ellpeck.actuallyadditions.recipe.HairyBallHandler;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public class ItemHairyBall extends ItemBase{

    public ItemHairyBall(String name){
        super(name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote){
            ItemStack returnItem = this.getRandomReturnItem();
            if(!player.inventory.addItemStackToInventory(returnItem)){
                EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, returnItem);
                entityItem.delayBeforeCanPickup = 0;
                player.worldObj.spawnEntityInWorld(entityItem);
            }
            stack.stackSize--;
            world.playSoundAtEntity(player, "random.pop", 0.2F, Util.RANDOM.nextFloat()*0.1F+0.9F);
        }
        return stack;
    }

    public ItemStack getRandomReturnItem(){
        return ((HairyBallHandler.Return)WeightedRandom.getRandomItem(Util.RANDOM, HairyBallHandler.returns)).returnItem.copy();
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
