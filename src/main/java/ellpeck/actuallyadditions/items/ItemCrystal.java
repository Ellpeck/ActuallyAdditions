/*
 * This file ("ItemCrystal.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.blocks.BlockCrystal;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemCrystal extends Item implements IActAddItemOrBlock{

    @SideOnly(Side.CLIENT)
    public IIcon[] textures;

    public ItemCrystal(){
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1){
        return par1 >= this.textures.length ? null : this.textures[par1];
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= BlockCrystal.allCrystals.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+BlockCrystal.allCrystals[stack.getItemDamage()].name;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= BlockCrystal.allCrystals.length ? EnumRarity.common : BlockCrystal.allCrystals[stack.getItemDamage()].rarity;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < BlockCrystal.allCrystals.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.textures = new IIcon[BlockCrystal.allCrystals.length];
        for(int i = 0; i < this.textures.length; i++){
            this.textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+BlockCrystal.allCrystals[i].name);
        }
    }

    @Override
    public String getName(){
        return "itemCrystal";
    }
}