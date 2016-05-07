/*
 * This file ("ItemMisc.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMisc extends ItemBase{

    public static final TheMiscItems[] allMiscItems = TheMiscItems.values();

    public ItemMisc(String name){
        super(name);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= allMiscItems.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allMiscItems[stack.getItemDamage()].name;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allMiscItems.length ? EnumRarity.COMMON : allMiscItems[stack.getItemDamage()].rarity;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allMiscItems.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    protected void registerRendering(){
        ResourceLocation[] resLocs = new ResourceLocation[allMiscItems.length];
        for(int i = 0; i < allMiscItems.length; i++){
            String name = this.getRegistryName()+allMiscItems[i].name;
            resLocs[i] = new ResourceLocation(name);
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), new ModelResourceLocation(name, "inventory"));
        }
        ActuallyAdditions.proxy.addRenderVariant(this, resLocs);
    }
}
