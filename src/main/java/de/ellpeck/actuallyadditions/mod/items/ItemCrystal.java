/*
 * This file ("ItemCrystal.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.BlockCrystal;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrystal extends ItemBase{

    private final boolean isEmpowered;

    public ItemCrystal(String name, boolean isEmpowered){
        super(name);
        this.isEmpowered = isEmpowered;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public boolean hasEffect(ItemStack stack){
        return this.isEmpowered;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= BlockCrystal.ALL_CRYSTALS.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+"_"+BlockCrystal.ALL_CRYSTALS[stack.getItemDamage()].name;
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= BlockCrystal.ALL_CRYSTALS.length ? EnumRarity.COMMON : BlockCrystal.ALL_CRYSTALS[stack.getItemDamage()].rarity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, NonNullList list){
        for(int j = 0; j < BlockCrystal.ALL_CRYSTALS.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    protected void registerRendering(){
        for(int i = 0; i < BlockCrystal.ALL_CRYSTALS.length; i++){
            String name = this.getRegistryName()+"_"+BlockCrystal.ALL_CRYSTALS[i].name;
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), new ResourceLocation(name), "inventory");
        }
    }
}