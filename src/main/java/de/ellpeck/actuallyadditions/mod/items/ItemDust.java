/*
 * This file ("ItemDust.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheDusts;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemDust extends ItemBase implements IColorProvidingItem{

    public static final TheDusts[] allDusts = TheDusts.values();

    public ItemDust(String name){
        super(name);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }


    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= allDusts.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allDusts[stack.getItemDamage()].name;
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allDusts.length ? EnumRarity.COMMON : allDusts[stack.getItemDamage()].rarity;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allDusts.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    protected void registerRendering(){
        for(int i = 0; i < allDusts.length; i++){
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), new ModelResourceLocation(this.getRegistryName(), "inventory"));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IItemColor getColor(){
        return new IItemColor(){
            @Override
            public int getColorFromItemstack(ItemStack stack, int pass){
                return stack.getItemDamage() >= allDusts.length ? 0xFFFFFF : allDusts[stack.getItemDamage()].color;
            }
        };
    }
}
