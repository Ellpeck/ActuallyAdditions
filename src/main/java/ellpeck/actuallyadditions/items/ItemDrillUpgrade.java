/*
 * This file ("ItemDrillUpgrade.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDrillUpgrade extends Item implements IActAddItemOrBlock{

    public UpgradeType type;
    public String unlocalizedName;

    public ItemDrillUpgrade(UpgradeType type, String unlocName){
        this.type = type;
        this.unlocalizedName = unlocName;
        this.setMaxStackSize(1);
    }

    public static int getSlotToPlaceFrom(ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound != null){
            return compound.getInteger("SlotToPlaceFrom")-1;
        }
        return -1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote && this.type == UpgradeType.PLACER){
            this.setSlotToPlaceFrom(stack, player.inventory.currentItem);
        }
        return stack;
    }

    public void setSlotToPlaceFrom(ItemStack stack, int slot){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null){
            compound = new NBTTagCompound();
        }

        compound.setInteger("SlotToPlaceFrom", slot+1);

        stack.setTagCompound(compound);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public String getName(){
        return this.unlocalizedName;
    }

    public enum UpgradeType{
        SPEED,
        SPEED_II,
        SPEED_III,
        SILK_TOUCH,
        FORTUNE,
        FORTUNE_II,
        THREE_BY_THREE,
        FIVE_BY_FIVE,
        PLACER
    }
}
